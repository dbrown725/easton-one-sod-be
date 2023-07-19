package sod.eastonone.music.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import sod.eastonone.music.dao.entity.SodSong;
import sod.eastonone.music.dao.entity.SongComment;
import sod.eastonone.music.dao.entity.User;
import sod.eastonone.music.dao.repository.SodSongRepository;
import sod.eastonone.music.dao.repository.UserRepository;
import sod.eastonone.music.model.EmailPreference;

@Service
public class EmailService {

	@Value("${emailservice.from-email-address}")
	private String fromEmailAddress;

	@Value("${emailservice.to-email-addresses}")
	private String[] toEmailAddressList;

	@Autowired
	private SodSongRepository sodSongRepository;

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private UserRepository userRepository;

	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	public void sendSODNotification(SodSong sodSong, String message, String emailAddress) throws Exception {

		try {
			String subject = "Song of the Day";

			String msgLine = "";
			if (!message.isBlank()) {
				msgLine = "<div>" + message + "</div><br/>";
			}

			String videoId = "";
			String videoUrl = sodSong.getYoutubeUrl();
			if (videoUrl.startsWith("https://www.youtube.com") && videoUrl.length() >= 43) {
				videoId = videoUrl.substring(32, 43);
			} else if (videoUrl.startsWith("https://youtu.be") && videoUrl.length() >= 28) {
				videoId = videoUrl.substring(17, 28);
			}

			ArrayList<String> toFilteredEmailAddressList = new ArrayList<String>();

			for (String recipient: toEmailAddressList) {
			    User user = userRepository.getUserByEmailAddress(recipient);
			    if(user.getEmailPreference().equals(EmailPreference.ALL) || user.getEmailPreference().equals(EmailPreference.NEW_SONG_ONLY)) {
			    	toFilteredEmailAddressList.add(recipient);
			    }
			}

			String emailText = "<html><head>"
					+ "<style>\n"
					+ "@media screen and (max-width: 1200px) {\n"
					+ "    .thumbnail {\n"
					+ "        margin: auto;\n"
					+ "     }\n"
					+ "}\n"
					+ "\n"
					+ "</style>"
					+ "</head><body>"
					+ "<div style=\"background-color:#3880FF; color:#FFFFFF; font-size:46px; text-align: center; \">Song of the Day</div><br/>"
					+ "<div class=\"thumbnail\" align=\"left\" valign=\"middle\" "
					+ "style=\"border-radius: 15px; width: 300px; height: 168px; background:url('https://i.ytimg.com/vi/" + videoId + "/hqdefault.jpg'); "
					+ "background-size:  cover; background-position: center; margin-bottom: 20px\">"
					+ "<a href=\"" + videoUrl + "\"" + "target=\"_blank\""
							+ "style=\"display:block;width:100%;text-decoration:none;height:168px;\"></a>"
					+ "</div>"
					+ "<div>Submitter: " + sodSong.getUser().getFirstName() + " " + sodSong.getUser().getLastName()
					+ "</div><br/>" + msgLine + "<div><a href=\"" + videoUrl + "\""
					+ "target=\"_blank\">" + sodSong.getTitle() + "</a></div><br/>" + "<div> Band: "
					+ sodSong.getActualBandName() + "</div><br/>" + "<div> Song: " + sodSong.getActualSongName()
					+ "</div><br/>"

					+ "<div>To add a new comment click<a href=\"" + "http://songofthedaymusic.com/?commentSongId=" + sodSong.getId() + "\""
					+ "target=\"_blank\"> here.</a>" + "</div><br/>"

					+ "<div> Playlist: <a href=\""
					+ "https://www.youtube.com/playlist?list=PLPFWSmJg6BGh7X7DGsWLdWO-Qt27460De" + "\""
					+ "target=\"_blank\">" + sodSong.getYoutubePlaylist() + "</a>" + "</div><br/>" + "<div> Visit the "
					+ "<a href=\"" + "http://songofthedaymusic.com" + "\""
					+ "target=\"_blank\">Song of the day</a> website to submit your song." + "</div><br/>"
					+ "</body></html>";

			MimeMessage mineMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mineMessage);

			helper.setSubject(subject);
			helper.setFrom(fromEmailAddress);

	        String[] recipients = new String[toFilteredEmailAddressList.size()];
	        for (int i = 0; i < toFilteredEmailAddressList.size(); i++) {
	        	recipients[i] = toFilteredEmailAddressList.get(i);
	        }
			helper.setTo(recipients);

			boolean html = true;
			helper.setText(emailText, html);

			mailSender.send(mineMessage);
			logger.debug("Email sent");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

	}

	public void sendCommentNotification(SongComment songComment, boolean update) throws Exception {

		try {
			String subject = "SOD Comment(s)";

			SodSong sodSong = sodSongRepository.getSongById(songComment.getSongId());

			String videoId = "";
			String videoUrl = sodSong.getYoutubeUrl();
			if (videoUrl.startsWith("https://www.youtube.com") && videoUrl.length() >= 43) {
				videoId = videoUrl.substring(32, 43);
			} else if (videoUrl.startsWith("https://youtu.be") && videoUrl.length() >= 28) {
				videoId = videoUrl.substring(17, 28);
			}

			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String activeCommentMarker = "<span style=\"color: red\">* </span>";
			StringBuilder commentHtml = new StringBuilder();
			for (SongComment cmt : sodSong.getSongComments())
			{

		        String modifyDateTime = cmt.getModifyTime().format(format);
		        commentHtml.append(cmt.getId() == songComment.getId()?activeCommentMarker:"");
				commentHtml.append("<span>" + cmt.getComment() + "</span><div> "  + cmt.getUserFirstName() + " " + cmt.getUserLastName()
				+ " " + modifyDateTime + " </div><br/>");
			}
			String commentType = update?"updated":"created";

			ArrayList<String> toFilteredEmailAddressList = new ArrayList<String>();

			for (String recipient: toEmailAddressList) {
			    User user = userRepository.getUserByEmailAddress(recipient);
			    if(user.getEmailPreference().equals(EmailPreference.ALL)) {
			    	toFilteredEmailAddressList.add(recipient);
			    }
			}

			String emailText = "<html><head>"
					+ "<style>\n"
					+ "@media screen and (max-width: 1200px) {\n"
					+ "    .thumbnail {\n"
					+ "        margin: auto;\n"
					+ "     }\n"
					+ "}\n"
					+ "\n"
					+ "</style>"
					+ "</head><body>"
					+ "<div style=\"background-color:#3880FF; color:#FFFFFF; font-size:46px; text-align: center; \">SOD Comment(s)</div><br/>"
					+ "<div class=\"thumbnail\" align=\"left\" valign=\"middle\" "
					+ "style=\"border-radius: 15px; width: 300px; height: 168px; background:url('https://i.ytimg.com/vi/" + videoId + "/hqdefault.jpg'); "
					+ "background-size:  cover; background-position: center; margin-bottom: 20px\">"
					+ "<a href=\"" + videoUrl + "\"" + "target=\"_blank\""
							+ "style=\"display:block;width:100%;text-decoration:none;height:168px;\"></a>"
					+ "</div>"
					+ "<div>" + sodSong.getActualBandName() + " - " + sodSong.getActualSongName()
					+ "</div>"
					+ "<div>Latest comment " + commentType + " by: " + songComment.getUserFirstName() + " " + songComment.getUserLastName() +"</div>"

					+ "<div>To add a new comment click<a href=\"" + "http://songofthedaymusic.com/?commentSongId=" + sodSong.getId() + "\""
					+ "target=\"_blank\"> here.</a>" + "</div><br/>"

					+ "<div><b>Comment(s)</b></div>"
					+ commentHtml					+ "<div> Playlist: <a href=\""
					+ "https://www.youtube.com/playlist?list=PLPFWSmJg6BGh7X7DGsWLdWO-Qt27460De" + "\""
					+ "target=\"_blank\">" + sodSong.getYoutubePlaylist() + "</a>" + "</div><br/>" + "<div> Visit the "
					+ "<a href=\"" + "http://songofthedaymusic.com" + "\""
					+ "target=\"_blank\">Song of the day</a> website to submit your song." + "</div><br/>"
					+ "</body></html>";

			MimeMessage mineMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mineMessage);

			helper.setSubject(subject);
			helper.setFrom(fromEmailAddress);

	        String[] recipients = new String[toFilteredEmailAddressList.size()];
	        for (int i = 0; i < toFilteredEmailAddressList.size(); i++) {
	        	recipients[i] = toFilteredEmailAddressList.get(i);
	        }
			helper.setTo(recipients);

			boolean html = true;
			helper.setText(emailText, html);
			
			mailSender.send(mineMessage);
			logger.debug("Email sent");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

	}

}

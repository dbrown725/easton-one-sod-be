package sod.eastonone.music.service;

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

@Service
public class EmailService {

	@Value("${emailservice.from-email-address}")
	private String fromEmailAddress;

	@Value("${emailservice.to-email-addresses}")
	private String[] toEmailAddressList;

	@Autowired
	private JavaMailSender mailSender;
	
	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

	public void sendSODNotification(SodSong sodSong, String message, String emailAddress) throws Exception {

		try {
			String subject = "Song of the Day";

			String msgLine = "";
			if (!message.isBlank()) {
				msgLine = "<div>" + message + "</div><br/>";
			}

			String emailText = "<html><body>"
					+ "<div style=\"background-color:#3880FF; color:#FFFFFF; font-size:46px; text-align: center; \">Song of the Day</div><br/>"
					+ "<div>Submitter: " + sodSong.getUser().getFirstName() + " " + sodSong.getUser().getLastName()
					+ "</div><br/>" + msgLine + "<div><a href=\"" + sodSong.getYoutubeUrl() + "\""
					+ "target=\"_blank\">" + sodSong.getTitle() + "</a></div><br/>" + "<div> Band: "
					+ sodSong.getActualBandName() + "</div><br/>" + "<div> Song: " + sodSong.getActualSongName()
					+ "</div><br/>" + "<div> Playlist: <a href=\""
					+ "<PLACEHOLDER_PLAYLIST>" + "\""
					+ "target=\"_blank\">" + sodSong.getYoutubePlaylist() + "</a>" + "</div><br/>" + "<div> Visit the "
					+ "<a href=<PLACEHOLDER_URL>"
					+ "target=\"_blank\">Song of the day</a> website to submit your song." + "</div><br/>"
					+ "</body></html>";

			MimeMessage mineMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mineMessage);

			helper.setSubject(subject);
			helper.setFrom(fromEmailAddress);
			helper.setTo(toEmailAddressList);

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

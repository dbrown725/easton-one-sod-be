package sod.eastonone.music.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import sod.eastonone.music.dao.entity.SodSong;

@Service
public class EmailService {
	
	@Value("${sendgrid.api-key}")
	private String sendGridApiKey;
	
	@Value("${emailservice.from-email-address}")
	private String fromEmailAddress;
	
	@Value("#{'${emailservice.to-email-addresses}'.split(',')}") 
	private List<String> toEmailAddressList;
	 
	public void sendSODNotification(SodSong sodSong, String message, String emailAddress) throws Exception {
		
		try {
			Email from = new Email(fromEmailAddress);
			
			var personalization = new Personalization();
			
			//Temp change for testing
//			for (String toEmail : toEmailAddressList) {
//				personalization.addTo(new Email(toEmail));
//			}
			personalization.addTo(new Email(emailAddress));

			String subject = "Song of the Day";
			
			String msgLine = "";
			if (!message.isBlank()) {
				msgLine = "<div>" + message + "</div><br/>";
			}
			
			Content content = new Content("text/html",
					"<html><body>"
							+ "<div style=\"background-color:#3880FF; color:#FFFFFF; font-size:46px; text-align: center; \">Song of the Day</div><br/>"
							+ "<div>Submitter: " + sodSong.getUser().getFirstName() + " " + sodSong.getUser().getLastName() + "</div><br/>"
							+ msgLine
							+ "<div><a href=\"" + sodSong.getYoutubeUrl() + "\"" + "target=\"_blank\">"
								+ sodSong.getTitle() + "</a></div><br/>"
							+ "<div> Band: " + sodSong.getActualBandName() + "</div><br/>"
							+ "<div> Song: " + sodSong.getActualSongName() + "</div><br/>"
							+ "<div> Playlist: <a href=\"" + "https://www.youtube.com/playlist?list=PLPFWSmJg6BGh7X7DGsWLdWO-Qt27460De"
								+ "\"" + "target=\"_blank\">" + sodSong.getYoutubePlaylist() + "</a>" + "</div><br/>"
								+ "<div> Visit the Song of the Day website to submit your song: " +
								"<a href=\"" + "http://www.google.com" + "\"" + "target=\"_blank\">Coming soon!</a>" + "</div><br/>"
							+ "<div>Please do not reply. This mailbox is not monitored.</div>"
							+ "</body></html>");

			Mail mail = new Mail();
			mail.addContent(content);
			mail.setSubject(subject);
			mail.setFrom(from);
			mail.addPersonalization(personalization);

			SendGrid sg = new SendGrid(sendGridApiKey);
			
			Request request = new Request();
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			
			Response response = sg.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());

		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

}

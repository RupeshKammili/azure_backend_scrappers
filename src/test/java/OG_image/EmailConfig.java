package OG_image;

import java.io.File;
import java.util.Properties;
 

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class EmailConfig {
	
	 
	public static void sendEmail() {

		// G mail: demorupesh890@gmail.com
		// Pass: @Nani8519
		// App pass: ljjrdoxibatbhqaj
		// App name: demo2

		// Declaring sender email, app Password and receiver email address.
		final String senderEmail = "demorupesh890@gmail.com";
		final String appPassword = "ljjrdoxibatbhqaj";
		final String receiverEmail = "demorupesh890@gmail.com";

		// SMTP server properties - like mail settings rules for how to send an email
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");// yes I want to log into email server
		prop.put("mail.smtp.host", "smtp.gmail.com");// gmail we are using.
		prop.put("mail.smtp.starttls.enable", "true");// connection safe and secure TLS.
		prop.put("mail.smtp.port", "587");// official gmail port number for sending emails securely.

		// Creating the session with prop details (how to send) and sender, password details.
		// Creating a session in servers with rules (props) and with authenticator username and app password.
		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail, appPassword);// Provide your email and app password when  asked													
			}
		});
		session.setDebug(true);// will print all the steps - for debug it will be easy.

		try {
			
			// Creating an email message
			Message message = new MimeMessage(session);// passing session details
			message.setFrom(new InternetAddress(senderEmail));// from address and it accepts in properly formatted
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));// type & to
			message.setSubject("This is automation results");// sub line
			
			String htmlContent = "<html>"
			        + "<body>"
			        + "<p>This is a scrapper automation result for LinkedIn, Twitter, Facebook.</p>"
			        + "<p><b>Click the link below to view the report:</b></p>"
			        + "<a href='https://rupeshkammili.github.io/Scrappers_Automation/'>View Extent Report</a>"
			        + "</body>"
			        + "</html>";

			MimeBodyPart htmlBody = new MimeBodyPart();
			htmlBody.setContent(htmlContent, "text/html; charset=utf-8");

		
			MimeBodyPart part = new MimeBodyPart();
			part.attachFile(new File("index.html"));

			// Adding the multiple parts to object.
			MimeMultipart multipart = new MimeMultipart();
			multipart.addBodyPart(htmlBody);
			multipart.addBodyPart(part);
			message.setContent(multipart);

			
			Transport.send(message);// sends the email with session and settings.
			System.out.println("Successfully sent the email");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

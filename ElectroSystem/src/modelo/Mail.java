package modelo;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;

	public static void main(String args[]) throws AddressException, MessagingException {
		try {
			Mail.generateAndSendEmail("Aca va el mail", "Este es el asunto", "Aca va el texto<br><br> Saludos,<br> ElectroR Admin");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void generateAndSendEmail(String to, String subject, String body) throws AddressException, MessagingException {

		// Se setean las propiedades
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");

		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		// Se agrega un recipiente que contiene el receptor del mail
		generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(to));
		// Se carga el asunto del texto
		generateMailMessage.setSubject(subject);
		// String emailBody = "Test email by ElectroR JavaMail API. " + "<br><br> Regards, <br>ElectroR Admin";
		// Se carga el cuerpo del mail en formato html
		generateMailMessage.setContent(body, "text/html");

		// Step3
		Transport transport = getMailSession.getTransport("smtp");
		// Se conecta al mail emisor
		transport.connect("smtp.gmail.com", "electrorgrupo1", "grupo1234");
		transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
		transport.close();
	}
}

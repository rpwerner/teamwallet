package com.eintracht.teamwallet;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

public class TestMail
{

    @Test
    public void testMail()
    {
        //sendMailSSL();
        sendMailTLS();
    }


    public void sendMailTLS()
    {
        final String username = "rafael.werner.us@gmail.com";
        final String password = "r4f43lw3rn3r85";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {protected PasswordAuthentication getPasswordAuthentication() {return new PasswordAuthentication(username, password);}});

        boolean isFullPaymentDone = false;
        
        String waitingOnPayment = "<div align=\"center\" class=\"img-container center  autowidth  \" style=\"padding-right: 0px;  padding-left: 0px;\">\r\n" + 
        		" <!--[if mso]>\r\n" + 
        		" <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\r\n" + 
        		"	<tr style=\"line-height:0px;line-height:0px;\">\r\n" + 
        		"	   <td style=\"padding-right: 0px; padding-left: 0px;\" align=\"center\">\r\n" + 
        		"		  <![endif]-->\r\n" + 
        		"		  <img class=\"center  autowidth \" align=\"center\" border=\"0\" src=\"cid:waiting\" alt=\"Image\" title=\"Image\" style=\"outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;clear: both;display: block !important;border: 0;height: auto;float: none;width: 100%;max-width: 488px\" width=\"488\">\r\n" + 
        		"		  <!--[if mso]>\r\n" + 
        		"	   </td>\r\n" + 
        		"	</tr>\r\n" + 
        		" </table>\r\n" + 
        		" <![endif]-->\r\n" + 
        		"</div>";
        
        String welcomeSentence = "Hier ist was Sie offen bei der Mannschaftskasse haben.";
        String letsPay = "Wollen wir das Geld überweisen? Ja? Super!!";
        
        String welcomeWaitingSentence = "Sie haben immer noch nicht alle Schulden bezahlt!";
        String waitingLetsPay = "Wollen wir das jetzt machen? Es dauert nicht soooo lange...";
        
        
        try {

        	// read main template
        	Path path = Paths.get(getClass().getClassLoader().getResource("monthlyFee.txt").toURI());
		    StringBuilder mainBody = new StringBuilder();
		    Stream<String> lines = Files.lines(path);
		    lines.forEach(line -> mainBody.append(line).append("\n"));
		    lines.close();

		    // Set last name
		    String main = mainBody.toString().replaceAll("\\{lastName\\}", "Best");
		    
		    
		    //read item template
        	path = Paths.get(getClass().getClassLoader().getResource("item_to_be_paid.txt").toURI());
		    StringBuilder itemToBePaid = new StringBuilder();
		    lines = Files.lines(path);
		    lines.forEach(line -> itemToBePaid.append(line).append("\n"));
		    lines.close();
		    
		    // Setting Dynamic penalties
		    String penalty1 = new String(itemToBePaid.toString());
		    
		    penalty1 = penalty1.replaceAll("\\{toBePaid\\}", "Monats Beitrag - August").replaceAll("\\{value\\}", "5");
		    
		    String penalty2 = new String(itemToBePaid.toString());
		    penalty2 = penalty2.replaceAll("\\{toBePaid\\}", "3x zu spät ins Training").replaceAll("\\{value\\}", "3");
		    
		    
		    // putting all together
		    
		    // set welcome text
		    if(isFullPaymentDone)
		    {
		    	main = main.replaceAll("\\{waitingOnPayment\\}", "").replaceAll("\\{welcomeSentence\\}", welcomeSentence).replaceAll("\\{letsPay\\}", letsPay);
		    }
		    else
		    {
		    	main = main.replaceAll("\\{waitingOnPayment\\}", waitingOnPayment).replaceAll("\\{welcomeSentence\\}", welcomeWaitingSentence).replaceAll("\\{letsPay\\}", waitingLetsPay);
		    }
		   
		    
		    // set items and total to main
		    main = main.replaceAll("\\{items\\}", penalty1+penalty2).replaceAll("\\{total\\}", "8");
		    
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("rafael.werner.us@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("rafael.werner.85@gmail.com"));
            //message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("niklasbest@me.com"));
            message.setSubject("Testing Subject 2");

            
            // This mail has 2 part, the BODY and the embedded image
            MimeMultipart multipart = new MimeMultipart("related");

            // first part (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(main, "text/html; charset=utf-8");
            // add it
            multipart.addBodyPart(messageBodyPart);

            // second part (the image)
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource("D:/github/teamwallet/src/main/resources/images/logo.jpg");

            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");
            multipart.addBodyPart(messageBodyPart);
            
            if(!isFullPaymentDone)
            {
            	messageBodyPart = new MimeBodyPart();
            	DataSource waiting = new FileDataSource("D:/github/teamwallet/src/main/resources/images/waiting.jpg");

                messageBodyPart.setDataHandler(new DataHandler(waiting));
                messageBodyPart.setHeader("Content-ID", "<waiting>");
                multipart.addBodyPart(messageBodyPart);
            }
            
            // add image to the multipart
            //multipart.addBodyPart(messageBodyPart);

            // put everything together
            message.setContent(multipart);
            
            Transport.send(message);

            System.out.println("Done");

        }  catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}

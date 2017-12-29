package com.ltmh.myelin.mail;

import static org.hamcrest.CoreMatchers.not;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.search.FlagTerm;


public class MyelinEmailTracer {
	public static void check(String host, String storeType, String user,
		      String password) 
		   {
		      try {

		      //create properties field
		      Properties properties = new Properties();

		      properties.put("mail.pop3.host", host);
		      properties.put("mail.pop3.port", "995");
		      properties.put("mail.pop3.starttls.enable", "true");
		      Session emailSession = Session.getDefaultInstance(properties);
		  
		      //create the POP3 store object and connect with the pop server
		      Store store = emailSession.getStore("pop3s");

		      store.connect(host, user, password);

		      //create the folder object and open it
		      Folder emailFolder = store.getFolder("INBOX");
		      emailFolder.open(Folder.READ_ONLY);
		      
		      
		   // search for all "unseen" messages
		      Flags seen = new Flags(Flags.Flag.SEEN);
		      FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
		      Message notSeenMessages[] = emailFolder.search(unseenFlagTerm);
		      System.out.println("notSeenMessages: "+notSeenMessages.length);

//		      // retrieve the messages from the folder in an array and print it
//		      Message[] messages = emailFolder.getMessages();
//		      System.out.println("messages.length---" + messages.length);

		      for (int i = 0, n = notSeenMessages.length; i < n; i++) {
		         Message message = notSeenMessages[i];
		         System.out.println("message exPunged: "+message.isExpunged());
//		         System.out.println("message exPunged: "+message.getContentType());
//		         System.out.println("message exPunged: "+message.getDescription());
//		         System.out.println("message exPunged: "+message.getDisposition());
		         System.out.println("---------------------------------");
		         System.out.println("Email Number " + (i + 1));
		         System.out.println("Subject: " + message.getSubject());
		         System.out.println("From: " + message.getFrom()[0]);
				 System.out.println("From1: " + InternetAddress.toString(message.getFrom()));
				 String email = message.getFrom() == null ? null : ((InternetAddress) message.getFrom()[0]).getAddress();
				 System.out.println("sender email: "+email);
		         System.out.println("Text: " + message.getContent().toString());
		         if (message.getContent() instanceof Multipart) {
				     System.out.println("Text is Multipart....");
		         }
		         Multipart mp = (Multipart) message.getContent();  
		         Object p = mp.getBodyPart(0).getContent();  
		         String q = p.toString();//object has the body content  
		         System.out.println("q: "+q);
		      }

		      //close the store and folder objects
		      emailFolder.close(false);
		      store.close();

		      } catch (NoSuchProviderException e) {
		         e.printStackTrace();
		      } catch (MessagingException e) {
		         e.printStackTrace();
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		   }

		   public static void main(String[] args) {

		      String host = "pop.gmail.com";// change accordingly
		      String mailStoreType = "pop3";
		      String username = "ltm.myelin@gmail.com";// change accordingly
		      String password = "myelin!23";// change accordingly

		      check(host, mailStoreType, username, password);

		   }
}

package com.myelin.builder.server.scheduler;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.FetchProfile;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.myelin.builder.app.entity.MyelinContentPlan;
import com.myelin.builder.framework.mail.MailSessionManager;
import com.myelin.builder.framework.util.OpenStringUtils;
import com.myelin.builder.server.manager.MyelinQueueManager;

@Component
public class LtmMyelinContentReceiver {
	private final Logger log = LogManager.getLogger(this.getClass());

	private final static String host = "pop.gmail.com";// change accordingly
	private final static String mailStoreType = "pop3";
	private final static String username = "ltm.myelin@gmail.com";// change accordingly
	private final static String password = "myelin!23";// change accordingly

	@Autowired
    private MyelinQueueManager myelinQueueManager;

	@Autowired
    private MailSessionManager mailSessionManager;
	
	@Scheduled(cron = "10 0/5 * * * ?")
	public void receiveContentFromMail() {
		log.info("Doing..  Process..");
		try {
			
			Session emailSession = mailSessionManager.getSession();

			// create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore("pop3s");

			store.connect(host, username, password);

			// create the folder object and open it
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			// search for all "unseen" messages
			Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
			Message notSeenMessages[] = emailFolder.search(unseenFlagTerm);
			log.info("notSeenMessages: " + notSeenMessages.length);

			// // retrieve the messages from the folder in an array and print it
			// Message[] messages = emailFolder.getMessages();
			// log.info("messages.length---" + messages.length);

			for (int i = 0, n = notSeenMessages.length; i < n; i++) {
				Message message = notSeenMessages[i];
				log.info("message exPunged: " + message.isExpunged());
				// log.info("message exPunged: "+message.getContentType());
				// log.info("message exPunged: "+message.getDescription());
				// log.info("message exPunged: "+message.getDisposition());
				log.info("---------------------------------");
				log.info("Email Number " + (i + 1));
				log.info("Subject: " + message.getSubject());
				log.info("From: " + message.getFrom()[0]);
				log.info("From1: " + InternetAddress.toString(message.getFrom()));
				String email = message.getFrom() == null ? null : ((InternetAddress) message.getFrom()[0]).getAddress();
				log.info("sender email: " + email);
				log.info("Text: " + message.getContent().toString());
				String realContent = "";
				if (message.getContent() instanceof Multipart) {
					log.info("Text is Multipart....");
					Multipart mp = (Multipart) message.getContent();
					for (int kk = 0; kk < mp.getCount(); kk++) {
				        BodyPart bp = mp.getBodyPart(kk);
				        if (Pattern
				                .compile(Pattern.quote("text/html"),
				                        Pattern.CASE_INSENSITIVE)
				                .matcher(bp.getContentType()).find()) {
				            // found html part
				        	realContent = (String) bp.getContent();
				        	break;
				        } else {
				            // some other bodypart...
				        }
				    }
//					Object p = mp.getBodyPart(0).getContent();
//					realContent = p.toString();// object has the body content
					log.info("realContent: " + realContent);
				}else {
					realContent = message.getContent().toString();
				}
				
				
				/**
				 * email 삭제 처리
				 */
//				String ans = "Y";
//	            if ("Y".equals(ans) || "y".equals(ans)) {
//		       // set the DELETE flag to true
//		       message.setFlag(Flags.Flag.DELETED, true);
//		       log.info("Marked DELETE for message: " + subject);
//	            } else if ("n".equals(ans)) {
//		       break;
//	            }

				MyelinContentPlan planObj = new MyelinContentPlan();
				planObj.setCustEmail(email);
				planObj.setCustId(email);
				planObj.setMyelinContent(realContent);
				planObj.setMyelinSubject(message.getSubject());
				
				String firstTime = OpenStringUtils.getCurrentTimeFullDisplayHivePartitionMinute();
				planObj.setFirstTime(firstTime);
				
				myelinQueueManager.getQueueByName("ORC_WRITE_EVENT").put(planObj);
				
			}

			// close the store and folder objects
			emailFolder.close(false);
			store.close();

		} catch (

		NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void check(String host, String storeType, String user, String password) {
		try {

			// create properties field
			Properties properties = new Properties();

			properties.put("mail.pop3.host", host);
			properties.put("mail.pop3.port", "995");
			properties.put("mail.pop3.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);

			// create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore("pop3s");

			store.connect(host, user, password);

			// create the folder object and open it
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			// search for all "unseen" messages
			Flags seen = new Flags(Flags.Flag.SEEN);
			FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
			Message notSeenMessages[] = emailFolder.search(unseenFlagTerm);
//			log.info("notSeenMessages: " + notSeenMessages.length);

			// // retrieve the messages from the folder in an array and print it
			// Message[] messages = emailFolder.getMessages();
			// log.info("messages.length---" + messages.length);

			for (int i = 0, n = notSeenMessages.length; i < n; i++) {
				Message message = notSeenMessages[i];
//				log.info("message exPunged: " + message.isExpunged());
//				log.info("Email Number " + (i + 1));
//				log.info("Subject: " + message.getSubject());
//				log.info("From: " + message.getFrom()[0]);
//				log.info("From1: " + InternetAddress.toString(message.getFrom()));
				String email = message.getFrom() == null ? null : ((InternetAddress) message.getFrom()[0]).getAddress();
//				log.info("sender email: " + email);
//				log.info("Text: " + message.getContent().toString());
				if (message.getContent() instanceof Multipart) {
//					log.info("Text is Multipart....");
				}
				Multipart mp = (Multipart) message.getContent();
				Object p = mp.getBodyPart(0).getContent();
				String q = p.toString();// object has the body content
//				log.info("q: " + q);
			}

			// close the store and folder objects
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
}

package com.myelin.builder.framework.mail;

import java.net.Authenticator;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.myelin.builder.app.entity.MyelinContentPlan;

@Component
public class MailSender {

	@Autowired
	private MailSessionManager mailSessionManager;
	
	public MailSender() {
		startup();
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void setSession() {
	}

	public void startup() {
		
	}
	
	public void sendingMail(String recipient, String flag, MyelinContentPlan content) throws AddressException, MessagingException {
		if (mailSessionManager.getSession() == null) 
			mailSessionManager.startup();
		
		Message mimeMessage = new MimeMessage(mailSessionManager.getSession()); // MimeMessage 생성
		mimeMessage.setFrom(new InternetAddress("hhokyung@gmail.com")); // 발신자 셋팅 , 보내는 사람의 이메일주소를 한번 더 입력합니다. 이때는 이메일 풀
																		// 주소를 다 작성해주세요.
		mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));// 수신자셋팅 //.TO 외에 .CC(참조)
								
		String subject = content.getMyelinSubject();
		if (flag.equals("10")) {
			subject = subject + " (10 MINUTE)";
		}if (flag.equals("20")) {
			subject = subject + " (2 HOUR)";
		}else if (flag.equals("24")) {
			subject = subject + " (1 DAY)";
			
		}else if (flag.equals("70")) {
			subject = subject + " (7 DAY)";
			
		}else if (flag.equals("300")) {
			subject = subject + " (30 DAY)";
			
		}else if (flag.equals("1800")) {
			subject = subject + " (180 DAY)";			
		}
		mimeMessage.setSubject(subject); // 제목셋팅
		String contentTemp = content.getMyelinContent();
		contentTemp = contentTemp + "\n\n"+" ## This Content First Created Time : "+content.getFirstTime();
		mimeMessage.setContent(contentTemp, "text/html; charset=utf-8"); // 내용셋팅

		Transport.send(mimeMessage);
	}
	

	public void sendingMail(MyelinContentPlan content) throws AddressException, MessagingException {
		if (mailSessionManager.getSession() == null) 
			mailSessionManager.startup();
		
		Message mimeMessage = new MimeMessage(mailSessionManager.getSession()); // MimeMessage 생성
		mimeMessage.setFrom(new InternetAddress("ltm.myelin@gmail.com")); // 발신자 셋팅 , 보내는 사람의 이메일주소를 한번 더 입력합니다. 이때는 이메일 풀
																		// 주소를 다 작성해주세요.
		mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(content.getCustEmail()));// 수신자셋팅 //.TO 외에 .CC(참조)
				
		String flag = content.getMyelinFlag();
		
		String subject = content.getMyelinSubject();
		if (flag.equals("10")) {
			subject = subject + " (10 MINUTE)";
		}if (flag.equals("20")) {
			subject = subject + " (2 HOUR)";
		}else if (flag.equals("24")) {
			subject = subject + " (1 DAY)";
			
		}else if (flag.equals("70")) {
			subject = subject + " (7 DAY)";
			
		}else if (flag.equals("300")) {
			subject = subject + " (30 DAY)";
			
		}else if (flag.equals("1800")) {
			subject = subject + " (180 DAY)";			
		}
		mimeMessage.setSubject(subject); // 제목셋팅
		String contentTemp = content.getMyelinContent();
		contentTemp = contentTemp + "\n\n"+" ## This Content First Created Time : "+content.getFirstTime();
		mimeMessage.setContent(contentTemp, "text/html; charset=utf-8"); // 내용셋팅

		Transport.send(mimeMessage);
	}

	public static void main(String[] args) throws AddressException, MessagingException {
		// 네이버일 경우 smtp.naver.com 을 입력합니다. // Google일 경우 smtp.gmail.com 을 입력합니다.
		String host = "smtp.gmail.com";
		final String username = "hhokyung@gmail.com"; // 네이버 아이디를 입력해주세요. @nave.com은 입력하지 마시구요.
		final String password = "o07901!23"; // 네이버 이메일 비밀번호를 입력해주세요.
		int port = 465; // 포트번호 // 메일 내용
		String recipient = "hhokyung@mediaflow.kr"; // 받는 사람의 메일주소를 입력해주세요.
		String subject = "메일테스트"; // 메일 제목 입력해주세요.
		String body = username + "님으로 부터 메일을 받았습니다."; // 메일 내용 입력해주세요.
		Properties props = System.getProperties();
		// 정보를 담기 위한 객체 생성
		// SMTP 서버 정보 설정
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.trust", host);
		// Session 생성
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			String un = username;
			String pw = password;

			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(un, pw);
			}
		});
		session.setDebug(true);

		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\r\n" + "<HTML>\r\n" + " <HEAD>\r\n"
				+ "	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n" + " </HEAD>\r\n"
				+ " <BODY>\r\n" + " <table>\r\n" + "	<tr>\r\n" + "		<td>assign</td>\r\n"
				+ "		<td>[동] ①할당하다, 배당하다  ②지명하다, 지정하다 </td>\r\n" + "	</tr>\r\n" + "	<tr>\r\n"
				+ "		<td>[əsáin]</td>\r\n" + "		<td>He assigned work to each man.<br>그는 각자에게 작업을 할당하다 </td>\r\n"
				+ "	</tr>\r\n" + " </table>\r\n" + " </BODY>\r\n" + "</HTML>\r\n");
		// for debug
		Message mimeMessage = new MimeMessage(session); // MimeMessage 생성
		mimeMessage.setFrom(new InternetAddress("hhokyung@gmail.com")); // 발신자 셋팅 , 보내는 사람의 이메일주소를 한번 더 입력합니다. 이때는 이메일 풀
																		// 주소를 다 작성해주세요.
		mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));// 수신자셋팅 //.TO 외에 .CC(참조)
																							// .BCC(숨은참조) 도 있음
		mimeMessage.setSubject("dfsssdffds"); // 제목셋팅
		mimeMessage.setText(sb.toString());// 내용셋팅
//		mimeMessage.setContent(sb.toString(), "text/html; charset=utf-8"); // 내용셋팅

		for (int ii = 0; ii < 1; ii++)
			Transport.send(mimeMessage); // javax.mail.Transport.send() 이용

		// for debug
		// Message mimeMessage = new MimeMessage(session); //MimeMessage 생성
		// mimeMessage.setFrom(new InternetAddress("hhokyung@gmail.com")); //발신자 셋팅 ,
		// 보내는 사람의 이메일주소를 한번 더 입력합니다. 이때는 이메일 풀 주소를 다 작성해주세요.
		// mimeMessage.setRecipient(Message.RecipientType.TO, new
		// InternetAddress(recipient));//수신자셋팅 //.TO 외에 .CC(참조) .BCC(숨은참조) 도 있음
		// mimeMessage.setSubject(sb.toString()); //제목셋팅
		// mimeMessage.setText(body); //내용셋팅
		// Transport.send(mimeMessage); //javax.mail.Transport.send() 이용
		//
		//
		//// Message mimeMessage = new MimeMessage(session); //MimeMessage 생성
		// mimeMessage.setFrom(new InternetAddress("hhokyung@gmail.com")); //발신자 셋팅 ,
		// 보내는 사람의 이메일주소를 한번 더 입력합니다. 이때는 이메일 풀 주소를 다 작성해주세요.
		// mimeMessage.setRecipient(Message.RecipientType.TO, new
		// InternetAddress(recipient));//수신자셋팅 //.TO 외에 .CC(참조) .BCC(숨은참조) 도 있음
		// mimeMessage.setSubject(sb.toString()); //제목셋팅
		// mimeMessage.setText(body); //내용셋팅
		// Transport.send(mimeMessage); //javax.mail.Transport.send() 이용

	}

	// static class MyAuthenticator extends Authenticator {
	// @Override
	// public PasswordAuthentication getPasswordAuthentication() {
	// String username = "hhokyung@gmail.com";
	// String password = "o07901!23";
	// return new PasswordAuthentication(username, password);
	// }
	// }

}

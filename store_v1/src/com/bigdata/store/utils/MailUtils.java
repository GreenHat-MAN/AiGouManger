package com.bigdata.store.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {

	public static void sendMail(String email, String code) throws AddressException, MessagingException {
		// 1.创建一个程序与邮件服务器会话对象 Session
		Properties props = new Properties();
		// 设置发送的协议
		props.setProperty("mail.transport.protocol", "SMTP");

		// 设置发送邮件的服务器
		props.setProperty("mail.host", "smtp.163.com");
		props.setProperty("mail.smtp.auth", "true");// 指定验证为true

		// 创建验证器
		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				// 设置发送人的帐号和授权码
				return new PasswordAuthentication("zhangjidong20005@163.com", "TULKBBWXELUBOODM");
			}
		};

		Session session = Session.getInstance(props, auth);

		// 2.创建一个Message，它相当于是邮件内容
		Message message = new MimeMessage(session);

		// 设置发送者
		message.setFrom(new InternetAddress("zhangjidong20005@163.com"));

		// 设置发送方式与接收者
		message.setRecipient(RecipientType.TO, new InternetAddress(email));

		// 设置邮件主题
		message.setSubject("来自爱购网的激活邮件");

		String url = "http://localhost:8080/store_v1/UserServlet?method=active&code=" + code;
		String content = "<h1>来自爱购网的激活邮件!激活请点击以下链接!</h1><h3><a href='" + url + "'>" + url + "</a></h3>";
		// 设置邮件内容
		message.setContent(content, "text/html;charset=utf-8");

		// 3.创建 Transport用于将邮件发送
		Transport.send(message);
	}

	public static void main(String[] args) throws AddressException, MessagingException {
		MailUtils.sendMail("2389515877@qq.com", "23432seds");
		
	}
}

package org.wc.plugins;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailSender {

  private Logger log = Logger.getLogger(getClass().getName());
  private MailHost host;
  JavaMailSenderImpl mail$send;

  public MailSender(MailHost host) {
    this.host = host;
    this.mail$send = new JavaMailSenderImpl();
    mail$send.setHost(host.getHost());
    mail$send.setUsername(host.getUsername());
    mail$send.setPassword(host.getPassword());

    //
    Properties prop = new Properties();
    prop.put("mail.smtp.auth", "true");
    prop.put("mail.smtp.timeout", 25000);
    prop.put("mail.smtp.starttls.enable", "true");
    prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    mail$send.setJavaMailProperties(prop);

  }

  /**
   * 
   * <p>
   * 这里处理图片：
   * <p>
   * Elements elelist = doc.select("img"); int i = 1;
   * 
   * for (Element ele : elelist) { String imgurl = ele.attr("src"); ele.attr("src", "cid:imgdd"+i);
   * //FileDataSource sorce = new FileDataSource(new File(imgurl)); InputStreamSource sorce = new
   * UrlResource(imgurl); help.addInline("imgdd" + i, sorce,"image/jpg");
   * 
   * i++; }
   * 
   * @param email
   * @param title
   * @param content
   * @param ishtml
   */
  public void send(String email, String title, String content, boolean ishtml) {
    try {

      //
      MimeMessage msg = mail$send.createMimeMessage();
      MimeMessageHelper help = new MimeMessageHelper(msg, true, "utf-8");
      help.setTo(email);
      //help.setFrom(host.getUsername());
      help.setFrom(host.getUsername(), "钱大人");
      help.setSubject(title);
      if (ishtml) {
        Document doc = Jsoup.parse(content);
        help.setText(doc.html(), true);
      } else {
        help.setText(content);
      }

      mail$send.send(msg);
    } catch (Exception e) {
      e.printStackTrace();
      String msg = "邮件发送错误： to -> " + email + "\r\n" + content + " \r\n" + e.getMessage();
      log.error(msg);
    }
  }
 
  public void send(String to, String subject, String content) {
    this.send(to, subject, content, false);
  }

  public void sendHTML(String to, String subject, String content) {
    this.send(to, subject, content, true);
  }



  public static class MailHost {

    private String host;
    private int port;
    private String username;
    private String password;

    public String getHost() {
      return host;
    }

    public void setHost(String host) {
      this.host = host;
    }

    public int getPort() {
      return port;
    }

    public void setPort(int port) {
      this.port = port;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

  }



  public static void main(String[] args) throws IOException {
    MailHost host = new MailHost(); 
    MailSender manager = new MailSender(host);
    String file = FileUtils.readFileToString(new File(
        "/home/paopao/workspace/qdrspace/qiandarenv4/src/main/webapp/WEB-INF/template/mt.findpass.mailbox.verify.html"));
    manager.sendHTML("bboyer@qq.com", "发发送到发送到反倒是送到反倒是飞是这里得股", file);
  }

}

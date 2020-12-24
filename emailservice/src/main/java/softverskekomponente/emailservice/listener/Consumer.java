package softverskekomponente.emailservice.listener;

import java.util.Properties;
import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;



@Component
public class Consumer {

	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	    
	    mailSender.setUsername("softverskekomponente@gmail.com");
	    mailSender.setPassword("skraf123!");
	    
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	    
	    return mailSender;
	}
	
	@JmsListener(destination = "NewAccountEmail.queue")
	public void sendNewAccountEmail(String email) {
		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("softverskekomponente@gmail.com");
        message.setTo(email); 
        message.setSubject("Novi Nalog na sajtu"); 
        message.setText("Otvorili ste novi nalog na sajtu sa ovom email adresom. SPORTSKI POZDRAV!");
        this.getJavaMailSender().send(message);
        
		
	}
	
	@JmsListener(destination = "DeletedFlightEmail.queue")
	public void sendDeletedFlightNotification( String email) {
		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("softverskekomponente@gmail.com");
        message.setTo(email); 
        message.setSubject("Vas let je otkazan"); 
        message.setText("Nazalost vas let je otkazan. Pare su vam vracene! Vise detalja nadjite u aplikaciji");
        this.getJavaMailSender().send(message);
        
		
	}
	
	@JmsListener(destination = "AccountChanged.queue")
	public void sendChangedEmail(String email) {
		
		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("softverskekomponente@gmail.com");
        message.setTo(email); 
        message.setSubject("Promenjen Nalog na sajtu"); 
        message.setText("Promenili ste email na ovaj novi na sajtu SOFT KOMP. SPORTSKI POZDRAV!");
        this.getJavaMailSender().send(message);
		
	}
}
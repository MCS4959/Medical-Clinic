package com.mc.service;

import java.io.Serializable;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String REMETENTE = "medicClinicWeb@gmail.com";
    private static final String SENHA_APP  = "kyclevbeevfmyhfa";
    
    
    public void enviarConfirmacaoConsulta(String emailDestino, String nomePaciente, 
		String nomeMedico, String especialidade, 
		String dataConsulta) {
			Properties props = new Properties();
			props.put("mail.smtp.host",            "smtp.gmail.com");
			props.put("mail.smtp.port",            "587");
			props.put("mail.smtp.auth",            "true");
			props.put("mail.smtp.starttls.enable", "true");
		
		Session session = Session.getInstance(props, new Authenticator() {
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(REMETENTE, SENHA_APP);
			}
		});
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(REMETENTE));
			message.setRecipients(Message.RecipientType.TO,
			   InternetAddress.parse(emailDestino));
			message.setSubject("Consulta confirmada - Medical Clinic");
			
			String corpo = "<div style='font-family:Arial,sans-serif;max-width:500px;'>"
			+ "<h2 style='color:#141e30;'>Olá, " + nomePaciente + "!</h2>"
			+ "<p>Sua consulta foi agendada com sucesso. Confira os detalhes:</p>"
			+ "<div style='background:#f0f4f8;padding:16px;border-radius:8px;"
			+      "border-left:4px solid #1abc9c;margin:16px 0;'>"
			+ "<p><strong>📅 Data:</strong> "        + dataConsulta  + "</p>"
			+ "<p><strong>🩺 Médico:</strong> "      + nomeMedico    + "</p>"
			+ "<p><strong>🏥 Especialidade:</strong> " + especialidade + "</p>"
			+ "</div>"
			+ "<p>Caso precise cancelar ou remarcar, entre em contato conosco.</p>"
			+ "<br><p>Atenciosamente,<br><strong>Equipe Medical Clinic</strong></p>"
			+ "</div>";
		
			message.setContent(corpo, "text/html; charset=utf-8");
			Transport.send(message);
			
			} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException("Erro ao enviar email: " + e.getMessage());
	}
}
    

    public void enviarSenhaAcesso(String emailDestino, String nomePaciente, String senha) {

        Properties props = new Properties();
        props.put("mail.smtp.host",            "smtp.gmail.com");
        props.put("mail.smtp.port",            "587");
        props.put("mail.smtp.auth",            "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMETENTE, SENHA_APP);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(REMETENTE));
            message.setRecipients(Message.RecipientType.TO,
                                  InternetAddress.parse(emailDestino));
            message.setSubject("Bem-vindo à Medical Clinic!");

            String corpo = "<div style='font-family:Arial,sans-serif;max-width:500px;'>"
                + "<h2 style='color:#141e30;'>Olá, " + nomePaciente + "!</h2>"
                + "<p>Seu cadastro na <strong>Medical Clinic</strong> foi realizado com sucesso.</p>"
                + "<p>Use as credenciais abaixo para acessar o sistema:</p>"
                + "<div style='background:#f0f4f8;padding:16px;border-radius:8px;"
                +      "border-left:4px solid #1abc9c;margin:16px 0;'>"
                + "<p><strong>Email:</strong> " + emailDestino + "</p>"
                + "<p><strong>Senha:</strong> " + senha + "</p>"
                + "</div>"
                + "<p style='color:#e74c3c;font-size:12px;'>"
                + "Por segurança, recomendamos alterar sua senha no primeiro acesso.</p>"
                + "<br><p>Atenciosamente,<br><strong>Equipe Medical Clinic</strong></p>"
                + "</div>";

            message.setContent(corpo, "text/html; charset=utf-8");
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao enviar email: " + e.getMessage());
        }
    }
}
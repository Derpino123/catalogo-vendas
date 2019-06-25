package br.com.davsantos.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.davsantos.entities.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage message);
}

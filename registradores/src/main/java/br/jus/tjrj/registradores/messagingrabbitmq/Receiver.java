package br.jus.tjrj.registradores.messagingrabbitmq;

import java.util.concurrent.CountDownLatch;

import org.springframework.stereotype.Component;

import br.jus.tjrj.registradores.model.Pessoa;

@Component
public class Receiver {
	
	private CountDownLatch latch = new CountDownLatch(1);
	
	public void receiveMessage(byte[] message) {
		String message1 = new String(message);
		System.out.println("Received <" + message1 + ">");
		latch.countDown();
	}
	
	public void receiveMessage(String message) {
		System.out.println("Received <" + message + ">");
		latch.countDown();
	}
	
	public void receiveMessage( Pessoa pessoa ) {
		System.out.println(pessoa);
	}
	
	public CountDownLatch getLatch() {
		return latch;
	}
}
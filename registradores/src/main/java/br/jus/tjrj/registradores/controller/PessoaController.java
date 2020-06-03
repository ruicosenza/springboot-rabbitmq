package br.jus.tjrj.registradores.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.jus.tjrj.registradores.messagingrabbitmq.Receiver;
import br.jus.tjrj.registradores.messagingrabbitmq.Sender;
import br.jus.tjrj.registradores.model.Pessoa;

@RestController
@RequestMapping("/cosenza")
public class PessoaController {
	private final RabbitTemplate rabbitTemplate;
	private final Receiver receiver;

	public PessoaController(Receiver receiver, RabbitTemplate rabbitTemplate) {
		this.receiver = receiver;
		this.rabbitTemplate = rabbitTemplate;
	}

	@PostMapping
	public void cadastrar(@RequestBody Pessoa pessoa) {
		try {
			System.out.println("Sending message...");
			rabbitTemplate.convertAndSend(Sender.topicExchangeName, "foo.bar.baz", pessoa);
			receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
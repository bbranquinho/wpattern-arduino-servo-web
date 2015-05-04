package org.wpattern.websocket;

import java.util.*;
import javax.websocket.*;
import javax.websocket.server.*;

@ServerEndpoint(value = "/websocket")
public class WebSocket {

	private static final Set<Session> sessoes = Collections.synchronizedSet(new HashSet<Session>());

	private static final Serial serial = new Serial();

	static {
		serial.abrirPortaSerial();
	}

	@OnOpen
	public void sessaoAberta(Session sessao) {
		System.out.println("sessao aberta");
		sessoes.add(sessao);
	}

	@OnClose
	public void sessaoFechada(Session sessao) {
		System.out.println("sessao fechada");
		sessoes.remove(sessao);
	}

	@OnMessage
	public void receberMensagem(String mensagem) {
		System.out.println("mensagem recebida: " + mensagem);
		serial.enviarMensagemArduino(mensagem);
	}

	public static void enviarMensagemClientes(String mensagem) {
		for (Session session : sessoes) {
			try {
				session.getBasicRemote().sendText(mensagem);
			} catch (Exception e) {
				System.err.println("Erro ao enviar mensagem para o cliente. " + e.getMessage());
			}
		}
	}
}

package org.wpattern.websocket;

import gnu.io.*;
import java.io.*;

public class Serial implements SerialPortEventListener {

	private BufferedReader input;

	@Override
	public void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				WebSocket.enviarMensagemClientes(this.input.readLine());
			} catch (Exception e) {
				System.err.println("Erro ao ler dados do arduino. " + e.getMessage());
			}
		}
	}

	private OutputStream output;

	public void enviarMensagemArduino(String message) {
		try {
			this.output.write(message.getBytes());
		} catch (Exception e) {
			System.err.println("Erro ao enviar mensagem para o Arduino. " + e.getMessage());
		}
	}

	private static final int RATE = 9600;
	
	private static final String PORT = "COM9";

	public void abrirPortaSerial() {
		try {
			CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(PORT);
	
			SerialPort serialPort = (SerialPort) portId.open(this.getClass().getName(), 2000);
	
			serialPort.setSerialPortParams(RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
	
			serialPort.enableReceiveTimeout(1000);
			serialPort.enableReceiveThreshold(0);
			
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();
	
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println("Error ao abrir a porta serial.");
		}
	}

}


package com.gr.utils.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import com.gr.utils.Utils;

public class TcpSocketHandler implements SocketHandler {
	private SocketConsumer consumer;
	private Socket socket;
	private DataOutputStream outputStream;
	private DataInputStream inputStream;
	private Thread readerThread;
	private int packetSize = 128;

	public int getPacketSize() {
		return packetSize;
	}

	public void setPacketSize(int packetSize) {
		this.packetSize = packetSize;
	}

	public TcpSocketHandler(SocketConsumer consumer) {
		this.consumer = consumer;
	}

	@Override
	public void connect(String host, int port) throws Exception {
		socket = new Socket(host, port);
		outputStream = new DataOutputStream(socket.getOutputStream());
		inputStream = new DataInputStream(socket.getInputStream());
		startReaderThread();
		consumer.onConnect();
	}

	private void startReaderThread() {
		readerThread = new Thread() {
			@Override
			public void run() {
				try {
					while (true) {
						if (inputStream.available() > 0) {
							byte[] bytes = new byte[inputStream.available()];
							inputStream.read(bytes);
							consumer.onBytesReceived(bytes);
						}
					}
				} catch (Exception e) {
					Utils.logger.error("Error reading from socket", e);
				}
			}
		};
		readerThread.start();
	}

	@Override
	public void send(byte[] bytes) throws Exception {
		outputStream.write(bytes);
	}

	@Override
	public void send(String string) throws Exception {
		outputStream.writeUTF(string);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void disconnect() throws Exception {
		readerThread.stop();
		consumer.onDisonnect();
		socket.close();
	}

}

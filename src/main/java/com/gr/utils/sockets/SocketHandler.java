package com.gr.utils.sockets;

public interface SocketHandler {

	public void connect(String host, int port) throws Exception;

	public void send(byte[] bytes) throws Exception;

	public void send(String string) throws Exception;

	public void disconnect() throws Exception;
}

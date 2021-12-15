package com.gr.utils.sockets;

public interface SocketConsumer {

	public void onConnect() throws Exception;
	
	public void onDisonnect() throws Exception;
	
	public void onBytesReceived(byte[] bytes) throws Exception;

}

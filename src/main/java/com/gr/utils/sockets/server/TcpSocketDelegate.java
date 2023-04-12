package com.gr.utils.sockets.server;

public interface TcpSocketDelegate {

	void onClientConnected(ClientSession session);

	void onClientDisconnected(ClientSession session);

	void onData(byte[] packet, ClientSession session);

}

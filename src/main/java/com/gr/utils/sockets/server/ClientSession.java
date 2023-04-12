package com.gr.utils.sockets.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ClientSession {

	private SelectionKey key;
	private ByteBuffer buffer;
	private String token;
	private byte[] data;
	private SocketChannel client;
	private String ip;

	public ClientSession(SelectionKey selkey, SocketChannel client) {
		this.key = selkey;
		this.client = client;
		try {
			ip = client.getRemoteAddress().toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(byte[] bytes) throws IOException {
		Header header = new Header(bytes.length);
		byte[] fullPacket = new byte[bytes.length + Header.SIZE];

		ByteBuffer buf = ByteBuffer.wrap(fullPacket);
		buf.put(header.toBytes());
		buf.put(bytes);
		buffer = ByteBuffer.wrap(buf.array());
		client.write(buffer);
	}

	public SelectionKey getKey() {
		return key;
	}

	public void setKey(SelectionKey selkey) {
		this.key = selkey;
	}

	public ByteBuffer getBuffer() {
		return buffer;
	}

	public void setBuffer(ByteBuffer buf) {
		this.buffer = buf;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public SocketChannel getClient() {
		return client;
	}

	public void setClient(SocketChannel client) {
		this.client = client;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}

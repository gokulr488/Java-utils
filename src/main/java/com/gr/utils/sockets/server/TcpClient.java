package com.gr.utils.sockets.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TcpClient {

	private SocketChannel client;
	private ByteBuffer buffer;
	private TcpServerConfig config;
	private TcpSocketDelegate delegate;

	public TcpClient(TcpServerConfig config, TcpSocketDelegate delegate) {
		this.config = config;
		registerDeligate(delegate);
	}

	public void registerDeligate(TcpSocketDelegate delegate) {
		this.delegate = delegate;
	}

	public void connect() throws IOException {
		client = SocketChannel.open(new InetSocketAddress(config.getIpAddress(), config.getPort()));
		delegate.onClientConnected(null);
		readerThread();
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

	private void readerThread() {
		new Thread() {
			@Override
			public void run() {
				while (client.isConnected()) {
					try {
						byte[] headerBytes = read(Header.SIZE);
						if (headerBytes == null)
							break;
						Header header = new Header(headerBytes);
						byte[] packet = read(header.getLength());
						delegate.onData(packet, null);
					} catch (Exception e) {
						System.out.println("Tcp Reader failed");
						e.printStackTrace();
					}
				}
				System.out.println("Tcp Socket Reader stopped");
			}
		}.start();
	}

	private byte[] read(long length) throws IOException {
		ByteBuffer buf = ByteBuffer.allocate((int) length);
		try {
			int receivedSize = 0;
			while (receivedSize < length) {
				int size = client.read(buf);
				if (size == -1) {
					close();
					return null;
				}
				receivedSize += size;
			}
		} catch (Exception e) {
			e.printStackTrace();
			close();
		}
		return buf.array();
	}

	public void close() throws IOException {
		client.close();
		delegate.onClientDisconnected(null);
	}

}

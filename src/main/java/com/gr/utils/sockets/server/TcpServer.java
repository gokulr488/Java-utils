package com.gr.utils.sockets.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TcpServer {
	private TcpSocketDelegate delegate;
	TcpServerConfig config;
	Selector selector;
	private static final long messageTimeout = 1000;

	public void registerDeligate(TcpSocketDelegate delegate) {
		this.delegate = delegate;
	}

	public TcpServer(TcpServerConfig config, TcpSocketDelegate delegate) {
		this.config = config;
		registerDeligate(delegate);
	}

	public void startServer() throws IOException {
		selector = Selector.open();
		ServerSocketChannel serverSocket = ServerSocketChannel.open();
		serverSocket.bind(new InetSocketAddress(config.getIpAddress(), config.getPort()));
		serverSocket.configureBlocking(false);
		serverSocket.register(selector, SelectionKey.OP_ACCEPT);
		startHandlerThread();
	}

	private void startHandlerThread() {
		new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						selector.select();
						Set<SelectionKey> selectedKeys = selector.selectedKeys();
						Iterator<SelectionKey> iter = selectedKeys.iterator();
						while (iter.hasNext()) {
							SelectionKey key = iter.next();
							iter.remove();
							if (key.isAcceptable()) {
								register(key);
							}
							if (key.isReadable()) {
								readFromClient(key);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	private void readFromClient(SelectionKey key) throws IOException {
		byte[] headerBytes = read(key, Header.SIZE);
		if (headerBytes == null)
			return;
		Header header = new Header(headerBytes);
		byte[] packet = read(key, header.getLength());
		delegate.onData(packet, (ClientSession) key.attachment());
	}

	private byte[] read(SelectionKey key, long length) throws IOException {
		SocketChannel channel = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.allocate((int) length);
		long entryTime = System.currentTimeMillis();
		try {
			int receivedSize = 0;
			while (receivedSize < length) {
				// check this logic
				int size = channel.read(buffer);
				if (size == -1) {
					disconnectClient(key);
					return null;
				}
				receivedSize += size;
				if (entryTime + messageTimeout < System.currentTimeMillis()) {
					disconnectClient(key);
					return null;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			disconnectClient(key);
		}
		return buffer.array();
	}

	private void register(SelectionKey key) throws IOException {
		ServerSocketChannel serverSocket = (ServerSocketChannel) key.channel();
		SocketChannel client = serverSocket.accept();
		client.configureBlocking(false);
		ClientSession session = new ClientSession(key, client);
		client.register(selector, SelectionKey.OP_READ, session);
		delegate.onClientConnected(session);
	}

	public void disconnectClient(SelectionKey key) {
		ClientSession session = (ClientSession) key.attachment();
		SocketChannel client = (SocketChannel) key.channel();
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		key.cancel();
		delegate.onClientDisconnected(session);
	}

}

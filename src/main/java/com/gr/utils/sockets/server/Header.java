package com.gr.utils.sockets.server;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class Header {
	public static final int SIZE = 8;
	private long length;

	public Header(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		length = byteBuffer.getLong();
	}

	public byte[] toBytes() {
		ByteBuffer byteBuffer = ByteBuffer.allocate(SIZE);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.putLong(length);
		return byteBuffer.array();
	}
	
	public static void main(String[] args) {
		Header header =new Header(951965195);
		byte[] byt = header.toBytes();
		System.out.println(Arrays.toString(byt));
	}

	public Header(long length) {
		this.length = length;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

}

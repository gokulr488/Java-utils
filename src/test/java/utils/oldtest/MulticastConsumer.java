package utils.oldtest;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastConsumer extends Thread {

	public static void main(String[] args) {
		MulticastConsumer server = new MulticastConsumer();
		server.start();
	}

	protected MulticastSocket socket = null;
	protected byte[] buf = new byte[256];

	@Override
	public void run() {
		try {
			socket = new MulticastSocket(4446);
			InetAddress group = InetAddress.getByName("230.0.2.169");
			socket.joinGroup(group);
			while (socket.isBound()) {
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				String received = new String(packet.getData(), 0, packet.getLength());

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}

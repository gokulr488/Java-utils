/**
 * 
 */
package utils.oldtest;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author Gokul
 *
 */
public class UdpServer {

	private DatagramSocket socket;
	private InetAddress group;
	private byte[] buf = new byte[256];

	public UdpServer() throws Exception {
		socket = new DatagramSocket();
	}

	public static void main(String[] args) throws Exception {
		UdpServer server = new UdpServer();
		server.run();
	}

	public void run() throws Exception {
		for (int i = 0; i < 256; i++) {
			buf[i] = (byte) i;
		}
		while (true) {
			group = InetAddress.getByName("230.0.0.0");
			DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
			socket.send(packet);
			Thread.sleep(1000);
			System.out.println("Publishing data");
		}
	}
}

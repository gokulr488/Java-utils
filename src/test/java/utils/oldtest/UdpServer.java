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

	public UdpServer() throws Exception {
		socket = new DatagramSocket();
	}

	public static void main(String[] args) throws Exception {
		UdpServer server = new UdpServer();
		server.run();
	}

	public void run() throws Exception {
		String test = "dummy data";
		byte[] buf = test.getBytes();
		group = InetAddress.getByName("230.0.2.169");
		DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
		while (socket.isBound()) {
			socket.send(packet);
			Thread.sleep(1000);
			System.out.println("Publishing data");
		}
	}
}

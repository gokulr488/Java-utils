/**
 * 
 */
package utils.oldtest;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.util.Arrays;

/**
 * @author Gokul
 *
 */
public class UdpClient {

	protected MulticastSocket socket = null;
	protected byte[] buf = new byte[25];

	public static void main(String[] args) throws Exception {
		UdpClient cli = new UdpClient();
		cli.startClient();
	}

	public void startClient() throws Exception {
		socket = new MulticastSocket(4446);
		socket.joinGroup(InetAddress.getByName("230.0.2.169"));
		while (socket.isBound()) {
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			socket.receive(packet);
			String st = new String(packet.getData());
			System.out.println(st);
		}

	}

}

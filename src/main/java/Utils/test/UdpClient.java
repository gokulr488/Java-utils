/**
 * 
 */
package Utils.test;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

/**
 * @author Gokul
 *
 */
public class UdpClient {

	protected MulticastSocket socket = null;
	protected byte[] buf = new byte[256];

	public static void main(String[] args) throws Exception {
		UdpClient cli = new UdpClient();
		cli.startClient();
	}

	public void startClient() throws Exception {
		socket = new MulticastSocket(4446);
		InetAddress group = InetAddress.getByName("230.0.0.0");
		socket.joinGroup(group);
		while (true) {
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			socket.receive(packet);
			System.out.println(Arrays.toString(packet.getData()));
		}

	}

}

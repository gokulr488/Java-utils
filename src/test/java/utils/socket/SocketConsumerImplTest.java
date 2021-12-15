package utils.socket;

import java.util.Arrays;

import com.gr.utils.Utils;
import com.gr.utils.sockets.SocketConsumer;
import com.gr.utils.sockets.SocketHandler;
import com.gr.utils.sockets.TcpSocketHandler;

public class SocketConsumerImplTest implements SocketConsumer {

	private SocketHandler tcpHandler;

	public SocketConsumerImplTest() {
		tcpHandler = new TcpSocketHandler(this);
	}

	public void startTest() {
		try {
			tcpHandler.connect("localhost", 8235);

		} catch (Exception e) {
			Utils.logger.error("", e);
		}
	}

	@Override
	public void onConnect() throws Exception {
		System.out.println("connected");
	}

	@Override
	public void onDisonnect() throws Exception {
		System.out.println("Disconnected");
	}

	@Override
	public void onBytesReceived(byte[] bytes) throws Exception {
		System.out.println(Arrays.toString(bytes));
	}

}

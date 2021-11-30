package Utils.sockets;

public interface SocketAdaptor {

	public void onConnect() throws Exception;
	
	public void onBytesReceived(byte[] bytes) throws Exception;

}

package Utils.linux;

public class ScpConfig {
	private String userName;
	private String ipAddress;
	private String password;
	private String destinationPath;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDestinationPath() {
		return destinationPath;
	}

	public void setDestinationPath(String destinationPath) {
		this.destinationPath = destinationPath;
	}

	@Override
	public String toString() {
		return "ScpConfig [userName=" + userName + ", ipAddress=" + ipAddress + ", password=" + password
				+ ", destinationPath=" + destinationPath + "]";
	}
}

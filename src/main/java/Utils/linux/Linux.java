package Utils.linux;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class Linux {

	private static Logger logger = LoggerFactory.getLogger(Linux.class);

	/**
	 * @param command
	 * @param directory
	 * @return
	 */
	public static List<String> executeInDirectory(String command, String directory) {
		try {
			if (directory == null) {
				logger.info("Executing command :{}", command);
			} else {
				logger.info("Executing command: {} in directory: {}", command, directory);
			}
			File dir = null;
			if (directory != null) {
				dir = new File(directory);
			}
			Process process = Runtime.getRuntime().exec(command, null, dir);
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			List<String> output = new ArrayList<String>();
			String outputLine = "";
			while ((outputLine = br.readLine()) != null) {
				logger.debug(outputLine);
				output.add(outputLine);
			}
			logger.info("Waiting for Execution To Finish");
			process.waitFor();
			logger.info("Execution completed with exit code : {} ", process.exitValue());
			process.destroy();
			return output;
		} catch (Exception e) {
			logger.error("Unable to execute command {}", command, e);
			return null;
		}
	}

	/**
	 * @param command
	 * @return
	 */
	public static List<String> execute(String command) {
		return executeInDirectory(command, null);
	}

	/**
	 * @param command
	 * @return
	 */
	public static List<String> executeOnWindows(String command) {
		command = "cmd /c " + command;
		return executeInDirectory(command, null);
	}

	public static void scp(ScpConfig scpConfig, String fileName) {

		JSch jSch = new JSch();
		try {
			Session session = jSch.getSession(scpConfig.getUserName(), scpConfig.getIpAddress());
			session.setPassword(scpConfig.getPassword());
			session.setConfig("StrictHostKeyChecking", "no");
			session.connect();

			ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
			sftp.connect();
			logger.info("SFTP connected. Attempting to send {} to {}", fileName, scpConfig.getIpAddress());
			sftp.put(fileName, scpConfig.getDestinationPath());
			logger.info("Succesfully transfered file: {} over SFTP to :{}", fileName, scpConfig.getIpAddress());
			sftp.disconnect();
			session.disconnect();

		} catch (JSchException | SftpException e) {
			logger.error("Unable to SCP file: {} with : {}", fileName, scpConfig, e);
		}
	}

}

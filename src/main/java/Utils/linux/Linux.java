package Utils.linux;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
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
			List<String> output = new ArrayList<>();
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

	public static int executeApplication(String command) {
		CommandLine cmdLine = new CommandLine(command);
		DefaultExecutor executor = new DefaultExecutor();
		executor.setExitValue(1);
		try {
			logger.info("Executing : {}", cmdLine);
			return executor.execute(cmdLine);
		} catch (IOException e) {
			logger.error("Unable to execute command : {}", cmdLine, e);
			return 0;
		}

	}

	public static void executeShellFile(String shellFile) {

		try {
			List<String> cmdList = new ArrayList<String>();
			cmdList.add("sh");
			cmdList.add(shellFile);
			ProcessBuilder pb = new ProcessBuilder(cmdList);
			// pb=pb.inheritIO();
			logger.info("Executing Script file {}", shellFile);
			Process p = pb.inheritIO().start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				logger.info(line);
			}
			p.waitFor();
		} catch (IOException e) {
			logger.error("Unable to execute shell script file {}", shellFile, e);
		} catch (InterruptedException e) {
			logger.error("Unable to execute shell script file {}", shellFile, e);
		}
	}

	public static int executeApplication(String app, String directory, List<String> arguments) {
		CommandLine cmdLine = new CommandLine(app);
		for (String arg : arguments) {
			cmdLine.addArgument(arg);
		}
		DefaultExecutor executor = new DefaultExecutor();
		// executor.setWorkingDirectory(new File(directory));
		executor.setExitValue(1);
		try {
			logger.info("Executing : {}", cmdLine);
			return executor.execute(cmdLine);
		} catch (IOException e) {
			logger.error("Unable to execute command : {}", cmdLine, e);
			return 0;
		}
	}

}

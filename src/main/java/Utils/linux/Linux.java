package Utils.linux;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Linux {
	private static Logger logger = LoggerFactory.getLogger(Linux.class);

	public static List<String> executeInDirectory(String command, String directory) {
		try {
			if (directory == null) {
				logger.info("Executing command :{}", command);
			} else {
				logger.info("Executing command: {} in directory: {}", command, directory);
			}
			Process process = Runtime.getRuntime().exec(command);
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

	public static List<String> execute(String command) {
		return executeInDirectory(command, null);
	}

	public static List<String> executeOnWindows(String command) {
		command = "cmd /c " + command;
		return executeInDirectory(command, null);
	}

}

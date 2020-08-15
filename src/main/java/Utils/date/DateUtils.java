package Utils.date;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <h1>The following date and time operations are available</h1>
 * 
 * 1. Convert long/epoch value to formatted String
 * <p>
 * 2. Convert String value of date/time to any other String format
 * <p>
 * 3. 
 * 
 * @author Gokul
 *
 */
public class DateUtils {

	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

	/**
	 * Converts the long\epoch value to the provided date format and returns as
	 * String
	 */
	public static String toString(long longValue, String dateFormat) {
		Date date = new Date(longValue);
		Format format = new SimpleDateFormat(dateFormat);
		return format.format(date);
	}

	/**
	 * String value of date can be converted to a different date format
	 * <p>
	 * Provide the date to be converted, String format of the input date and
	 * expected string format of output
	 * 
	 * @exception ParseException
	 */
	public static String changeFormat(String date, String inputFormat, String outputFormat) {
		Date dateObj = toDate(date, inputFormat);
		return toString(dateObj.getTime(), outputFormat);

	}

	/**
	 * Get the string value of current Time in the specified format
	 */
	public static String getCurrentTimeAs(String format) {
		return toString(new Date().getTime(), format);
	}

	/**
	 * Returns a date object for the provided String.
	 * <p>
	 * date format of the input string needs to be provided
	 * 
	 * @exception ParseException
	 */
	public static Date toDate(String dateString, String dateFormat) {
		try {
			return new SimpleDateFormat(dateFormat).parse(dateString);
		} catch (ParseException e) {
			logger.error("Unable to parse {} with input format as {}", dateString, dateFormat, e);
			return null;
		}
	}

}

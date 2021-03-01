package Utils.test;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class HtmlParseTest {
	public static void main(String[] args) {
		System.out.println(parse(html));
	}

	public static String parse(String html) {// Header need to be configurable
		Document document = Jsoup.parse(html);
		Elements rows = document.select("tr");
		JsonArray json = new JsonArray();

		if (rows != null && !rows.isEmpty()) {
			List<String> header = populateHeader(rows);
			for (Element row : rows) {
				Elements dataRows = row.select("td");
				if (null != dataRows && dataRows.size() == header.size()) {
					JsonObject field = new JsonObject();
					for (int i = 0; i < dataRows.size(); i++) {
						field.addProperty(header.get(i), dataRows.get(i).text());
					}
					json.add(field);
				}
			}
		}

		return json.toString();
	}

	private static List<String> populateHeader(Elements rows) {
		for (Element row : rows) {
			Elements headerElement = row.select("th");
			if (headerElement != null && !headerElement.isEmpty()) {
				return getHeaderFromTable(headerElement);
			}
		}
		return createDummyHeader(rows.first().children());

	}

	private static List<String> createDummyHeader(Elements dataColumns) {
		List<String> header = new ArrayList<>();
		for (int i = 0; i < dataColumns.size(); i++) {
			header.add("column" + i);
		}
		return header;
	}

	private static List<String> getHeaderFromTable(Elements headers) {
		List<String> head = new ArrayList<>();
		if (null != headers) {
			for (Element header : headers) {
				head.add(header.text());
			}
		}
		return head;
	}

	static String html = "<table class=\"MainTable\" id=\"scrollNumber1\">\r\n"
			+ "	<tr class=\"MainTableTopRow\">\r\n"
			+ "		<td class=\"MainTableTopRowLeftColumn\">Manage Contacts</td>\r\n"
			+ "		<td class=\"MainTableTopRowRightColumn\">\r\n"
			+ "		<table class=\"TopStatusBar\">\r\n"
			+ "			<tr>\r\n"
			+ "				<td>DOE, JOHN M 50 years</td>\r\n"
			+ "				<td>&nbsp;</td>\r\n"
			+ "				<td style=\"text-align: right\"><a   target=\"_blank\" href=\"http://www.oscarmanual.org/search?SearchableText=contact\">Help</a> | <a\r\n"
			+ "					href=\"javascript:popupStart(300,400,'About.jsp')\">About</a> | <a\r\n"
			+ "					href=\"javascript:popupStart(300,400,'License.jsp')\">License</a></td>\r\n"
			+ "			</tr>\r\n"
			+ "		</table>\r\n"
			+ "		</td>\r\n"
			+ "	</tr>\r\n"
			+ "	<tr>\r\n"
			+ "		<td class=\"MainTableLeftColumn\" valign=\"top\">&nbsp; <a\r\n"
			+ "			href=\"javascript:window.close();\">Close Window</a></td>\r\n"
			+ "		<td valign=\"top\" class=\"MainTableRightColumn\">\r\n"
			+ "\r\n"
			+ "		<form method=\"post\" name=\"contactForm\" id=\"contactForm\" action=\"Contact.do\">\r\n"
			+ "			<input type=\"hidden\" name=\"method\" value=\"saveManage\"/>\r\n"
			+ "			<input type=\"hidden\" name=\"demographic_no\" value=\"1\"/>\r\n"
			+ "\r\n"
			+ "			<b>Personal Contacts:</b>\r\n"
			+ "			<br />\r\n"
			+ "			<div id=\"contact_container\"></div>\r\n"
			+ "			<input type=\"hidden\" id=\"contact_num\" name=\"contact_num\" value=\"0\"/>\r\n"
			+ "			<a href=\"#\" onclick=\"addContact();\">[ADD]</a>\r\n"
			+ "\r\n"
			+ "			<br/><br/>\r\n"
			+ "			<b>Professional Contacts:</b>\r\n"
			+ "			<br />\r\n"
			+ "			<div id=\"procontact_container\"></div>\r\n"
			+ "			<input type=\"hidden\" id=\"procontact_num\" name=\"procontact_num\" value=\"0\"/>\r\n"
			+ "			<a href=\"#\" onclick=\"addProContact();\">[ADD]</a>\r\n"
			+ "\r\n"
			+ "			<br/>\r\n"
			+ "\r\n"
			+ "			<input type=\"submit\" value=\"Submit\" />\r\n"
			+ "			&nbsp;&nbsp;\r\n"
			+ "			<input type=\"button\" name=\"cancel\" value=\"Cancel\" onclick=\"window.close()\" />\r\n"
			+ "\r\n"
			+ "		</form>\r\n"
			+ "\r\n"
			+ "	</td>\r\n"
			+ "	</tr>\r\n"
			+ "	<tr>\r\n"
			+ "		<td class=\"MainTableBottomRowLeftColumn\">&nbsp;</td>\r\n"
			+ "		<td class=\"MainTableBottomRowRightColumn\" valign=\"top\">&nbsp;</td>\r\n"
			+ "	</tr>\r\n"
			+ "</table>";

}

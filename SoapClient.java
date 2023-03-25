import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import org.w3c.dom.*;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SoapClient {
	public static void main(String[] args) throws IOException {
		// Code to make a webservice HTTP request
		String responseString = "";
		String outputString = "";
		String wsEndPoint = "https://grmenarini--devart.sandbox.my.salesforce.com/services/Soap/c/56.0/00D9E000000AC8D";
		URL url = new URL(wsEndPoint);
		URLConnection connection = url.openConnection();
		HttpURLConnection httpConn = (HttpURLConnection) connection;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String xmlInput = " <soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:enterprise.soap.sforce.com\"><soapenv:Header></soapenv:Header><soapenv:Body><ns1:login xmlns:ns1=\"urn:enterprise.soap.sforce.com\"><ns1:username>giuseppe.villella@tengroup.it.devart</ns1:username><ns1:password>MeNarIni2022!!h7JdP56O8bDTQxdt2kq6puvB</ns1:password></ns1:login></soapenv:Body></soapenv:Envelope>";
		byte[] buffer = new byte[xmlInput.length()];
		buffer = xmlInput.getBytes();
		bout.write(buffer);
		byte[] b = bout.toByteArray();
		String SOAPAction = "urn:enterprise.soap.sforce.com/Soap/loginRequest";
		httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		httpConn.setRequestProperty("SOAPAction", SOAPAction);
		httpConn.setRequestMethod("POST");
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		OutputStream out = httpConn.getOutputStream();
		// Write the content of the request to the outputstream of the HTTP
		// Connection.
		out.write(b);
		out.close();
		// Ready with sending the request.
		// Read the response.
		InputStreamReader isr = new InputStreamReader(httpConn.getInputStream(), Charset.forName("UTF-8"));
		BufferedReader in = new BufferedReader(isr);
		// Write the SOAP message response to a String.
		while ((responseString = in.readLine()) != null) {
			outputString = outputString + responseString;
		}
		// Write the SOAP message formatted to the console.
		// String formattedSOAPResponse = formatXML(outputString);
		getElementByTag("sessionId", outputString);
		// System.out.println(formattedSOAPResponse);
	}

	// format the XML in pretty String
	// private static String formatXML(String unformattedXml) {
	// 	try {
	// 		Document document = parseXmlFile(unformattedXml);
	// 		TransformerFactory transformerFactory = TransformerFactory.newInstance();
	// 		transformerFactory.setAttribute("indent-number", 3);
	// 		Transformer transformer = transformerFactory.newTransformer();
	// 		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	// 		DOMSource source = new DOMSource(document);
	// 		StreamResult xmlOutput = new StreamResult(new StringWriter());
	// 		transformer.transform(source, xmlOutput);
	// 		return xmlOutput.getWriter().toString();
	// 	} catch (TransformerException e) {
	// 		throw new RuntimeException(e);
	// 	}
	// }

	// parse XML
	// private static Document parseXmlFile(String in) {
	// 	try {
	// 		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	// 		DocumentBuilder db = dbf.newDocumentBuilder();
	// 		InputSource is = new InputSource(new StringReader(in));
	// 		return db.parse(is);
	// 	} catch (IOException | ParserConfigurationException | SAXException e) {
	// 		throw new RuntimeException(e);
	// 	}
	// }

	private static String getElementByTag(String in, String paylod) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(paylod)));
			Element rootElement = document.getDocumentElement();
			NodeList nodes = rootElement.getElementsByTagName(in);
			NodeList subList = nodes.item(nodes.getLength() - 1).getChildNodes();
			if (subList != null && subList.getLength() > 0) {
				System.out.println(subList.item(subList.getLength() - 1).getNodeValue());
			}

			return "db.parse(is)";
		} catch (IOException | ParserConfigurationException | SAXException e) {
			throw new RuntimeException(e);
		}
	}
}
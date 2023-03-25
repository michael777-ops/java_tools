import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import org.w3c.dom.*;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SoapClient2 {
	public static void main(String[] args) throws IOException {
		
		try{
			String responseString = "";
			String outputString = "";
			String sessionId = "";
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
			out.write(b);
			out.close();
			InputStreamReader isr = new InputStreamReader(httpConn.getInputStream(), Charset.forName("UTF-8"));
			BufferedReader in = new BufferedReader(isr);
			while ((responseString = in.readLine()) != null) {
				outputString = outputString + responseString;
			}
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(new InputSource(new StringReader(outputString)));
				Element rootElement = document.getDocumentElement();
				NodeList nodes = rootElement.getElementsByTagName("sessionId");
				NodeList subList = nodes.item(nodes.getLength() - 1).getChildNodes();
				if (subList != null && subList.getLength() > 0) {
					sessionId = subList.item(subList.getLength() - 1).getNodeValue();
				}
			} catch ( ParserConfigurationException e) {
				throw new RuntimeException(e);
			} catch (SAXException e) {
				throw new RuntimeException(e);
			}
	
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

}

}
import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import org.json.JSONObject;
import javax.net.ssl.HttpsURLConnection;

 public class TekaAuth {
     public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException {
         String POST_PARAMS = "grant_type=password&client_id=api-test-client-f806d166-8efc-4b6a-8b6b-02589f8d7374&client_secret=EDD14638674BF3781062CF562E2FF930DA568403C4FCAFA5E437CC54451A8E64&username=testaccount@test.gr&password=jS&x4O&LG!#bF";
         URL obj = new URL("https://api.e-signit.gr/authenticate");
         HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
         con.setSSLSocketFactory(RelaxedSSLContext.getInstance().getSocketFactory());
         con.setHostnameVerifier(RelaxedSSLContext.localhostValid);
         con.setRequestMethod("POST");
         con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

         con.setDoOutput(true);

         OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
         writer.write(POST_PARAMS);
         writer.flush();


          // Get the response code
          int responseCode = con.getResponseCode();

          // Check the response code
          if (responseCode == 200) {
              // The request was successful
              System.out.println("The request was successful");
          } else {
              // The request failed
              System.out.println("The request failed with response code " + responseCode);
              System.out.println("The request failed with message => " + con.getResponseMessage());
          }
 
          // Close the connection
          con.disconnect();
     }

 }

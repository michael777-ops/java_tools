// import java.io.*;
// import java.net.*;
// import java.security.KeyManagementException;
// import java.security.NoSuchAlgorithmException;
// import java.util.Base64;
// // import org.json.JSONObject;

// import javax.net.ssl.HttpsURLConnection;

// public class TekaAuth {
//     public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException {

//         String POST_PARAMS = "grant_type=password&client_id=api-test-client-f806d166-8efc-4b6a-8b6b-02589f8d7374&client_secret=EDD14638674BF3781062CF562E2FF930DA568403C4FCAFA5E437CC54451A8E64&username=testaccount@test.gr&password=jS&x4O&LG!#bF";
//         URL obj = new URL("https://api.e-signit.gr/authenticate");

//         HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
//         con.setSSLSocketFactory(RelaxedSSLContext.getInstance().getSocketFactory());
//         con.setHostnameVerifier(RelaxedSSLContext.localhostValid);
//         con.setRequestMethod("POST");
//         con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

//         con.setDoOutput(true);

//         OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
//         writer.write(POST_PARAMS);
//         writer.flush();


//          // Get the response code
//          int responseCode = con.getResponseCode();

//          // Check the response code
//          if (responseCode == 200) {
//              // The request was successful
//              System.out.println("The request was successful");
//          } else {
//              // The request failed
//              System.out.println("The request failed with response code " + responseCode);
//              System.out.println("The request failed with message => " + con.getResponseMessage());
//          }
 
//          // Close the connection
//          con.disconnect();
//     }

// }



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


public class TekaAuth {

    public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException {
        // Crea una nuova URL per l'API
        URL url = new URL("https://api.e-signit.gr/authenticate");

        // Crea una nuova connessione HTTP
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setDoOutput(true);
        connection.setSSLSocketFactory(RelaxedSSLContext.getInstance().getSocketFactory());
        connection.setHostnameVerifier(RelaxedSSLContext.localhostValid);

        // Imposta i parametri della richiesta
        String grant_type = "password";
        String client_id = "api-test-client-f806d166-8efc-4b6a-8b6b-02589f8d7374";
        String client_secret = "EDD14638674BF3781062CF562E2FF930DA568403C4FCAFA5E437CC54451A8E64";
        String username = "testaccount@test.gr";
        String password = "jS&x4O&LG!#bF";

        // Crea una stringa URL-encoded con i parametri
        String data = "grant_type=" + URLEncoder.encode(grant_type, "UTF-8") + "&client_id=" + URLEncoder.encode(client_id, "UTF-8") + "&client_secret=" + URLEncoder.encode(client_secret, "UTF-8")+ "&username=" + URLEncoder.encode(username, "UTF-8")+ "&password=" + URLEncoder.encode(password, "UTF-8");

        // Invia i parametri alla richiesta
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(data);
        wr.flush();
        wr.close();

        // Leggi la risposta della richiesta
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String response = reader.readLine();
            System.out.println(response);
        } else {
            System.out.println("Errore: " + responseCode);
        }
    }
}
import java.io.*;
import java.net.*;
import java.util.Base64;
// import org.json.JSONObject;

public class TokenOauth {
    public static void main(String[] args) throws IOException {

        String username = "myserviceapi";
        String password = "LEG8_UVuPGQ05jeLRa_ZROop2";
        String userpass = username + ":" + password;

        String basicAuth = "Basic " + Base64.getEncoder().encodeToString(userpass.getBytes());

        String POST_PARAMS = "grant_type=client_credentials";
        URL obj = new URL("https://oauthasservices-v9o42vbtez.eu2.hana.ondemand.com/oauth2/api/v1/token");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        // con.setRequestProperty("Content-Type", "application/json;odata=verbose");
        con.setRequestProperty("Authorization", basicAuth);

        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //get token , da cambiare bozza per libreria mancante JSON 
            String[] arrOfStr = response.toString().split("\"");

            System.out.println(arrOfStr[3]);
        } else {
            System.out.println("POST request not worked");
        }

    }

}
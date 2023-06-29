import java.io.*;
import java.net.*;
import java.util.Base64;
// import org.json.JSONObject;

public class TokenOauthTeka {
    public static void main(String[] args) throws IOException {

        String POST_PARAMS = "grant_type=client_credentials&client_id=sap45879aff-add6-4cf0-af57-bcbf74ad8f77&client_secret=a7x888a7-7ded-4f3c-904c-ba9f804be6ff&scope=service";
        String oAuthToken;
        String  SADID;
        URL obj = new URL("https://csc-test.adacom.com/oauth2/token");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

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

            // get token , da cambiare bozza per libreria mancante JSON
            String[] arrOfStr = response.toString().split("\"");
            oAuthToken = arrOfStr[3];

            System.out.println("token => ".concat(oAuthToken));

            // nea post call for to get authorization
            String jsonString = "{ \"credentialID\": \"adobe2NP\", \"numSignatures\": 1, \"hash\": [\"qa6K085XEXVu/TgZpBAKrGx/ysD/MDX/OYnuUtp5s9Q=\"] ,\"OTP\": \"318606\", \"PIN\": \"R@ndom123!\"}";
            URL objAuth = new URL("https://csc-test.adacom.com/csc/v1/Credentials/authorize");
            HttpURLConnection conAuth = (HttpURLConnection) objAuth.openConnection();
            conAuth.setRequestMethod("POST");
            conAuth.setRequestProperty("Authorization", "Bearer ".concat(oAuthToken));
            conAuth.setRequestProperty("Content-Type", "application/json");

            // For POST only - START
            conAuth.setDoOutput(true);
            OutputStream osAuth = conAuth.getOutputStream();
            osAuth.write(jsonString.getBytes());
            osAuth.flush();
            osAuth.close();

            int responseCodeAuth = conAuth.getResponseCode();
            System.out.println("POST Response Code Auth :: " + responseCodeAuth);

            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader inAuth = new BufferedReader(new InputStreamReader(
                        conAuth.getInputStream()));
                String inputLineAuth;
                StringBuffer responseAuth = new StringBuffer();

                while ((inputLineAuth = inAuth.readLine()) != null) {
                    responseAuth.append(inputLineAuth);
                }
                inAuth.close();

                // get SADID
                String[] arrOfStrAuth = responseAuth.toString().split("\"");
                SADID = arrOfStrAuth[3];
                System.out.println("SADID => ".concat(SADID));

            } else {
                System.out.println("POST request not worked");
            }

        } else {
            System.out.println("POST request not worked");
        }

    }

}
package il.ac.haifa.ClinicSystem;
import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;

public class HttpApiTester {
    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    public void sms() {

        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    @Override
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HostnameVerifier allHostsValid = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        } catch (GeneralSecurityException e) {
            System.out.println(e.getMessage());
        }

        try {
            StringBuilder sendString = new StringBuilder();
            String username = "john";
            String password = "Xc3ffs";
            String messagetype = "SMS:TEXT";
            String httpUrl = "https://127.0.0.1:9508/";
            String recipient = URLEncoder.encode("+972584513493", "UTF-8");
            String messagedata = URLEncoder.encode("TestMessage", "UTF-8");

            sendString.append(httpUrl).append("api?action=sendmessage").
                    append("&username=").append(username).append("&password=").
                    append(password).append("&recipient=").append(recipient).
                    append("&messagetype=").append(messagetype).append("&messagedata=").
                    append(messagedata);

            System.out.println("Sending request: " + sendString.toString());

            URL url = new URL(sendString.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader br = null;
            System.out.println("Http response received: ");
            if (con.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String strCurrentLine;
                while ((strCurrentLine = br.readLine()) != null) {
                    System.out.println(strCurrentLine);
                }
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String strCurrentLine;
                while ((strCurrentLine = br.readLine()) != null) {
                    System.out.println(strCurrentLine);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
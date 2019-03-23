package somantic.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class HttpConnector {

    private String login;
    private String password;
    private String urlAddress;
    private final String USER_AGENT = "Mozilla/5.0";

    HttpConnector(String login, String password, String url) {
        this.login = login;
        this.password = password;
        this.urlAddress = url;
    }

    HttpConnector() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    public String sendGet(int counter) throws IOException {
        URL obj = new URL(urlAddress + "?login=" + login + "&password=" + password + "&counter=" + counter);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + urlAddress);
        System.out.println("Response Code : " + responseCode);
        StringBuffer response = read(con);
        return response.toString();
    }

    public String sendPost(String message) throws IOException {
        URL url = new URL(this.urlAddress);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        String urlParameters = "login=" + login + "&password=" + password + "&message=" + message;
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
        StringBuffer response = read(con);
        return response.toString();
    }

    private StringBuffer read(HttpURLConnection con) throws IOException {
        StringBuffer response;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        return response;
    }

}

package ui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.AuthData;
import model.LoginRequest;
import spark.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ServerFacade {
    private static String serverUrl = null;
    public ServerFacade (int port){
        serverUrl = "http://localhost:" + port;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        PreloginUI p = new PreloginUI(serverUrl);
        p.start();
    }

    public void getInput(){
        //when called prints postlogin ui page
        System.out.println("Welcome to CS240 Chess Game!");
        System.out.println("Enter in a command or 'help' for your options");

        //take in input and form it into a list. Based on the first item in the list, that is the keyword
        Scanner in = new Scanner(System.in);

        String userInput = in.nextLine();
        String[] inputList = userInput.split(" ");
        while (!inputList[0].equalsIgnoreCase("quit")) {
    }
    private void makeRequest(String serverEnd, String method, byte[] request, String authToken){
        try {
            URI uri = new URI(serverUrl + serverEnd);
            HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
            http.setRequestMethod(method);

            http.addRequestProperty("Authorization", authToken);
            // Send post request
            http.setDoOutput(true);
            OutputStream wr = http.getOutputStream();
            wr.write(request);
            wr.flush();
            wr.close();

            int responseCode = http.getResponseCode();
            String responseMessage;
            if(responseCode == 401){
                responseMessage = "Error: unauthorized";
                System.out.println("Response Message : " + responseMessage);
            } else if (responseCode == 400) {
                responseMessage = "Error: bad request";
                System.out.println("Response Message : " + responseMessage);
            }else if(responseCode == 403) {
                responseMessage = "Error: already taken";
                System.out.println("Response Message : " + responseMessage);
            }else {
                String result = "";
                try (InputStream respBody = http.getInputStream()) {
                    result = IOUtils.toString(respBody);
                    System.out.println(result);
                    //return json object?
                }
            }
        }
        catch(Exception r) {
            System.out.println("Exception Message : " + r.getMessage());
        }
    }
}

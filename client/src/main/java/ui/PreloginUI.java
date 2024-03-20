package ui;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import com.google.gson.Gson;
import spark.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;

import model.*;
import service.*;

public class PreloginUI {

    public PreloginUI() {

    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        PreloginUI p = new PreloginUI();
        p.start();
    }

    //start function
    public void start() throws IOException, URISyntaxException {
        //print out prelogin ui to the screen
        System.out.println("Welcome to CS240 Chess Game!");
        System.out.println("Enter in a command or 'help' for your options");

        //take in input and form it into a list. Based on the first item in the list, that is the keyword
        Scanner in = new Scanner(System.in);

        String userInput = in.nextLine();
        String[] inputList = userInput.split(" ");

        while (!inputList[0].equalsIgnoreCase("quit")) {

            if (inputList[0].equalsIgnoreCase("help")) {
                System.out.println(getHelp());
            }

            //else if list[1] == register
            else if (inputList[0].equalsIgnoreCase("register")) {
                if (inputList.length != 4) {
                    System.out.println("insufficient arguments");
                    System.out.println(getHelp());
                } else {
                    try {
                        URI uri = new URI("http://localhost:8080/user");
                        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
                        http.setRequestMethod("POST");

                        UserData request = new UserData(inputList[1], inputList[2], inputList[3]);
                        // Send post request
                        http.setDoOutput(true);
                        OutputStream wr = http.getOutputStream();
                        wr.write(new Gson().toJson(request).getBytes(StandardCharsets.UTF_8));
                        wr.flush();
                        wr.close();

                        int responseCode = http.getResponseCode();
                        String responseMessage = http.getResponseMessage();
                        if(responseCode == 403){
                            responseMessage = "Error: already taken";
                            System.out.println("Response Message : " + responseMessage);
                        } else if (responseCode == 400) {
                            responseMessage = "Error: bad request";
                            System.out.println("Response Message : " + responseMessage);
                        }
                        else {
                            String result = "";
                            try (InputStream respBody = http.getInputStream()) {
                                result = IOUtils.toString(respBody);
                                System.out.println(result);
                                var user = new Gson().fromJson(result, AuthData.class);
                                PostloginUI p = new PostloginUI(user.authToken());
                                p.postLogin();
                            }
                        }
                    }
                    catch(Exception r){
                        System.out.println("Exception Message : " + r.getMessage());
                    }
                }
            }
            //            returns user to PostLoginUI

            // else if list[1] == login
            else if (inputList[0].equalsIgnoreCase("login")) {
                if (inputList.length != 3) {
                    System.out.println("insufficient arguments");
                    System.out.println(getHelp());
                }
                URI uri = new URI("http://localhost:8080/session");
                HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
                http.setRequestMethod("POST");

                String urlParameters = "{\"username\":" + inputList[1] + "}";
                LoginRequest request = new LoginRequest(inputList[1], inputList[2]);
                // Send post request
                http.setDoOutput(true);
                OutputStream wr = http.getOutputStream();
                wr.write(new Gson().toJson(request).getBytes(StandardCharsets.UTF_8));
                wr.flush();
                wr.close();

                int responseCode = http.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + uri);
                System.out.println("Post parameters : " + urlParameters);
                System.out.println("Response Code : " + responseCode);


                // Make the request
                //http.connect();

                // Output the response body
                try (InputStream respBody = http.getInputStream()) {
                    String result = IOUtils.toString(respBody);
                    //InputStreamReader inputStreamReader = new InputStreamReader(respBody);
                    System.out.println(result);
                }
                //      call login function in UserUI
//sends input data to server.java
                //prints logged in as username if success
                //prints authtoken
                //returns user to PostLoginUI

            } else {
                System.out.println("I'm sorry, I did not understand your request. Please try again.");
                System.out.println(getHelp());
            }
            userInput = in.nextLine();
            inputList = userInput.split(" ");
        }
    }

    private String getHelp() {
        return """
                'register' <Username> <Password> <Email> = register account
                'login' <Username> <Password> = login to your account
                'quit' = exit chess game
                'help' = show options
                """;
    }
}

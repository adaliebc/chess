package ui;

import chess.ChessGame;
import com.google.gson.Gson;
import model.*;

import spark.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class PostloginUI {
    public String authToken;
    public PostloginUI (String inputAuthToken){
        authToken = inputAuthToken;
    }
    public void postLogin () throws IOException, URISyntaxException {
        //when called prints postlogin ui page
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
            // if logout
            else if (inputList[0].equalsIgnoreCase("logout")){
                try {
                    URI uri = new URI("http://localhost:8080/session");
                    HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
                    http.setRequestMethod("DELETE");
                    http.addRequestProperty("Authorization", authToken);

                    // Send get request
                    http.connect();

                    int responseCode = http.getResponseCode();
                    String responseMessage;
                    if(responseCode == 401){
                        responseMessage = "Error: unauthorized";
                        System.out.println("Response Message : " + responseMessage);
                    } else if (responseCode == 500) {
                        responseMessage = "Error: description";
                        System.out.println("Response Message : " + responseMessage);
                    }
                    else {
                        try (InputStream respBody = http.getInputStream()) {
                            System.out.println("Successfully logged out");
                            break;
                            }
                        }
                } catch (Exception r) {
                    System.out.println("Exception Message : " + r.getMessage());
                }
            }
            //if create game
            else if (inputList[0].equalsIgnoreCase("create")) {
                if (inputList.length != 2) {
                    System.out.println("insufficient arguments");
                    System.out.println(getHelp());
                } else {
                    try {
                        URI uri = new URI("http://localhost:8080/game");
                        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
                        http.setRequestMethod("POST");
                        http.addRequestProperty("Authorization", authToken);

                        ChessGame request = new ChessGame(inputList[1]);
                        // Send post request
                        http.setDoOutput(true);
                        OutputStream wr = http.getOutputStream();
                        wr.write(new Gson().toJson(request).getBytes(StandardCharsets.UTF_8));
                        wr.flush();
                        wr.close();

                        int responseCode = http.getResponseCode();
                        String responseMessage;
                        if (responseCode == 401) {
                            responseMessage = "Error: unauthorized";
                            System.out.println("Response Message : " + responseMessage);
                        } else if (responseCode == 400) {
                            responseMessage = "Error: bad request";
                            System.out.println("Response Message : " + responseMessage);
                        } else {
                            String result = "";
                            try (InputStream respBody = http.getInputStream()) {
                                result = IOUtils.toString(respBody);
                                System.out.println(result);
                                var user = new Gson().fromJson(result, GameData.class);
                                System.out.println("Successfully created game: " + user);
                            }
                        }
                        // Output the response body
                        try (InputStream respBody = http.getInputStream()) {
                            String result = IOUtils.toString(respBody);
                            System.out.println(result);
                        }
                    } catch (Exception r) {
                        System.out.println("Exception Message : " + r.getMessage());
                    }
                }
            }
            //send to gameUI
            //if join or observe
            //send to gameui, does same thing
            //list games
            else if (inputList[0].equalsIgnoreCase("list")) {
                try {
                    URI uri = new URI("http://localhost:8080/game");
                    HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
                    http.setRequestMethod("GET");
                    http.addRequestProperty("Authorization", authToken);

                    // Send get request
                    http.connect();

                    int responseCode = http.getResponseCode();
                    String responseMessage;
                    if(responseCode == 401){
                        responseMessage = "Error: unauthorized";
                        System.out.println("Response Message : " + responseMessage);
                    } else if (responseCode == 400) {
                        responseMessage = "Error: bad request";
                        System.out.println("Response Message : " + responseMessage);
                    }
                    else {
                        String result = "";
                        try (InputStream respBody = http.getInputStream()) {
                            result = IOUtils.toString(respBody);
                            var list = new Gson().fromJson(result, GameList.class);
                            for (var game: list.games()) {
                                System.out.println(game);
                            }
                        }
                    }
                } catch (Exception r) {
                    System.out.println("Exception Message : " + r.getMessage());
                }
            }
            //create game
            //take input and send it to server
            //receives gameID and uses chessgame
            //sends to gameplayUI

            //join game
            // if the input list doesn't have info for joining as a player, fill in the blanks with null
            //if success
            //send to gameplayui

            //list game
            //sends link to server
            //prints out list of games

            //logout
            //sends input data to server.java
            //prints successfully logged out as username if success
            //returns user to PreloginUI
            else {
                System.out.println("I'm sorry, I did not understand your request. Please try again.");
                System.out.println(getHelp());
            }
            userInput = in.nextLine();
            inputList = userInput.split(" ");
        }
    }
    private String getHelp() {
        return """
                'create' <name> = create a chess game
                'join' <game ID> <white|black> = join a chess game
                'observe' <gameID> = observe a chess game
                'list' = list all chess games
                'help' = show options
                """;
    }
}

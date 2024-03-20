package ui;

import com.google.gson.Gson;
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

public class PostloginUI {
    public PostloginUI (String authToken){

    }

    public void postLogin () throws IOException, URISyntaxException {
        //when called prints postlogin ui page
        System.out.println("Welcome to CS240 Chess Game!");
        System.out.println("Enter in a command or 'help' for your options");

        //take in input and form it into a list. Based on the first item in the list, that is the keyword
        Scanner in = new Scanner(System.in);

        String userInput = in.nextLine();
        String[] inputList = userInput.split(" ");

            if (inputList[0].equalsIgnoreCase("help")) {

            }
            // if logout
            //send to userui
            //if create game
            //send to gameUI
            //if join or observe
            //send to gameui, does same thing
            //list games
            URI uri = new URI("http://localhost:8080/game");
            HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
            http.setRequestMethod("GET");

            // Send post request
            http.setDoOutput(true);
            OutputStream wr = http.getOutputStream();

            wr.flush();
            wr.close();

            int responseCode = http.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + uri);
            System.out.println("Response Code : " + responseCode);


            // Make the request
            //http.connect();

            // Output the response body
            try (InputStream respBody = http.getInputStream()) {
                String result = IOUtils.toString(respBody);

                System.out.println(result);
            }
            //esle
            //print out command unknown, try again, maybe the help screen


            //class where we create and deal with games

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

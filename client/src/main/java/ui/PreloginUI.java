package ui;

import java.util.Objects;
import java.util.Scanner;

public class PreloginUI {

    //start function
    void start(String [] args) {
        //print out prelogin ui to the screen
        System.out.print("Welcome to CS240 Chess Game!");
        System.out.print("Enter in a command or 'help' for your options");

        //take in input and form it into a list. Based on the first item in the list, that is the keyword
        Scanner in = new Scanner(System.in);

        String userInput = in.nextLine();
        String[] inputList = userInput.split(" ");

        while (!Objects.equals(inputList[0], "quit")) {

            if (inputList[0].equalsIgnoreCase("help")) {
                System.out.print("'register' <Username> <Password> <Email> = register account");
                System.out.print("'login' <Username> <Password> = login to your account");
                System.out.print("'quit' = exit chess game");
                System.out.print("'help' = show options");
            }
            /* URI uri = new URI("http://localhost:8080/name");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("GET");

        // Make the request
        http.connect();

        // Output the response body
        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            System.out.println(new Gson().fromJson(inputStreamReader, Map.class));
        }*/
            //else if list[1] == register
            else if (inputList[0].equalsIgnoreCase("register")) {
                //            sends input data to server.java
                //            prints logged in as username if success
                //            prints authtokne
                //            returns user to PostLoginUI
            }
            // else if list[1] == login
            else if (inputList[0].equalsIgnoreCase("login")) {
                //      call login function in UserUI
//sends input data to server.java
                //prints logged in as username if success
                //prints authtoken
                //returns user to PostLoginUI

            }
            else {
                System.out.print("I'm sorry, I did not understand your request. Please try again.");
            }
            userInput = in.nextLine();
            inputList = userInput.split(" ");
        }
    }
}

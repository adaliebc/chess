package ui;

import java.util.Scanner;

public class PostloginUI {
    public PostloginUI (String authToken){

    }

    public void postLogin () {
        //when called prints postlogin ui page
        System.out.println("Welcome to CS240 Chess Game!");
        System.out.println("Enter in a command or 'help' for your options");

        //take in input and form it into a list. Based on the first item in the list, that is the keyword
        Scanner in = new Scanner(System.in);

        String userInput = in.nextLine();
        String[] inputList = userInput.split(" ");

        while (!inputList[0].equalsIgnoreCase("quit")) {

            if (inputList[0].equalsIgnoreCase("help")) {
            }
//input is formed into a list
            //list[1] is keyword
//if stataemnts to send input to where it needs to be
            //if help
            //list the options
            // if logout
            //send to userui
            //if create game
            //send to gameUI
            //if join or observe
            //send to gameui, does same thing
            //list games
            //send to gameui
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
    }
}

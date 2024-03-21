package ui;

import chess.ChessGame;
import model.GameData;

import java.util.Scanner;


public class PreloginUI {

    private ServerFacade facade;
    private PostloginUI postloginUI;
    private GamePlayUI gamePlayUI;
    private boolean loggedIn;

    public PreloginUI() {
    }

    public void getInput() {
        loggedIn = false;
        //when called prints postlogin ui page
        System.out.println("Welcome to CS240 Chess Game!");
        System.out.println("Enter in a command or 'help' for your options");

        //take in input and form it into a list. Based on the first item in the list, that is the keyword
        Scanner in = new Scanner(System.in);

        String userInput = in.nextLine();
        String[] inputList = userInput.split(" ");
        while (!inputList[0].equalsIgnoreCase("quit")) {
            if (inputList[0].equalsIgnoreCase("help")) {
                if (loggedIn) {
                    System.out.println(getHelp());
                } else {
                    System.out.println(postloginUI.getHelp());
                }
            } else if (inputList[0].equalsIgnoreCase("register")) {
                boolean success = facade.register(inputList);
                if(success){
                    loggedIn = true;
                } else {
                    loggedIn = false;
                }
            } else if (inputList[0].equalsIgnoreCase("login")){
                loggedIn = facade.login(inputList);
            } else if (inputList[0].equalsIgnoreCase("logout")){
                boolean success = facade.logout();
                if(success){
                    System.out.println("Successfully logged out");
                    loggedIn = false;
                } else {
                    loggedIn = true;
                }
            } else if (inputList[0].equalsIgnoreCase("create")) {
                int gameID = facade.createGame(inputList);
                if(gameID != 0){
                    System.out.println("Use GameID to join game");
                }
            } else if (inputList[0].equalsIgnoreCase("join")|| inputList[0].equalsIgnoreCase("observe")) {
                ChessGame game = facade.joinGame(inputList);
                if(game != null){
                    System.out.print("Successfully joined the game!");
                    gamePlayUI.printBoard(game);
                }
            }else if (inputList[0].equalsIgnoreCase("list")) {
                facade.listGames();
            }
        }
    }

        public String getHelp () {
            return """
                    'register' <Username> <Password> <Email> = register account
                    'login' <Username> <Password> = login to your account
                    'quit' = exit chess game
                    'help' = show options
                    """;
        }
}

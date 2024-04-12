package ui;

import chess.ChessBoard;
import chess.ChessGame;
import model.GameData;

import java.util.Scanner;


public class PreloginUI {

    private ServerFacade facade;
    private WebSocketFacade websocketFacade;
    private PostloginUI postloginUI;
    private GamePlayUI gamePlayUI;
    private NotificationHandler notificationHandler;
    private boolean loggedIn;

    public PreloginUI() {
        facade = new ServerFacade(8080);
        notificationHandler = new NotificationHandler();
        websocketFacade = new WebSocketFacade(notificationHandler);
        postloginUI = new PostloginUI();
        gamePlayUI = new GamePlayUI();
    }
public static void main(String[] args){
    PreloginUI p = new PreloginUI();
    p.getInput();
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
                if (!loggedIn) {
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
                ChessBoard game = facade.joinGame(inputList);
                //call join game fnction in wsfacade
                if(game != null){
                    System.out.println("Successfully joined the game!");
                    websocketFacade.joinGame(inputList, facade.getAuthToken());
                    gamePlayUI.printBoard(game);
                }
            }else if (inputList[0].equalsIgnoreCase("list")) {
                facade.listGames();
            }
            userInput = in.nextLine();
           inputList = userInput.split(" ");
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

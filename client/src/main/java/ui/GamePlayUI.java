package ui;

import chess.ChessBoard;
import chess.ChessGame;
import webSocketMessages.serverMessages.ServerMessage;

import java.util.Scanner;

public class GamePlayUI {
    String authToken = "";
    int gameID;
    ChessBoard board;
    //player color: either a string that can say observer, black or white, or the actual team color
    ChessGame.TeamColor playerColor;
    WebSocketFacade webSocketFacade;
    public GamePlayUI(WebSocketFacade webSocketFacade){
        this.webSocketFacade = webSocketFacade;
    }
    //create a main function
    //makes a play game function
    public void playGame(String authToken) {
        this.authToken = authToken;
        System.out.println("Enter in a command or 'help' for your options");

        //take in input and form it into a list. Based on the first item in the list, that is the keyword
        Scanner in = new Scanner(System.in);

        String userInput = in.nextLine();
        String[] inputList = userInput.split(" ");
        while (!inputList[0].equalsIgnoreCase("leave") && (!inputList[0].equalsIgnoreCase("resign") && playerColor != null)) {
            if (inputList[0].equalsIgnoreCase("help")) {
                System.out.println(getHelp());
            }
            else if(inputList[0].equalsIgnoreCase("redraw")){
                webSocketFacade.redraw();
            }
            else if(inputList[0].equalsIgnoreCase("make_move")){
                if(inputList.length != 6){
                    System.out.println("Incorrect number of input");
                    System.out.println(getHelp());
                }
                else{
                    webSocketFacade.makeMove(inputList, authToken, playerColor);
                }
            }
            else if(inputList[0].equalsIgnoreCase("highlight")){
                if(inputList.length != 3){
                    System.out.println("Incorrect number of inputs");
                    System.out.println(getHelp());
                }
                else{
                    webSocketFacade.highlight(inputList, playerColor);
                }
            }
            userInput = in.nextLine();
            inputList = userInput.split(" ");
        }
    }

    public void printBoard(ChessBoard game){
        //add in a specification, if black, print the board from blacks perspective
        //if white, print white
        //else if it is a obersevr, print both
        System.out.println(game.toString());
    }

    public String getHelp () {
        return """
                    'redraw' = redraws board onto screen at your command
                    'leave' = leave the game
                    'make_move' <beginning coordinate (A 6)> <ending coordinate> <Promotion Piece>= make a move, add a space between coordinate digits
                    'resign' = quit and exit the game
                    'highlight' <starting coordinates> = print out a screen to highlight legal moves
                    """;
    }
}

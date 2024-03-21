package ui;

import chess.ChessGame;
import com.google.gson.Gson;
import model.*;
import spark.utils.IOUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class ServerFacade {
    private static String serverUrl = null;
    private PreloginUI preloginUI = new PreloginUI();
    private String authToken = "";
    private PostloginUI postloginUI = new PostloginUI();

    public ServerFacade(int port) {
        serverUrl = "http://localhost:" + port;
    }

    public static void main(String[] args) {
        PreloginUI p = new PreloginUI();
        p.getInput();
    }

    public boolean register(String[] inputList) {
        if (inputList.length != 4) {
            System.out.println("insufficient arguments");
            System.out.println(preloginUI.getHelp());
            return false;
        } else {
            UserData registerRequest = new UserData(inputList[1], inputList[2], inputList[3]);
            byte[] request = new Gson().toJson(registerRequest).getBytes(StandardCharsets.UTF_8);
            String result = makeRequest("/user", "POST", request, authToken);
            if (!result.isEmpty()) {
                System.out.println("Successfully logged in as " + inputList[1]);
                var user = new Gson().fromJson(result, AuthData.class);
                authToken = user.authToken();
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean listGames(){
        String result = makeRequest("/game", "GET", null, authToken);
        if(result.isEmpty()){
        var list = new Gson().fromJson(result, GameList.class);
        for (var game: list.games()) {
            System.out.println(game);
        }
        return true;
        }else {
            return false;
        }
    }
    public boolean login(String[] inputList){
        if (inputList.length != 3) {
            System.out.println("insufficient arguments");
            System.out.println(preloginUI.getHelp());
            return false;
        } else {
            LoginRequest loginRequest = new LoginRequest(inputList[1], inputList[2]);
            byte[] request = new Gson().toJson(loginRequest).getBytes(StandardCharsets.UTF_8);
            String result = makeRequest("/session", "POST", request, authToken);
            if (!result.isEmpty()) {
                var user = new Gson().fromJson(result, AuthData.class);
                System.out.println("Successfully logged in as " + inputList[1]);
                authToken = user.authToken();
                return true;
            } else{
                return false;
            }
        }
    }

    public boolean logout (){
        String result = makeRequest("/session", "DELETE", null,authToken);
        return !result.isEmpty();
    }

    public boolean createGame(String[] inputList){
        if (inputList.length != 2) {
            System.out.println("insufficient arguments");
            System.out.println(preloginUI.getHelp());
            return false;
        } else {
            GameName request = new GameName(inputList[1]);
            byte[] inputResult = new Gson().toJson(request).getBytes(StandardCharsets.UTF_8);
            String result = makeRequest("/game", "POST", inputResult, authToken);
            if(!result.isEmpty()){
                var game = new Gson().fromJson(result, GameID.class);
                System.out.println("Successfully created game: " + game);
                return true;
            } else{
                return false;
            }
        }
    }

    public ChessGame joinGame(String[] inputList){
        int gameID;
        try{
            gameID = Integer.parseInt(inputList[1]);
            if (inputList[0].equalsIgnoreCase("join") && inputList.length != 3) {
                System.out.println("insufficient arguments");
                System.out.println(postloginUI.getHelp());

            } else if (inputList[0].equalsIgnoreCase("observe") && inputList.length != 2){
                System.out.println("insufficient arguments");
                System.out.println(postloginUI.getHelp());

            }else if (inputList[0].equalsIgnoreCase("observe")){
                JoinGameRequest request = new JoinGameRequest("", gameID);
                byte[] inputRequest = new Gson().toJson(request).getBytes(StandardCharsets.UTF_8);
                String result = makeRequest("/game", "PUT", inputRequest, authToken);
                var gameData = new Gson().fromJson(result, GameData.class);
                return gameData.game();

            } else if (!inputList[2].equalsIgnoreCase("white") && !inputList[2].equalsIgnoreCase("black")){
                System.out.println("Player color must be black or white");
                System.out.println(postloginUI.getHelp());
            }
            else if (inputList[0].equalsIgnoreCase("join")) {
                JoinGameRequest request = new JoinGameRequest(inputList[2], gameID);
                byte[] inputRequest = new Gson().toJson(request).getBytes(StandardCharsets.UTF_8);
                String result = makeRequest("/game", "PUT", inputRequest, authToken);
                var gameData = new Gson().fromJson(result, GameData.class);
                return gameData.game();
            }
        }
        catch (NumberFormatException e){
            System.out.println("GameID is not an int");
            System.out.println(postloginUI.getHelp());
        }
        return null;
    }
    private String makeRequest(String serverEnd, String method, byte[] request, String authToken) {
        try {
            URI uri = new URI(serverUrl + serverEnd);
            HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
            http.setRequestMethod(method);
            if (!authToken.isEmpty()) {
                http.addRequestProperty("Authorization", authToken);
            }
            // Send post request

            if(request != null) {
                http.setDoOutput(true);
                OutputStream wr = http.getOutputStream();
                wr.write(request);
                wr.flush();
                wr.close();
            } else {
                http.connect();
            }

            int responseCode = http.getResponseCode();
            String responseMessage;
            if (responseCode == 401) {
                responseMessage = "Error: unauthorized";
                System.out.println("Response Message : " + responseMessage);
            } else if (responseCode == 400) {
                responseMessage = "Error: bad request";
                System.out.println("Response Message : " + responseMessage);
            } else if (responseCode == 403) {
                responseMessage = "Error: already taken";
                System.out.println("Response Message : " + responseMessage);
            } else {
                String result = "";
                try (InputStream respBody = http.getInputStream()) {
                    result = IOUtils.toString(respBody);
                    System.out.println(result);
                    return result;
                }
            }
        } catch (Exception r) {
            System.out.println("Exception Message : " + r.getMessage());
        }
        return "";
    }
}

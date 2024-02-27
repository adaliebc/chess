package server;

import com.google.gson.Gson;
import spark.*;
import java.util.*;
import service.*;
import model.*;

public class Server {
    UserService userService = new UserService();
    AuthService authService = new AuthService();
    GameService gameService = new GameService();
    public static void main(String[] args) {
        new Server().run(8080);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.get("/hello", (req, resp) -> "Hello World");
        Spark.delete("/db", this::clearBody);
        Spark.post("/user", this::registerBody);
        Spark.post("/game", this::createGameBody);
        Spark.put("/game", this::putGameBody);
        Spark.post("/session", this::loginBody);
        Spark.delete("/session", this::logoutBody);
        Spark.awaitInitialization();
        return Spark.port();
    }
    private Object clearBody(Request req, Response res) {
        //call service, who calls DAO, who clears data
        MResponse response = null;
        MResponse userResponse = userService.clearData();
        MResponse gameResponse = gameService.clearData();
        if (userResponse.code() != 200) {
            response = userResponse;
        } else if (gameResponse.code() != 200) {
            response = gameResponse;
        } else {
            response = userResponse;
        }
        //if no errors are called we return success, which is 200
        res.type("application/json");
        res.status(response.code());
        res.body(response.message());
        return "";
    }
    private Object registerBody(Request req, Response res){
        //Call service and send in username, password, and email
        var user = new Gson().fromJson(req.body(), UserData.class);
        AuthData token = null;
        if (user.email() == null || user.password() == null || user.username() == null){
            res.status(400);
            var message = new FailureResponse("Error: bad request");
            return new Gson().toJson(message);
        }
        try {
            token = userService.register(user);
        } catch(ResponseException r) {
            if (r.StatusCode() == 403) {
                res.status(403);
                var message = new FailureResponse("Error: already taken");
                return new Gson().toJson(message);
            }
        }
        res.type("application/json");
        res.status(200);
        var body = new Gson().toJson(token);
        res.body(body);
        return body;
    }
    private Object createGameBody(Request req, Response res) {
        GameID gameID = null;
        String gameName = req.body();
        String token = req.headers("Authorization");
        if (token == null || gameName == null){
            res.status(400);
            var message = new FailureResponse("Error: bad request");
            return new Gson().toJson(message);
        }
        //verify token
        try {
            userService.verifyToken(token);
        } catch(ResponseException r) {
            if (r.StatusCode() == 401) {
                res.status(401);
                var message = new FailureResponse("Error: unauthorized");
                return new Gson().toJson(message);
            }
        }
        gameID = gameService.createGame(gameName);
        res.type("application/json");
        res.status(200);
        return new Gson().toJson(gameID);
    }
    private Object putGameBody(Request req, Response res) {
        //Call service and send in username, password, and email
        var body = new Gson().toJson(Map.of("success", true));
        res.type("application/json");
        res.status(200);
        res.body(body);
        return body;
    }
    private Object loginBody(Request req, Response res) {
        //Call service and send in username, password, and email
        var user = new Gson().fromJson(req.body(), LoginRequest.class);
        AuthData token = null;
        if (user.password() == null || user.username() == null){
            res.status(400);
            var message = new FailureResponse("Error: bad request");
            return new Gson().toJson(message);
        }
        try {
            token = userService.login(user);
        } catch(ResponseException r) {
            if (r.StatusCode() == 401) {
                res.status(401);
                var message = new FailureResponse("Error: unauthorized");
                return new Gson().toJson(message);
            }
        }
        res.type("application/json");
        res.status(200);
        var body = new Gson().toJson(token);
        res.body(body);
        return body;
    }

    private Object logoutBody(Request req, Response res) {
        //Call service and send in username, password, and email
        String token = req.headers("Authorization");

        if (token == null){
            res.status(500);
            var message = new FailureResponse("Error: description");
            return new Gson().toJson(message);
        }
        try {
            userService.logout(token);
        } catch(ResponseException r) {
            if (r.StatusCode() == 401) {
                res.status(401);
                var message = new FailureResponse("Error: unauthorized");
                return new Gson().toJson(message);
            }
        }
        res.type("application/json");
        res.status(200);
        return "";
    }
    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
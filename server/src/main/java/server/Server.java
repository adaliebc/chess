package server;

import com.google.gson.Gson;
import spark.*;
import java.util.*;
import service.*;

public class Server {
    public static void main(String[] args) {
        new Server().run(8080);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.get("/hello", (req, resp) -> "Hello World");
        Spark.delete("/db", this::clearBody);
        Spark.post("/user", this::userBody);
        Spark.awaitInitialization();
        return Spark.port();
    }
    private Object clearBody(Request req, Response res) {
        //call service, who calls DAO, who clears data
        ChessService.clearData();
        //if no errors are called we return success, which is 200
        var body = new Gson().toJson(Map.of("success", true));
        res.type("application/json");
        res.status(200);
        res.body(body);
        return body;
    }
    private Object userBody(Request req, Response res) {
        var body = new Gson().toJson(Map.of("success", true));
        res.type("application/json");
        res.status(200);
        res.body(body);
        return body;
    }
    public void response(int code, String message){
        //message can be null
        //throw error code: message
    }
    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
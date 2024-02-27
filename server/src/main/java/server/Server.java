package server;

import com.google.gson.Gson;
import spark.*;
import java.util.*;
import service.*;
import model.*;

public class Server {
    ChessService service = new ChessService();
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
        Spark.post("/game", this::postGameBody);
        Spark.put("/game", this::putGameBody);
        Spark.post("/session", this::sessionBody);
        Spark.awaitInitialization();
        return Spark.port();
    }
    private Object clearBody(Request req, Response res) {
        //call service, who calls DAO, who clears data
        MResponse response = service.clearData();
        //if no errors are called we return success, which is 200
        var body = new Gson().toJson(Map.of("success", true));
        res.type("application/json");
        res.status(response.code());
        res.body(response.message());
        return body;
    }
    private Object userBody(Request req, Response res) {
        //Call service and send in username, password, and email
        var body = new Gson().toJson(Map.of("success", true));
        res.type("application/json");
        res.status(200);
        res.body(body);
        return body;
    }
    private Object postGameBody(Request req, Response res) {
        //Call service and send in username, password, and email
        var body = new Gson().toJson(Map.of("success", true));
        res.type("application/json");
        res.status(200);
        res.body(body);
        return body;
    }
    private Object putGameBody(Request req, Response res) {
        //Call service and send in username, password, and email
        var body = new Gson().toJson(Map.of("success", true));
        res.type("application/json");
        res.status(200);
        res.body(body);
        return body;
    }
    private Object sessionBody(Request req, Response res) {
        //Call service and send in username, password, and email
        var body = new Gson().toJson(Map.of("success", true));
        res.type("application/json");
        res.status(200);
        res.body(body);
        return body;
    }
    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
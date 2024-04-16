package websocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;

import javax.management.Notification;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();
    public final HashMap<String, Integer> games = new HashMap<String, Integer>();

    //function to create a connection and add it to connections
    public void add(String authToken, Session session, int gameID) {
        var connection = new Connection(authToken, session);
        connections.put(authToken, connection);
        games.put(authToken, gameID);
    }
    //function to remove that
    public void remove(String authToken){
        connections.remove(authToken);
        games.remove(authToken);
    }
    //function to get the connection
    public Connection getConnection(String authToken){
        return connections.get(authToken);
    }
    //broadcast function
    public void broadcast(String excludeAuthToken, ServerMessage notification) throws IOException {
        var removeList = new ArrayList<Connection>();
        int gameID = games.get(excludeAuthToken);
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if(games.get(c.authToken) == gameID) {
                    if (!c.authToken.equals(excludeAuthToken)) {
                        c.send(new Gson().toJson(notification));
                    }
                }
            } else {
                removeList.add(c);
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.authToken);
        }
    }
}

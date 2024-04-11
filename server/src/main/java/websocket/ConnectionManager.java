package websocket;

import org.eclipse.jetty.websocket.api.Session;

import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();


    //function to create a connection and add it to connections
    //function to remove that
    //function to get the connection
    public Connection getConnection(String authToken){
        return connections.get(authToken);
    }
    //broadcast function
}

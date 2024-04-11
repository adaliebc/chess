package websocket;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
//import webSocketMessages.Notification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Connection {
    String username;
    Session session;
    //may need to change Session in connections to Connection
    public final ConcurrentHashMap<String, Session> connections = new ConcurrentHashMap<>();

    //consructor
    public Connection(String username, Session session){
        this.username = username;
        this.session = session;
        connections.put(username, session);
    }
    //create a connection
    //stops connection (called by handler)
    public void remove(String username){
        connections.remove(username);
    }

    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
    }
    //broadcast message to all connections
}

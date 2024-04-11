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

    //consructor
    public Connection(String username, Session session){
        this.username = username;
        this.session = session;
    }

    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
    }
}

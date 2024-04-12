package websocket;

import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
//import webSocketMessages.Notification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Connection {
    String authToken;
    Session session;
    //may need to change Session in connections to Connection

    //consructor
    public Connection(String authToken, Session session){
        this.authToken = authToken;
        this.session = session;
    }

    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
    }

    //send error function
    public void sendError(RemoteEndpoint session, String msg) throws IOException {
        //session.getRemote().sendString(msg);
    }
}

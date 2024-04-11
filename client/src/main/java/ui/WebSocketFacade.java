package ui;

import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade {
//sends commands to server
    //i think we call the functions in UserGameCommand to send to handler
    public void onMessage(String message) {
        try {
            ServerMessage message =
                    gson.fromJson(message, ServerMessage.class);
            observer.notify(message);
        } catch(Exception ex) {
            observer.notify(new ErrorMessage(ex.getMessage()));
        }
    }


}

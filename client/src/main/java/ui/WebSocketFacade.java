package ui;

import service.ResponseException;
import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import com.google.gson.*;
import webSocketMessages.userCommands.UserGameCommand;


public class WebSocketFacade extends Endpoint{
    private Session session;
    private NotificationHandler notificationHandler;
//sends commands to server
    //i think we call the functions in UserGameCommand to send to handler

    public WebSocketFacade(NotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
        try {
            URI uri = new URI("ws://localhost:8080/connect");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, uri);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                public void onMessage(String message) {
                    //System.out.println(message);
                    notifyMessage(message);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(String msg) throws Exception {this.session.getBasicRemote().sendText(msg);}
    public void onOpen(Session session, EndpointConfig endpointConfig) {}

    public void notifyMessage(String inputMessage) {
        try {
            ServerMessage message = new Gson().fromJson(inputMessage, ServerMessage.class);
            notificationHandler.notify(message);
        } catch(Exception ex) {
            //observer.notify(new ErrorMessage(ex.getMessage()));
        }
    }

    public void joinGame(String[] inputList, String authToken){
        //send the game command through a message
        try {
            UserGameCommand command = new UserGameCommand(authToken);
            command.setCommandType(UserGameCommand.CommandType.JOIN_PLAYER);
            command.setCommand(inputList[1] + "," + inputList[2]);
            String message = new Gson().toJson(command);
            send(message);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //System.out.println("made it... yay!");
        //call reload game
    }

}

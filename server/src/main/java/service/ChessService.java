package service;

import server.*;
import dataAccess.*;
public class ChessService {
    public void clearData(){
        if(GameDAO.clear() && UserDAO.clear() && AuthDAO.clear()){
            return Server.response(200, null);
        } else {
            return Server.response(500, "{ \"message\": \"Error: description\" }");
        }
    }
}

package service;

import dataAccess.*;
import model.*;
public class ChessService {
    MemoryGameDAO gdao = new MemoryGameDAO();
    MemoryUserDAO udao = new MemoryUserDAO();
    MemoryAuthDAO adao = new MemoryAuthDAO();

    public MResponse clearData(){
        if(gdao.clear() && udao.clear() && adao.clear()){
            return new MResponse(200, "");
        } else {
            return new MResponse(500, "{ \"message\": \"Error: description\" }");
        }
    }

    //create new user
    //get user, if null, carry on, else: [403] { "message": "Error: already taken" }
    //send info to userDAO.java to create record and add to memory;
}

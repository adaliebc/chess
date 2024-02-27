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

}

package service;

import model.*;
import dataAccess.*;
public class UserService {
    MemoryUserDAO udao = new MemoryUserDAO();
    MemoryAuthDAO adao = new MemoryAuthDAO();
    AuthService authservice = new AuthService();
    public AuthData register(UserData user) throws ResponseException{
        //create new user
        if(udao.getUser(user.username()) != null){
            throw new ResponseException(403, "{ \"message\": \"Error: already taken\" }");
        } else {
            udao.createUser(user);
            AuthData token = authservice.createAuthToken(user.username());
            adao.addToken(token);
            return token;
        }
        //get user, if null, carry on, else: [403] { "message": "Error: already taken" }
        //send info to userDAO.java to create record and add to memory;
    }
    public AuthData login(UserData user) {
        return null;
    }
    public void logout(UserData user) {}
}



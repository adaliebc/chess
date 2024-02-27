package service;

import model.*;
import dataAccess.*;

import java.util.Objects;

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
    public AuthData login(LoginRequest input) throws ResponseException{
        UserData user = udao.getUser(input.username());
        if(udao.getUser(input.username()) == null || !Objects.equals(input.password(), user.password())){
            throw new ResponseException(401, "{ \"message\": \"Error: unauthorized\" }");
        } else {
            AuthData token = authservice.createAuthToken(user.username());
            adao.addToken(token);
            return token;
        }
    }
    public void logout(String token) throws ResponseException{
        AuthData user = adao.getAuth(token);
        if(user == null){
            throw new ResponseException(401, "{ \"message\": \"Error: unauthorized\" }");
        } else {
            adao.deleteToken(user);
        }

    }
    public void verifyToken(String token) throws ResponseException{
        if(!adao.verifyAuth(token)){
            throw new ResponseException(401, "{ \"message\": \"Error: unauthorized\" }");
        }

    }
    public MResponse clearData(){
        //when you create gameService, move game clear over there
        if(udao.clear() && adao.clear()){
            return new MResponse(200, "");
        } else {
            return new MResponse(500, "{ \"message\": \"Error: description\" }");
        }
    }
    public String getUsername(String token){
        AuthData user = adao.getAuth(token);
        return user.username();
    }

}



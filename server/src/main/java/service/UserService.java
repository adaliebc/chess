package service;

import model.*;
import dataAccess.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

public class UserService {
    SQLUserDAO udao = new SQLUserDAO();
    SQLAuthDAO adao = new SQLAuthDAO();
    AuthService authservice = new AuthService();

    public AuthData register(UserData user) throws ResponseException{
        //create new user
        UserData gotUser = udao.getUser(user.username());
        if(gotUser.username() != null && gotUser.password() != null && gotUser.email() != null){
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
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(input.password());
        if(udao.getUser(input.username()) == null || !Objects.equals(hashedPassword, user.password())){
            throw new ResponseException(401, "{ \"message\": \"Error: unauthorized\" }");
        } else {
            AuthData token = authservice.createAuthToken(user.username());
            adao.addToken(token);
            return token;
        }
    }
    public void logout(String token) throws ResponseException{
        AuthData user = adao.getAuth(token);
        if(user.username() == null && user.authToken() == null){
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
        if(udao.clear() && adao.clear()){
            return new MResponse(200, "");
        } else {
            return new MResponse(500, "{ \"message\": \"Error: description\" }");
        }
    }
    public String getUsername(String token) {
        AuthData user = adao.getAuth(token);
        if (user != null) {
            return user.username();
        }
        return null;
    }

}



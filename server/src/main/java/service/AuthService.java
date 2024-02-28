package service;

import model.*;
import dataAccess.*;

import java.util.Objects;
import java.util.UUID;

public class AuthService {
    private String createAuth() {
        return UUID.randomUUID().toString();
    }
    public AuthData createAuthToken(String username){
        if(username  == null){
            return null;
        }
        return new AuthData(createAuth(), username);
    }

}

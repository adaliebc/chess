package service;

import model.*;
import dataAccess.*;

import java.util.UUID;

public class AuthService {
    public String createAuth() {
        return UUID.randomUUID().toString();
    }
    public AuthData createAuthToken(String username){
        return new AuthData(createAuth(), username);
    }
}

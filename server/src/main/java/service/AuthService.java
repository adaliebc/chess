package service;

import model.*;
import dataAccess.*;

import java.util.Objects;
import java.util.UUID;

public class AuthService {
    MemoryAuthDAO adao = new MemoryAuthDAO();
    public String createAuth() {
        return UUID.randomUUID().toString();
    }
    public AuthData createAuthToken(String username){
        return new AuthData(createAuth(), username);
    }

}

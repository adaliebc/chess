package model;

public class AuthData {
    //authtoken and username
    record userRecord(String authToken, String username) {
        //create new authToken
        //UUID.randomUUID().toString()
        //match username to user
    }
}

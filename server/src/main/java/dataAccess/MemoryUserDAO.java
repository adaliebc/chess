package dataAccess;

import model.UserData;

import java.util.Collection;
import java.util.HashSet;

public class MemoryUserDAO implements UserDAO{
    Collection<UserData> userRecord = new HashSet<UserData>();
    public boolean clear(){
        userRecord.clear();
        if(userRecord.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    /* createUser: Create a new user.
        create record with UserData and push to MemoryUserDAO.java*/

    public void createUser(UserData user){
        userRecord.add(user);
    }

    public UserData getUser(String username) {
        for (UserData user : userRecord){
            if (user.username().equals(username)){
                return user;
            }
        }
        return null;
    }
}
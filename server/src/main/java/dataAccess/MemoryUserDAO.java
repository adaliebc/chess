package dataAccess;

import java.util.Collection;
import java.util.HashSet;

public class MemoryUserDAO implements UserDAO{
    Collection<Record> userRecord = new HashSet<Record>();
    public boolean clear(){
        userRecord.clear();
        if(userRecord.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    /* createUser: Create a new user.
        create record with UserData and push to MemoryUserDAO.java


    getUser: Retrieve a user with the given username.
    userRecord.contains(username);
    if it's there, userRecord.get(username);
    return record;
    */
}
package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.Collection;
import java.util.HashSet;

public class MemoryAuthDAO implements AuthDAO{
    Collection<AuthData> authRecord = new HashSet<AuthData>();
    //clear data
    //authRecord.removeAll(authRecord);
    // if returns false, throw error, else return 200
    public boolean clear(){
        authRecord.clear();
        return true;
    }

    public boolean addToken(AuthData token) {
        authRecord.add(token);
        return true;
    }
    public AuthData getAuth(String token) {
        for (AuthData user : authRecord){
            if (user.authToken().equals(token)){
                return user;
            }
        }
        return null;
    }
    public void deleteToken(AuthData token){
        authRecord.remove(token);
    }
}

    /* createAuth: Create a new authorization.
    getAuth: Retrieve an authorization given an authToken.
    deleteAuth: Delete an authorization so that it is no longer valid.*/


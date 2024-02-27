package dataAccess;

import model.AuthData;

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

    @Override
    public boolean addToken(AuthData token) {
        authRecord.add(token);
        return true;
    }

    /* createAuth: Create a new authorization.
    getAuth: Retrieve an authorization given an authToken.
    deleteAuth: Delete an authorization so that it is no longer valid.*/
}

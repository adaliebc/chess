package dataAccess;

import java.util.Collection;
import java.util.HashSet;

public class MemoryAuthDAO implements AuthDAO{
    Collection<Record> authRecord = new HashSet<Record>();
    //clear data
    //authRecord.removeAll(authRecord);
    // if returns false, throw error, else return 200
    public boolean clear(){
        authRecord.clear();
        return true;
    }

    /* createAuth: Create a new authorization.
    getAuth: Retrieve an authorization given an authToken.
    deleteAuth: Delete an authorization so that it is no longer valid.*/
}

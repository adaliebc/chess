package dataAccess;

public class AuthDAO {
    //clear data
    //authRecord.removeAll(authRecord);
    // if returns false, throw error, else return 200
    public boolean clear(){
        return authRecord.removeAll(authRecord);
    }

    /* createAuth: Create a new authorization.
    getAuth: Retrieve an authorization given an authToken.
    deleteAuth: Delete an authorization so that it is no longer valid.*/
}

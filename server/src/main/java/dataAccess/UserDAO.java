package dataAccess;

public class UserDAO {
    //clear data
    public boolean clear(){
        return userRecord.removeAll(userRecord);
    }
    /* createUser: Create a new user.
        create record with UserData and push to MemoryUserDAO.java


    getUser: Retrieve a user with the given username.
    userRecord.contains(username);
    if it's there, userRecord.get(username);
    return record;
    */
}

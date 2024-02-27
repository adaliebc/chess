package dataAccess;

import model.UserData;

public interface UserDAO {
    public boolean clear();
    public UserData getUser(String username);
    public boolean createUser(UserData user);
}

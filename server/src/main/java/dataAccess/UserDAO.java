package dataAccess;

import model.UserData;
import service.ResponseException;

public interface UserDAO {
    public boolean clear();
    public UserData getUser(String username);
    public void createUser(UserData user) throws ResponseException;
}

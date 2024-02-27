package dataAccess;

import model.AuthData;

public interface AuthDAO {
    public boolean clear();
    public boolean addToken(AuthData token);
}

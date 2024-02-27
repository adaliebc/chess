package dataAccess;

import model.AuthData;

public interface AuthDAO {
    public boolean clear();
    public boolean addToken(AuthData token);
    public AuthData getAuth(String token);
    public void deleteToken(AuthData token);
}

package dao;

import model.User;
import exceptions.*;

public interface UserDAO {
    void addUser(User user) throws UserAlreadyExistsException;
    User getUserByUsername(String username) throws UserNotFoundException;
    boolean validateUser(String username, String password) throws UserNotFoundException;
}
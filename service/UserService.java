package service;

import dao.UserDAO;
import model.User;
import exceptions.*;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void registerUser(User user) throws UserAlreadyExistsException {
        userDAO.addUser(user);
    }

    public User authenticate(String username, String password) 
            throws UserNotFoundException, InvalidCredentialsException {
        
        User user = userDAO.getUserByUsername(username);
        if (!user.getPassword().equals(password)) {
            throw new InvalidCredentialsException();
        }
        return user;
    }
}
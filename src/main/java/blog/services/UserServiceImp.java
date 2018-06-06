package blog.services;

import blog.common.UserManager;
import blog.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    private UserManager userManager;

    public UserServiceImp() {
        userManager = new UserManager();
    }

    @Override
    public boolean authenticate(String username, String password) {
        return userManager.isValidUser(username, password);
    }

    @Override
    public boolean registerNewUser(User user) {
        userManager.registerUser(user);
        return true;
    }

    @Override
    public User getUserByName(String userName) {
        return userManager.getUser(userName);
    }
}

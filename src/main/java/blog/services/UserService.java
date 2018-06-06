package blog.services;

import blog.model.User;

public interface UserService {

    boolean authenticate(String username, String password);
    boolean registerNewUser(User user);
    User getUserByName(String userName);
}

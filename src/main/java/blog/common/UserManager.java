package blog.common;

import blog.model.ProfilePhoto;
import blog.model.User;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.hibernate.Session;

@SuppressWarnings("unchecked")
public class UserManager extends SessionManager {

    public User registerUser(User user) {
        Session session = openSession();
        session.save(user);
        commitSession(session);
        return user;
    }

    public void deleteUser(User user) {
        Session session = openSession();
        session.delete(user);
        commitSession(session);
    }

    public User getUser(String userName) {
        Session session = openSession();
        User user = (User) session.get(User.class, userName);
        commitSession(session);
        return user;
    }

    public boolean isValidUser(final String userName, final String password) {

        Session session = openSession();
        User userFromDB = (User) session.get(User.class, userName);
        commitSession(session);

        if (userFromDB == null) return false;
        String hashOfPassword = userFromDB.getPasswordHash();

        String hashOfUserEnteredPassword = Hashing.sha256()
          .hashString(password, Charsets.US_ASCII)
                .toString();

        return hashOfPassword.equals(hashOfUserEnteredPassword);
    }

    public boolean isValidUser(final String userName, final String password, final boolean isHash) {

        Session session = openSession();
        User userFromDB = (User) session.get(User.class, userName);
        commitSession(session);

        if (userFromDB == null) return false;
        String hashOfPassword = userFromDB.getPasswordHash();

        String hashOfUserEnteredPassword;
        if(!isHash) {
            hashOfUserEnteredPassword = Hashing.sha256()
                    .hashString(password,Charsets.US_ASCII)
                    .toString();

        } else {
            hashOfUserEnteredPassword = password;
        }

        return hashOfPassword.equals(hashOfUserEnteredPassword);
    }

}

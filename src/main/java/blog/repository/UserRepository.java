package blog.repository;

import blog.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    @Query(nativeQuery = true,value="SELECT * FROM users WHERE UPPER(username) = UPPER (?1) ")
    String findUserExist(String user1);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="INSERT INTO users (username,fullname,passwordhash) VALUES (?1,?2,?3)")
    void addUserCredentials(String uname,String password,String fullName);

    @Query(nativeQuery = true,value="SELECT passwordhash FROM users WHERE UPPER(username)= UPPER(?1)")
    String findUserPassword(String user1);

}

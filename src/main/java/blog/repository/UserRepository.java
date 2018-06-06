package blog.repository;

import blog.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    @Query(nativeQuery = true,value="select * from users where upper(username) = upper (?1) ")
    String findUserExist(String user1);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value="insert into users (username,fullname,passwordhash) values (?1,?2,?3)")
    void addUserCredentials(String uname,String password,String fullName);

    @Query(nativeQuery = true,value="select passwordhash from users where upper(username)= upper(?1)")
    String findUserPassword(String user1);

    @Query(nativeQuery = true,value="select role from users where username=?1")
    String getUserRole(String uname);
}

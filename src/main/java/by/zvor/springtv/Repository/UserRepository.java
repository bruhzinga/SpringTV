package by.zvor.springtv.Repository;

import by.zvor.springtv.DTO.UserInfo;
import by.zvor.springtv.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Integer> {
   @Procedure("getAllUsers")
    Collection<UserInfo> findAllBy();


}
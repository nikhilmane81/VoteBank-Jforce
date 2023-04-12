package com.nikhil.repo;

import com.nikhil.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    public User findByUsername(String email);

    @Query(value = "select * from User where candidateid = ?1", nativeQuery = true)
    public List<User> findVotersById(int id);

}

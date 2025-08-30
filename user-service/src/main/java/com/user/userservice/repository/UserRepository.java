package com.user.userservice.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.user.userservice.entity.User;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends CrudRepository<User, UUID> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
}

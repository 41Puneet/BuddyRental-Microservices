package com.user_service.Repository;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.user_service.Entites.User;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User,UUID>{
Optional<User>findByEmail(String email);
Optional<User>findByPhoneNumber(String phoneNumber);
void deleteByEmail(String email);
}

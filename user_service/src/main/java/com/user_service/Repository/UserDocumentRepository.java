package com.user_service.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.user_service.Entites.UserDocument;
import java.util.UUID;

public interface UserDocumentRepository extends JpaRepository<UserDocument, UUID> {
    Optional<UserDocument> findByEmail(String email);
    Optional<UserDocument> findByPhoneNumber(String phoneNumber);
    void deleteByEmail(String email);

}

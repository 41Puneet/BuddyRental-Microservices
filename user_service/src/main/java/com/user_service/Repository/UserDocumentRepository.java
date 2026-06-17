package com.user_service.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.user_service.Entites.UserDocument;
import java.util.UUID;

public interface UserDocumentRepository extends JpaRepository<UserDocument, UUID> {
}

package ua.opnu.equipment_rental.Security;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.equipment_rental.Security.Model.AppUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}

package ua.opnu.equipment_rental.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.equipment_rental.Model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

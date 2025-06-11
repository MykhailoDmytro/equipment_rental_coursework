package ua.opnu.equipment_rental.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.equipment_rental.Model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

package ua.opnu.equipment_rental.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.opnu.equipment_rental.Model.Rental;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByCustomer_Id(Long customerId);
    List<Rental> findByEmployee_Id(Long employeeId);
    List<Rental> findByReturnedFalse();

    @Query("SELECT r.equipment.id, COUNT(r) FROM Rental r GROUP BY r.equipment.id")
    List<Object[]> countRentalsByEquipment();

    @Query("SELECT r.equipment.id FROM Rental r GROUP BY r.equipment.id ORDER BY COUNT(r) DESC")
    List<Long> findMostRentedEquipment();

    @Query("SELECT r FROM Rental r WHERE r.endDate < CURRENT_DATE AND r.returned = false")
    List<Rental> findOverdueRentals();
}

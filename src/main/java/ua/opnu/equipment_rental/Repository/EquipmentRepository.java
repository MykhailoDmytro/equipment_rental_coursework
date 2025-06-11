package ua.opnu.equipment_rental.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.opnu.equipment_rental.Model.Equipment;

import java.time.LocalDate;
import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    @Query("SELECT e FROM Equipment e WHERE e.availability = true OR e.id NOT IN " +
            "(SELECT r.equipment.id FROM Rental r WHERE " +
            "(r.startDate <= :date AND r.endDate >= :date))")
    List<Equipment> findAvailableOnDate(LocalDate date);
}

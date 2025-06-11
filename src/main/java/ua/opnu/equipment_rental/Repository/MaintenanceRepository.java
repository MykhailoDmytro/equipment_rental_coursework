package ua.opnu.equipment_rental.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.opnu.equipment_rental.Model.Maintenance;

import java.util.List;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    List<Maintenance> findByEquipmentId(Long equipmentId);
}

package ua.opnu.equipment_rental.DTO;

import lombok.Data;
import ua.opnu.equipment_rental.Model.Maintenance;

import java.time.LocalDate;

@Data
public class MaintenanceResponseDTO {
    private Long id;
    private Long equipmentId;
    private LocalDate date;
    private String description;

    public MaintenanceResponseDTO(Maintenance maintenance) {
        this.id = maintenance.getId();
        this.equipmentId = maintenance.getEquipment().getId();
        this.date = maintenance.getDate();
        this.description = maintenance.getDescription();
    }
}

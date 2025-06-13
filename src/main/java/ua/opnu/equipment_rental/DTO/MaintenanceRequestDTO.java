package ua.opnu.equipment_rental.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MaintenanceRequestDTO {
    private Long equipmentId;
    private LocalDate date;
    private String description;
}

package ua.opnu.equipment_rental.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RentalRequestDTO {
    private Long equipmentId;
    private Long customerId;
    private Long employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean returned;
}


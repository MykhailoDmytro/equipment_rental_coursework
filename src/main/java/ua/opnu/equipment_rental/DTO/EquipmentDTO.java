package ua.opnu.equipment_rental.DTO;

import java.math.BigDecimal;

public record EquipmentDTO(
        Long id,
        String name,
        String type,
        BigDecimal dailyRate,
        Boolean availability
) {}
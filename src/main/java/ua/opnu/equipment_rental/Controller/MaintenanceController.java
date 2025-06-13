package ua.opnu.equipment_rental.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.equipment_rental.Model.Maintenance;
import ua.opnu.equipment_rental.DTO.MaintenanceRequestDTO;
import ua.opnu.equipment_rental.DTO.MaintenanceResponseDTO;
import ua.opnu.equipment_rental.Service.MaintenanceService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping
    public ResponseEntity<MaintenanceResponseDTO> addMaintenance(@RequestBody MaintenanceRequestDTO dto) {
        Maintenance saved = maintenanceService.addMaintenanceFromDTO(dto);
        return ResponseEntity.ok(new MaintenanceResponseDTO(saved));
    }

    @GetMapping("/{equipmentId}")
    public ResponseEntity<List<MaintenanceResponseDTO>> getMaintenanceByEquipment(@PathVariable Long equipmentId) {
        List<MaintenanceResponseDTO> maintenanceDTOs = maintenanceService.getMaintenanceByEquipmentDTO(equipmentId);
        return ResponseEntity.ok(maintenanceDTOs);
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<MaintenanceResponseDTO> getMaintenanceById(@PathVariable Long id) {
        Optional<Maintenance> maintenance = maintenanceService.getMaintenanceById(id);
        return maintenance
                .map(m -> ResponseEntity.ok(new MaintenanceResponseDTO(m)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable Long id) {
        maintenanceService.deleteMaintenance(id);
        return ResponseEntity.noContent().build();
    }
}

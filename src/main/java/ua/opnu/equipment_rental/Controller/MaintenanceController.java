package ua.opnu.equipment_rental.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.equipment_rental.Model.Maintenance;
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
    public ResponseEntity<Maintenance> addMaintenance(@RequestBody Maintenance maintenance) {
        return ResponseEntity.ok(maintenanceService.addMaintenance(maintenance));
    }

    @GetMapping("/{equipmentId}")
    public ResponseEntity<List<Maintenance>> getMaintenanceByEquipment(@PathVariable Long equipmentId) {
        List<Maintenance> maintenances = maintenanceService.getMaintenanceByEquipment(equipmentId);
        return ResponseEntity.ok(maintenances);
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<Maintenance> getMaintenanceById(@PathVariable Long id) {
        Optional<Maintenance> maintenance = maintenanceService.getMaintenanceById(id);
        return maintenance.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenance(@PathVariable Long id) {
        maintenanceService.deleteMaintenance(id);
        return ResponseEntity.noContent().build();
    }
}

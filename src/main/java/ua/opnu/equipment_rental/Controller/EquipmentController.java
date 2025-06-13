package ua.opnu.equipment_rental.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.equipment_rental.Model.Equipment;
import ua.opnu.equipment_rental.DTO.EquipmentDTO;
import ua.opnu.equipment_rental.Service.EquipmentService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @PostMapping
    public ResponseEntity<EquipmentDTO> addEquipment(@RequestBody EquipmentDTO equipmentDTO) {
        Equipment equipment = equipmentService.toEntity(equipmentDTO);
        return ResponseEntity.ok(equipmentService.addEquipment(equipment));
    }

    @GetMapping
    public ResponseEntity<List<EquipmentDTO>> getAllEquipment() {
        return ResponseEntity.ok(equipmentService.getAllEquipment());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentDTO> getEquipmentById(@PathVariable Long id) {
        Optional<EquipmentDTO> equipment = equipmentService.getEquipmentById(id);
        return equipment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipmentDTO> updateEquipment(@PathVariable Long id, @RequestBody EquipmentDTO equipmentDTO) {
        try {
            Equipment updated = equipmentService.toEntity(equipmentDTO);
            return ResponseEntity.ok(equipmentService.updateEquipment(id, updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Long id) {
        equipmentService.deleteEquipment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    public ResponseEntity<List<EquipmentDTO>> getAvailableEquipmentOnDate(@RequestParam("date") String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(equipmentService.getAvailableEquipmentOnDate(localDate));
    }
}

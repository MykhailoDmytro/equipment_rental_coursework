package ua.opnu.equipment_rental.Service;

import org.springframework.stereotype.Service;
import ua.opnu.equipment_rental.Model.Equipment;
import ua.opnu.equipment_rental.Model.Maintenance;
import ua.opnu.equipment_rental.DTO.MaintenanceRequestDTO;
import ua.opnu.equipment_rental.DTO.MaintenanceResponseDTO;
import ua.opnu.equipment_rental.Repository.EquipmentRepository;
import ua.opnu.equipment_rental.Repository.MaintenanceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final EquipmentRepository equipmentRepository;

    public MaintenanceService(MaintenanceRepository maintenanceRepository,
                              EquipmentRepository equipmentRepository) {
        this.maintenanceRepository = maintenanceRepository;
        this.equipmentRepository = equipmentRepository;
    }

    public Maintenance addMaintenanceFromDTO(MaintenanceRequestDTO dto) {
        Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
        Maintenance maintenance = Maintenance.builder()
                .equipment(equipment)
                .date(dto.getDate())
                .description(dto.getDescription())
                .build();
        return maintenanceRepository.save(maintenance);
    }

    public List<MaintenanceResponseDTO> getMaintenanceByEquipmentDTO(Long equipmentId) {
        return maintenanceRepository.findByEquipmentId(equipmentId)
                .stream()
                .map(MaintenanceResponseDTO::new)
                .toList();
    }

    public Optional<Maintenance> getMaintenanceById(Long id) {
        return maintenanceRepository.findById(id);
    }

    public void deleteMaintenance(Long id) {
        maintenanceRepository.deleteById(id);
    }
}

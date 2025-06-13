package ua.opnu.equipment_rental.Service;

import org.springframework.stereotype.Service;
import ua.opnu.equipment_rental.Model.Equipment;
import ua.opnu.equipment_rental.DTO.EquipmentDTO;
import ua.opnu.equipment_rental.Repository.EquipmentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public EquipmentDTO addEquipment(Equipment equipment) {
        return toDTO(equipmentRepository.save(equipment));
    }

    public List<EquipmentDTO> getAllEquipment() {
        return equipmentRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public Optional<EquipmentDTO> getEquipmentById(Long id) {
        return equipmentRepository.findById(id).map(this::toDTO);
    }

    public EquipmentDTO updateEquipment(Long id, Equipment updatedEquipment) {
        return equipmentRepository.findById(id)
                .map(equipment -> {
                    equipment.setName(updatedEquipment.getName());
                    equipment.setType(updatedEquipment.getType());
                    equipment.setDailyRate(updatedEquipment.getDailyRate());
                    equipment.setAvailability(updatedEquipment.getAvailability());
                    return toDTO(equipmentRepository.save(equipment));
                })
                .orElseThrow(() -> new RuntimeException("Equipment not found with id " + id));
    }

    public void deleteEquipment(Long id) {
        equipmentRepository.deleteById(id);
    }

    public List<EquipmentDTO> getAvailableEquipmentOnDate(LocalDate date) {
        return equipmentRepository.findAvailableOnDate(date).stream()
                .map(this::toDTO)
                .toList();
    }

    public EquipmentDTO toDTO(Equipment equipment) {
        return new EquipmentDTO(
                equipment.getId(),
                equipment.getName(),
                equipment.getType(),
                equipment.getDailyRate(),
                equipment.getAvailability()
        );
    }

    public Equipment toEntity(EquipmentDTO dto) {
        Equipment equipment = new Equipment();
        equipment.setId(dto.id());
        equipment.setName(dto.name());
        equipment.setType(dto.type());
        equipment.setDailyRate(dto.dailyRate());
        equipment.setAvailability(dto.availability());
        return equipment;
    }
}

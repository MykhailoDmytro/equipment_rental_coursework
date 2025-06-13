package ua.opnu.equipment_rental.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.equipment_rental.DTO.EquipmentDTO;
import ua.opnu.equipment_rental.DTO.RentalDTO;
import ua.opnu.equipment_rental.DTO.RentalRequestDTO;
import ua.opnu.equipment_rental.Model.*;
import ua.opnu.equipment_rental.Service.RentalService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    public ResponseEntity<RentalDTO> createRental(@RequestBody RentalRequestDTO dto) {
        Rental rental = rentalService.createRentalFromDTO(dto);
        return ResponseEntity.ok(rentalService.toDTO(rental));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<RentalDTO>> getRentalsByCustomer(@PathVariable Long customerId) {
        List<Rental> rentals = rentalService.getRentalsByCustomer(customerId);
        List<RentalDTO> rentalDTOs = rentals.stream()
                .map(rentalService::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(rentalDTOs);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<RentalDTO>> getRentalsByEmployee(@PathVariable Long employeeId) {
        List<Rental> rentals = rentalService.getRentalsByEmployee(employeeId);
        List<RentalDTO> rentalDTOs = rentals.stream()
                .map(rentalService::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(rentalDTOs);
    }

    @GetMapping("/active")
    public ResponseEntity<List<RentalDTO>> getActiveRentals() {
        List<Rental> rentals = rentalService.getActiveRentals();
        List<RentalDTO> rentalDTOs = rentals.stream()
                .map(rentalService::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(rentalDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable Long id) {
        Optional<Rental> rental = rentalService.getRentalById(id);
        return rental.map(value -> ResponseEntity.ok(rentalService.toDTO(value)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<RentalDTO> completeRental(@PathVariable Long id) {
        try {
            Rental rental = rentalService.completeRental(id);
            return ResponseEntity.ok(rentalService.toDTO(rental));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count-by-equipment")
    public ResponseEntity<List<Object[]>> countRentalsByEquipment() {
        return ResponseEntity.ok(rentalService.countRentalsByEquipment());
    }

    @GetMapping("/total-income")
    public ResponseEntity<Double> getTotalIncome() {
        return ResponseEntity.ok(rentalService.getTotalIncome());
    }

    @GetMapping("/most-rented-equipment")
    public ResponseEntity<List<EquipmentDTO>> getMostRentedEquipment() {
        List<EquipmentDTO> dtoList = rentalService.getMostRentedEquipmentDTO();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<RentalDTO>> getOverdueRentals() {
        List<Rental> rentals = rentalService.getOverdueRentals();
        List<RentalDTO> rentalDTOs = rentals.stream()
                .map(rentalService::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(rentalDTOs);
    }
}

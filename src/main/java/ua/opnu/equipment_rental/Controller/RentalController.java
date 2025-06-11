package ua.opnu.equipment_rental.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.equipment_rental.Model.Equipment;
import ua.opnu.equipment_rental.Model.Rental;
import ua.opnu.equipment_rental.Model.RentalRequestDTO;
import ua.opnu.equipment_rental.Service.RentalService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    public ResponseEntity<Rental> createRental(@RequestBody RentalRequestDTO dto) {

        Rental rental = rentalService.createRentalFromDTO(dto);
        return ResponseEntity.ok(rental);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Rental>> getRentalsByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(rentalService.getRentalsByCustomer(customerId));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Rental>> getRentalsByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(rentalService.getRentalsByEmployee(employeeId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Rental>> getActiveRentals() {
        return ResponseEntity.ok(rentalService.getActiveRentals());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        Optional<Rental> rental = rentalService.getRentalById(id);
        return rental.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Rental> completeRental(@PathVariable Long id) {
        try {
            Rental rental = rentalService.completeRental(id);
            return ResponseEntity.ok(rental);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
        return ResponseEntity.noContent().build();
    }

    // Додаткові endpoints:

    @GetMapping("/count-by-equipment")
    public ResponseEntity<List<Object[]>> countRentalsByEquipment() {
        return ResponseEntity.ok(rentalService.countRentalsByEquipment());
    }

    @GetMapping("/total-income")
    public ResponseEntity<Double> getTotalIncome() {
        return ResponseEntity.ok(rentalService.getTotalIncome());
    }

    @GetMapping("/most-rented-equipment")
    public ResponseEntity<List<Equipment>> getMostRentedEquipment() {
        return ResponseEntity.ok(rentalService.getMostRentedEquipment());
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Rental>> getOverdueRentals() {
        return ResponseEntity.ok(rentalService.getOverdueRentals());
    }
}

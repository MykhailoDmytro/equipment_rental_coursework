package ua.opnu.equipment_rental.Service;

import org.springframework.stereotype.Service;
import ua.opnu.equipment_rental.DTO.EquipmentDTO;
import ua.opnu.equipment_rental.DTO.RentalDTO;
import ua.opnu.equipment_rental.DTO.RentalRequestDTO;
import ua.opnu.equipment_rental.Model.*;
import ua.opnu.equipment_rental.Repository.CustomerRepository;
import ua.opnu.equipment_rental.Repository.EmployeeRepository;
import ua.opnu.equipment_rental.Repository.EquipmentRepository;
import ua.opnu.equipment_rental.Repository.RentalRepository;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final EquipmentRepository equipmentRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    public RentalService(RentalRepository rentalRepository,
                         EquipmentRepository equipmentRepository,
                         CustomerRepository customerRepository,
                         EmployeeRepository employeeRepository) {
        this.rentalRepository = rentalRepository;
        this.equipmentRepository = equipmentRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
    }

    public Rental createRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    public Rental createRentalFromDTO(RentalRequestDTO dto) {
        Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Rental rental = Rental.builder()
                .equipment(equipment)
                .customer(customer)
                .employee(employee)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .returned(dto.getReturned())
                .build();

        return rentalRepository.save(rental);
    }

    public List<Rental> getRentalsByCustomer(Long customerId) {
        return rentalRepository.findByCustomer_Id(customerId);
    }

    public List<Rental> getRentalsByEmployee(Long employeeId) {
        return rentalRepository.findByEmployee_Id(employeeId);
    }

    public List<Rental> getActiveRentals() {
        return rentalRepository.findByReturnedFalse();
    }

    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    public Rental completeRental(Long rentalId) {
        return rentalRepository.findById(rentalId)
                .map(rental -> {
                    rental.setReturned(true);
                    return rentalRepository.save(rental);
                })
                .orElseThrow(() -> new RuntimeException("Rental not found with id " + rentalId));
    }

    public void deleteRental(Long id) {
        rentalRepository.deleteById(id);
    }

    public List<Object[]> countRentalsByEquipment() {
        return rentalRepository.countRentalsByEquipment();
    }

    public double getTotalIncome() {
        List<Rental> rentals = rentalRepository.findAll();
        return rentals.stream()
                .filter(Rental::getReturned)
                .mapToDouble(r -> {
                    long days = ChronoUnit.DAYS.between(r.getStartDate(), r.getEndDate());
                    if (days == 0) days = 1;
                    return days * r.getEquipment().getDailyRate().doubleValue();
                })
                .sum();
    }

    public RentalDTO toDTO(Rental rental) {
        return new RentalDTO(
                rental.getId(),
                rental.getEquipment().getId(),
                rental.getEquipment().getName(),
                rental.getCustomer().getId(),
                rental.getCustomer().getName(),
                rental.getEmployee().getId(),
                rental.getEmployee().getName(),
                rental.getStartDate(),
                rental.getEndDate(),
                rental.getReturned()
        );
    }

    public Rental toEntity(RentalDTO dto) {
        Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return Rental.builder()
                .id(dto.getId())
                .equipment(equipment)
                .customer(customer)
                .employee(employee)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .returned(dto.getReturned())
                .build();
    }

    public List<EquipmentDTO> getMostRentedEquipmentDTO() {
        List<Long> equipmentIds = rentalRepository.findMostRentedEquipment();
        List<Equipment> equipmentList = equipmentRepository.findAllById(equipmentIds);

        return equipmentList.stream()
                .map(e -> new EquipmentDTO(
                        e.getId(),
                        e.getName(),
                        e.getType(),
                        e.getDailyRate(),
                        e.getAvailability()
                ))
                .collect(Collectors.toList());
    }

    public List<Rental> getOverdueRentals() {
        return rentalRepository.findOverdueRentals();
    }
}

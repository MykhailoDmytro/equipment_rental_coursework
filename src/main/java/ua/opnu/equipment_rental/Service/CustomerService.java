package ua.opnu.equipment_rental.Service;

import org.springframework.stereotype.Service;
import ua.opnu.equipment_rental.Model.Customer;
import ua.opnu.equipment_rental.DTO.CustomerDTO;
import ua.opnu.equipment_rental.Repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDTO addCustomer(Customer customer) {
        return toDTO(customerRepository.save(customer));
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public Optional<CustomerDTO> getCustomerById(Long id) {
        return customerRepository.findById(id).map(this::toDTO);
    }

    public CustomerDTO updateCustomer(Long id, Customer updatedCustomer) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setName(updatedCustomer.getName());
                    customer.setPhone(updatedCustomer.getPhone());
                    return toDTO(customerRepository.save(customer));
                })
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + id));
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public CustomerDTO toDTO(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getPhone());
    }

    public Customer toEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setId(dto.id());
        customer.setName(dto.name());
        customer.setPhone(dto.phone());
        return customer;
    }
}

package jack.payroll.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import jack.payroll.assembler.EmployeeAssembler;
import jack.payroll.controller.EmployeeController;
import jack.payroll.exception.EmployeeNotFoundException;
import jack.payroll.model.Employee;
import jack.payroll.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repo ;
	
	@Autowired
	private EmployeeAssembler assembler;
	
	public List<Employee> getAllEmployees() {
		
		return repo.findAll() // list of Employee object from the database
				.stream()
				.map( employee -> assembler.toModel(employee) ) // add HATEOAS links, self and employees
				.collect( Collectors.toList() );
	}
	
	public Employee getEmployeeById(Long id) {

		Employee employee = repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
		return assembler.toModel(employee);
		
	}
	
	
	public Employee addNewEmployee(Employee employee) {
		
		return repo.save(employee);
		
	}
	
	public Employee updateEmployeeById(Employee employee, Long id) {
		
		if ( repo.existsById(id) ) {
			employee.setId(id);
			return repo.save(employee);
		} else {
			throw new EmployeeNotFoundException(id);
		}
		
	}
	
	public void deleteEmployeeById(Long id) {
		
		repo.deleteById(id);
		
	}
	
}


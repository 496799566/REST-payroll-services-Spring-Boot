package jack.payroll.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import jack.payroll.controller.EmployeeController;
import jack.payroll.exception.EmployeeNotFoundException;
import jack.payroll.model.Employee;
import jack.payroll.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repo ;
	
	public List<Employee> getAllEmployees() {
		
		List<Employee> employeeList = repo.findAll();
		
		return employeeList.stream()
				.map( employee -> {
					employee.add( WebMvcLinkBuilder.linkTo( getController().getEmployeeById(employee.getId())	).withSelfRel());
					employee.add( WebMvcLinkBuilder.linkTo( getController().getAll()							).withRel("employees"));
					return employee;
				})
				.collect(Collectors.toList());
	}
	
	public Employee getEmployeeById(Long id) {
		Employee employee = repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
		
		Link self 		= WebMvcLinkBuilder.linkTo( getController().getEmployeeById(id)	).withSelfRel();
		Link employees 	= WebMvcLinkBuilder.linkTo( getController().getAll()			).withRel("employees");
		
		employee.add( self );
		employee.add( employees );
		
		return employee;
	}
	
	private EmployeeController getController() {
		return WebMvcLinkBuilder.methodOn(EmployeeController.class);
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

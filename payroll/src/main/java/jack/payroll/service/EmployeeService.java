package jack.payroll.service;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	public CollectionModel<Employee> getAllEmployees() {
		
		return assembler.toCollectionModel(repo.findAll())
				// add the link to self;
				.add( linkTo( methodOn(EmployeeController.class).getAll() ).withSelfRel());
	
	}
	
	public Employee getEmployeeById(Long id) {

		Employee employee = repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
		return assembler.toModel(employee);
		
	}
	
	
	public ResponseEntity<? extends Employee> addNewEmployee(Employee employee) {
		
		Employee model = assembler.toModel(repo.save(employee));
		
		return ResponseEntity
				.created( getNewEmployeeURI(employee) )
				.body( model);
		
	}
	
	public ResponseEntity<?> updateEmployeeById(Employee employee, Long id) {
		
		if ( repo.existsById(id) ) {
			
			employee.setId(id);
			repo.save(employee);
			
			return ResponseEntity
					.status(HttpStatus.NO_CONTENT)
					.location( getNewEmployeeURI( employee ) )
					.build();
		} else {
			throw new EmployeeNotFoundException(id);
		}
		
	}
	
	public ResponseEntity<?> deleteEmployeeById(Long id) {
		
		if ( repo.existsById(id) ) {
			repo.deleteById(id);
		}
		
		return ResponseEntity.noContent().build();
		
	}

	private URI getNewEmployeeURI(Employee employee) {
		return linkTo(methodOn(EmployeeController.class).getEmployeeById(employee.getId()))
				.withSelfRel()
				.toUri();
	}
}


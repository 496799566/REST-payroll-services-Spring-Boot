package jack.payroll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jack.payroll.model.Employee;
import jack.payroll.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("employees")
	public CollectionModel<Employee> getAll() {
		return employeeService.getAllEmployees();
	}
	
	@GetMapping("employees/{id}")
	public Employee getEmployeeById(@PathVariable Long id) {
		return employeeService.getEmployeeById(id);
	}
	
	@PostMapping("employees")
	public ResponseEntity<? extends Employee> addNewEmployee(@RequestBody Employee employee) {
		return employeeService.addNewEmployee(employee);
	}
	
	@PutMapping("employees/{id}")
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee, @PathVariable Long id) {
		return employeeService.updateEmployeeById(employee, id);
	}
	
	@DeleteMapping("employees/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
		return employeeService.deleteEmployeeById(id);
	}
}

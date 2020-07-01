package jack.payroll.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jack.payroll.exception.EmployeeNotFoundException;
import jack.payroll.model.Employee;
import jack.payroll.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repo ;
	
	public List<Employee> getAllEmployees() {
		return repo.findAll();
	}
	
	public Employee getEmployeeById(Long id) {
		return repo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
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

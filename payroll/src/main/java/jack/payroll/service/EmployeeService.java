package jack.payroll.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return repo.findById(id).get();
	}
	
	public Employee addNewEmployee(Employee employee) {
		return repo.save(employee);
	}
	
	public Employee updateEmployeeById(Employee employee, Long id) {
		employee.setId(id);
		return repo.save(employee);
	}
	
	public void deleteEmployeeById(Long id) {
		repo.deleteById(id);
	}
}

package jack.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import jack.payroll.model.Employee;

@Component
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}

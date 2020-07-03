package jack.payroll.assembler;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Component;

import jack.payroll.controller.EmployeeController;
import jack.payroll.model.Employee;

@Component
public class EmployeeAssembler implements RepresentationModelAssembler<Employee, Employee> {

	@Override
	public Employee toModel(Employee employee) {
		employee.add( linkTo( methodOn(EmployeeController.class).getEmployeeById(employee.getId())	).withSelfRel());
		employee.add( linkTo( methodOn(EmployeeController.class).getAll()							).withRel("employees"));
		return employee;
	}
	
}

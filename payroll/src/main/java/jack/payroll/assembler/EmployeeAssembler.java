package jack.payroll.assembler;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import jack.payroll.controller.EmployeeController;
import jack.payroll.model.Employee;

@Component
public class EmployeeAssembler implements RepresentationModelAssembler<Employee, Employee> {

	@Override
	public Employee toModel(Employee employee) {
		employee.add( WebMvcLinkBuilder.linkTo( getController().getEmployeeById(employee.getId())	).withSelfRel());
		employee.add( WebMvcLinkBuilder.linkTo( getController().getAll()							).withRel("employees"));
		return employee;
	}
	
	private EmployeeController getController() {
		return WebMvcLinkBuilder.methodOn(EmployeeController.class);
	}
}

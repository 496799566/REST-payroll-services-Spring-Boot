package jack.payroll.database;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jack.payroll.OrderStatus;
import jack.payroll.model.Employee;
import jack.payroll.model.Order;
import jack.payroll.repository.EmployeeRepository;
import jack.payroll.repository.OrderRepository;

@Configuration
public class LoadDatabase {
	
	// hard coded data 
	
	private List<Employee> employees = Arrays.asList(
				new Employee("Mahnoor Hudson", 		"Owner"				),
				new Employee("Hajra Burke", 		"CEO"				),
				new Employee("Dixie Vasquez", 		"Manager"			),
				new Employee("Christie Rowland",	"Accountant" 		),
				new Employee("Marlie Reilly", 		"System Manager"	),
				new Employee("Rehaan Wilkinson",	"Architect"			),
				new Employee("Yasser Sanford",		"Front end dev"		),
				new Employee("Heath Rodrigues", 	"Back end dev"		),
				new Employee("Charis Whitley", 		"Negotiator"		),
				new Employee("Arwa Santiago", 		"Cook"				)
			);
	
	private List<Order> orders = Stream.iterate(1, n -> n + 1)
				.limit( employees.size() )
				.map( n -> new Order( "Order number " + n , OrderStatus.IN_PROGRESS ) )
				.collect(Collectors.toList() );
	
	// end of hard coded data
	
	// Spring Boot will run ALL CommandLineRunner beans 
	// once the application context is loaded.
	@Bean
	public CommandLineRunner initDataBase(EmployeeRepository employee, OrderRepository order) {
		
		return args -> {
			storeSampleEmployee(employee);
			storeSampleOrder(order);
		};
	}

	private void storeSampleOrder(OrderRepository orderRepo) {
		
		orders.stream().forEach( orderRepo::save );
		
	}

	private void storeSampleEmployee(EmployeeRepository employeeRepo ) {
		
		employees.stream().forEach( employeeRepo::save );
				
	}
}

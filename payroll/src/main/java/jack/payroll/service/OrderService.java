package jack.payroll.service;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.hateoas.mediatype.problem.Problem.ExtendedProblem;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jack.payroll.OrderStatus;
import jack.payroll.assembler.OrderAssembler;
import jack.payroll.controller.OrderController;
import jack.payroll.exception.OrderNotFoundException;
import jack.payroll.model.Order;
import jack.payroll.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repo ;
	
	@Autowired
	private OrderAssembler assembler;
	
//	getMapping
	
	public CollectionModel<Order> getAllOrders() {
		
		return assembler.toCollectionModel(repo.findAll())
				// add the link to self;
				.add( linkTo( methodOn(OrderController.class).getAll() ).withSelfRel());
	
	}
	
	public Order getOrderById(Long id) {

		Order order = repo.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
		return assembler.toModel(order);
		
	}
	
//	postMapping
	
	public ResponseEntity<? extends Order> addNewOrder(Order order) {
		
		order.setOrderStatus(OrderStatus.IN_PROGRESS);
		Order model = assembler.toModel(repo.save(order));
		
		return ResponseEntity
				.created( getNewOrderURI(order) )
				.body( model);
		
	}
	
//	putMapping
	
	public ResponseEntity<?> updateOrderById(Order order, Long id) {
		
		if ( repo.existsById(id) ) {
			
			order.setId(id);
			repo.save(order);
			
			return ResponseEntity
					.status(HttpStatus.NO_CONTENT)
					.location( getNewOrderURI( order ) )
					.build();
		} else {
			throw new OrderNotFoundException(id);
		}
		
	}
	
	public ResponseEntity<?> orderComplete(Long id) {
		
		Order order = repo.findById(id).orElseThrow( () -> new OrderNotFoundException(id)); 
		
		if ( order.getOrderStatus().equals( OrderStatus.IN_PROGRESS ) ) {
			
			order.setOrderStatus(OrderStatus.COMPLETED);
			order = assembler.toModel( repo.save( order ) );
			
			return ResponseEntity.ok(order);
			
		} else {
			
			return ResponseEntity
					.status(HttpStatus.METHOD_NOT_ALLOWED)
					.body( getProblem(order) );
		}
		
		
	}	
	
	public ResponseEntity<?> orderCancel(Long id) {
		
		Order order = repo.findById(id).orElseThrow( () -> new OrderNotFoundException(id));
		
		if ( order.getOrderStatus().equals( OrderStatus.IN_PROGRESS ) ) {
			order.setOrderStatus(OrderStatus.CANCELLED);
			order = assembler.toModel( repo.save( order ) );
			return ResponseEntity.ok(order);
		}
		return ResponseEntity
				.status(HttpStatus.METHOD_NOT_ALLOWED)
				.body( getProblem(order) );
		
	}
	
	
//	deleteMapping
	
	public ResponseEntity<?> deleteOrderById(Long id) {
		
		if ( repo.existsById(id) ) {
			repo.deleteById(id);
		}
		
		return ResponseEntity.noContent().build();
		
	}

//	helper methods
	
	private URI getNewOrderURI(Order order) {
		return linkTo(methodOn(OrderController.class).getOrderById(order.getId()))
				.withSelfRel()
				.toUri();
	}
	
	// referenced from 
	// https://docs.spring.io/spring-hateoas/docs/current/reference/html/#mediatypes.hal-forms
	private ExtendedProblem<Map<String, Object>> getProblem(Order order) {
		return Problem
				.create()
				.withDetail("The order is already " + order.getOrderStatus() + "!")
				.withProperties( map -> {
					map.put("OrderStatus", order.getOrderStatus() );
					map.put("HttpStatus", "METHOD_NOT_ALLOWED" );
					map.put("HttpStatusCode", HttpStatus.METHOD_NOT_ALLOWED );
				});
	}

}


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

import jack.payroll.model.Order;
import jack.payroll.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@GetMapping("orders")
	public CollectionModel<Order> getAll() {
		return orderService.getAllOrders();
	}
	
	@GetMapping("orders/{id}")
	public Order getOrderById(@PathVariable Long id) {
		return orderService.getOrderById(id);
	}
	
	@PostMapping("orders")
	public ResponseEntity<? extends Order> addNewOrder(@RequestBody Order order) {
		return orderService.addNewOrder(order);
	}
	
	@PutMapping("orders/{id}")
	public ResponseEntity<?> updateOrder(@RequestBody Order order, @PathVariable Long id) {
		return orderService.updateOrderById(order, id);
	}
	
	@PutMapping("orders/{id}/complete")
	public ResponseEntity<?> orderComplete(@PathVariable Long id) {
		// TODO Auto-generated method stub
		return orderService.orderComplete(id);
	}
	
	@PutMapping("orders/{id}/cancel")
	public ResponseEntity<?> orderCancel(@PathVariable Long id) {
		// TODO Auto-generated method stub
		return orderService.orderCancel(id);
	}
	

	@DeleteMapping("orders/{id}")
	public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
		return orderService.deleteOrderById(id);
	}


}

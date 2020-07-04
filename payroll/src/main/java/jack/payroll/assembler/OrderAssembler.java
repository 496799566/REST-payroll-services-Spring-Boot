package jack.payroll.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import jack.payroll.OrderStatus;
import jack.payroll.controller.OrderController;
import jack.payroll.model.Order;

@Component
public class OrderAssembler implements RepresentationModelAssembler<Order, Order> {

	@Override
	public Order toModel(Order order) {
		
		order.add( linkTo( methodOn(OrderController.class).getOrderById(order.getId())	).withSelfRel());
		order.add( linkTo( methodOn(OrderController.class).getAll()						).withRel("orders"));
		
		if ( order.getOrderStatus().equals( OrderStatus.IN_PROGRESS) ) {
			order.add( createCancelLink(	order ) );
			order.add( createcompleteLink(	order ) );
		}
		
		return order;
	}

	private Link createcompleteLink(Order order) {
		// TODO Auto-generated method stub
		return linkTo(methodOn(OrderController.class)
		          .orderComplete(order.getId())).withRel("complete");
	}

	private Link createCancelLink(Order order) {
		
		return linkTo(methodOn(OrderController.class)
		          .orderCancel(order.getId())).withRel("cancel");
	}
	
	

}

package jack.payroll.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

import jack.payroll.OrderStatus;

@Entity
@Table(name = "Customer_Order")
public class Order extends RepresentationModel<Order>{

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private OrderStatus orderStatus;
	
	public Order() {
		
	}
	
	public Order(String description, OrderStatus orderStatus) {
		super();
		this.description = description;
		this.setOrderStatus(orderStatus);
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
}

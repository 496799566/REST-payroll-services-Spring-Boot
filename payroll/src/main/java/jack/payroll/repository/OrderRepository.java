package jack.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jack.payroll.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}

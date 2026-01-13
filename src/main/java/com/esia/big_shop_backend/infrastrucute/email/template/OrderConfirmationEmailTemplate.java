package com.esia.big_shop_backend.infrastrucute.email.template;

import com.esia.big_shop_backend.domain.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderConfirmationEmailTemplate {

    public String generateSubject(Order order) {
        return "Order Confirmation - " + order.getOrderNumber();
    }

    public String generateBody(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear Customer,\n\n");
        sb.append("Thank you for your order!\n");
        sb.append("Order Number: ").append(order.getOrderNumber()).append("\n");
        sb.append("Total Amount: ").append(order.getTotalAmount()).append("\n\n");
        sb.append("We will notify you when your order is shipped.\n\n");
        sb.append("Best regards,\n");
        sb.append("Big Shop Team");
        return sb.toString();
    }
}

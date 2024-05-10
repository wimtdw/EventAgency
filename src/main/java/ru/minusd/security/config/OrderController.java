package ru.minusd.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.minusd.security.domain.model.Order;
import ru.minusd.security.repository.OrderRepository;
import ru.minusd.security.repository.UserRepository;

@Controller
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository; // Предполагается, что репозиторий пользователей уже определен

    @GetMapping("/order")
    public String orderForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("users", userRepository.findAll());
        return "order";
    }

    @PostMapping("/order")
    public String submitOrder(@ModelAttribute Order order, BindingResult result) {
        if (!result.hasErrors()) {
            orderRepository.save(order);
            return "redirect:/order_success";
        }
        return "order";
    }
}

package ru.mirea.event.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.mirea.event.domain.dto.ItemRequest;
import ru.mirea.event.domain.dto.QueryDTO;
import ru.mirea.event.domain.dto.UserDTO;
import ru.mirea.event.domain.model.Item;
import ru.mirea.event.service.AuthenticationService;
import ru.mirea.event.service.ItemService;
import ru.mirea.event.service.QueryService;
import ru.mirea.event.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Tag(name = "Аутентификация")
public class AdminController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final QueryService queryService;
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPanel(){
        return "admin";
    }
    @GetMapping("/allqueries")
    @PreAuthorize("hasRole('ADMIN')")
    public String getQueries(Model model) {
        List<QueryDTO> queries = queryService.getAllQueries();
        model.addAttribute("queries", queries);
        return "allqueries";
    }
    @PostMapping("/updatestatus")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public String updateQueryStatus(@RequestParam("id") Long id, @RequestParam("status") String status) {
        // Проверьте, что id и статус не пустые и что пользователь имеет право на изменение статуса
        queryService.updateStatus(id, status);
        return "redirect:/admin/allqueries"; // Перенаправление на страницу со списком запросов после обновления
    }
    @GetMapping("/allusers")
    @PreAuthorize("hasRole('ADMIN')")
    public String getUsers(Model model) {
        List<UserDTO> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "allusers";
    }

    private final ItemService itemService;
    @GetMapping("/add")
    public String itemForm() {
        return "item";
    }

    @PostMapping("/add")
    public String itemCreate(@ModelAttribute ItemRequest itemRequest, @ModelAttribute Item item, Model model, RedirectAttributes redirectAttributes ) {
        try {
            itemService.createItem(itemRequest);
            model.addAttribute("item", item);
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", true);
            return "redirect:/";
        }
    }
}

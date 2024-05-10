package ru.minusd.security.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.minusd.security.domain.dto.QueryDTO;
import ru.minusd.security.domain.dto.UserDTO;
import ru.minusd.security.service.AuthenticationService;
import ru.minusd.security.service.QueryService;
import ru.minusd.security.service.UserService;

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
    /*@PostMapping("/deleteuser")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/allusers"; // Перенаправление на страницу со списком запросов после обновления
    }*/
}

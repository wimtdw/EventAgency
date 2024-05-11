package ru.mirea.event.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.mirea.event.domain.dto.*;
import ru.mirea.event.domain.dto.ItemDTO;
import ru.mirea.event.domain.dto.JwtAuthenticationResponse;
import ru.mirea.event.domain.dto.SignInRequest;
import ru.mirea.event.domain.dto.SignUpRequest;
import ru.mirea.event.service.AuthenticationService;
import ru.mirea.event.service.ItemService;
import ru.mirea.event.service.QueryService;
import ru.mirea.event.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {
    private final UserService userService;
    private final ItemService itemService;
    private final AuthenticationService authenticationService;
    private final QueryService queryService;

    @GetMapping("/register")
    public String getRegistrationForm() {
        return "register";
    }

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("/register")
    public RedirectView signUp(@ModelAttribute SignUpRequest request, RedirectAttributes redirectAttributes) {
        try {
            JwtAuthenticationResponse jwtResponse = authenticationService.signUp(request);
            redirectAttributes.addFlashAttribute("success", true);
            return new RedirectView("/login?success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", true);
            return new RedirectView("/register?error");
        }
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "login";
    }

    /*@Operation(summary = "Авторизация пользователя")
    @PostMapping("/login")
    public JwtAuthenticationResponse signIn(@ModelAttribute SignInRequest request) {
        return authenticationService.signIn(request);
    }*/
    @PostMapping("/login")
    public RedirectView signIn(@ModelAttribute SignInRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            JwtAuthenticationResponse jwtResponse = authenticationService.signIn(request);
            Cookie jwtCookie = new Cookie("JWT", jwtResponse.getToken());
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            // Установите максимальное время жизни cookie, например, соответствующее сроку действия токена
            jwtCookie.setMaxAge(24 * 60 * 60); // Для примера, 1 день
            response.addCookie(jwtCookie);
            return new RedirectView("/");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", true);
            return new RedirectView("/login?error");
        }
        // Перенаправление на главную страницу

    }

    @GetMapping("/logout_m")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Создание cookie с нулевым токеном для удаления существующего
        Cookie jwtCookie = new Cookie("JWT", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // Установка времени жизни Cookie в 0 для его удаления
        response.addCookie(jwtCookie);
        return "redirect:/test";
    }
//        return "redirect:/logout";
        // Перенаправление на страницу входа
        /*try {
            response.sendRedirect("/login");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    /*@GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getQueries(Model model) {
        List<QueryDTO> queries = queryService.getAllQueries();
        model.addAttribute("queries", queries);
        return "admin";
    }*/
    /*@PostMapping("/updatestatus")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public String updateQueryStatus(@RequestParam("id") Long id, @RequestParam("status") String status) {
        // Проверьте, что id и статус не пустые и что пользователь имеет право на изменение статуса
        queryService.updateStatus(id, status);
        return "redirect:/admin"; // Перенаправление на страницу со списком запросов после обновления
    }*/
    @GetMapping("/")
    public String showItems(Model model){
        List<ItemDTO> items = itemService.getAllItems();
        model.addAttribute("items", items);
        return "index";
    }

}


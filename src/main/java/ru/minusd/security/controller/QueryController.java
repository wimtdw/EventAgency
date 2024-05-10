package ru.minusd.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.minusd.security.domain.dto.JwtAuthenticationResponse;
import ru.minusd.security.domain.dto.QueryDTO;
import ru.minusd.security.domain.dto.QueryRequest;
import ru.minusd.security.domain.dto.SignUpRequest;
import ru.minusd.security.domain.model.Query;
import ru.minusd.security.domain.model.User;
import ru.minusd.security.service.AuthenticationService;
import ru.minusd.security.service.QueryService;
import ru.minusd.security.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/personal")
public class QueryController {
    private final UserService userService;
    @GetMapping
    public String personalPage(){
        return "personal";
    }
   /* @GetMapping("/myqueries")
    public String queryShow(){
        return "querylist";
    }*/
   @GetMapping("/myqueries")
   public String getQueries(Model model) {
       User user = userService.getCurrentUser();
       List<QueryDTO> queries = queryService.getQueriesByUser(user);
       model.addAttribute("queries", queries);
       return "querylist";
   }

    private final QueryService queryService;
    @GetMapping("/query")
    public String queryForm() {
        return "query";
    }


    /*public String querySubmit(@ModelAttribute Query query, Model model) {
        model.addAttribute("query", query);
        return "result";
    }*/
    @PostMapping("/query")
    public String queryCreate(@ModelAttribute QueryRequest queryRequest, @ModelAttribute Query query, Model model, RedirectAttributes redirectAttributes ) {
        try {
            queryService.createQuery(queryRequest);
            model.addAttribute("query", query);
            return "result";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", true);
            return "result";
        }
    }


}
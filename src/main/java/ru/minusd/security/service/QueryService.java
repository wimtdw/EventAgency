package ru.minusd.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.minusd.security.domain.dto.JwtAuthenticationResponse;
import ru.minusd.security.domain.dto.QueryDTO;
import ru.minusd.security.domain.dto.QueryRequest;
import ru.minusd.security.domain.dto.SignUpRequest;
import ru.minusd.security.domain.model.Query;
import ru.minusd.security.domain.model.Role;
import ru.minusd.security.domain.model.User;
import ru.minusd.security.repository.QueryRepository;
import ru.minusd.security.repository.UserRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryService {
    private final QueryRepository repository;
    private final UserService userService;
    public Query save(Query query) {
        return repository.save(query);
    }

    public Query create(Query query) {
        return save(query);
    }


    public void createQuery(QueryRequest request) {
        User user_cur = userService.getCurrentUser();
        var query = Query.builder()
                .name(request.getName())
                .text(request.getText())
                .user(user_cur)
                .status("В обработке")
                .date(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                .build();

        create(query);
    }
    public List<QueryDTO> getQueriesByUser(User user) {
        List<Query> queries = repository.findByUser(user);
        List<QueryDTO> dtos = new ArrayList<>();
        for (Query query : queries) {
            QueryDTO dto = new QueryDTO();
            dto.setName(query.getName());
            dto.setText(query.getText());
            dto.setEmail(query.getUser().getEmail());
            dto.setDate(query.getDate());
            dto.setStatus(query.getStatus());
            dtos.add(dto);
        }
        return dtos;
    }
}

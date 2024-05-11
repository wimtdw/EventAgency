package ru.mirea.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.mirea.event.domain.dto.QueryDTO;
import ru.mirea.event.domain.dto.QueryRequest;
import ru.mirea.event.domain.model.Query;
import ru.mirea.event.domain.model.User;
import ru.mirea.event.repository.QueryRepository;

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
            dto.setUsername(query.getUser().getUsername());
            dto.setId(query.getId());
            dto.setText(query.getText());
            dto.setEmail(query.getUser().getEmail());
            dto.setDate(query.getDate());
            dto.setStatus(query.getStatus());
            dtos.add(dto);
        }
        return dtos;
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void updateStatus(Long id, String status){
        Query query = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный ID запроса"));
        query.setStatus(status);
        repository.save(query);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<QueryDTO> getAllQueries() {
        List<Query> queries = repository.findAll();
        List<QueryDTO> dtos = new ArrayList<>();
        for (Query query : queries) {
            QueryDTO dto = new QueryDTO();
            dto.setName(query.getName());
            dto.setUsername(query.getUser().getUsername());
            dto.setId(query.getId());
            dto.setText(query.getText());
            dto.setEmail(query.getUser().getEmail());
            dto.setDate(query.getDate());
            dto.setStatus(query.getStatus());
            dtos.add(dto);
        }
        return dtos;
    }
}

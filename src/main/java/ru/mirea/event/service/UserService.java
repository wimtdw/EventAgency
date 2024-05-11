package ru.mirea.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mirea.event.domain.dto.UserDTO;
import ru.mirea.event.domain.model.Role;
import ru.mirea.event.domain.model.User;
import ru.mirea.event.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public User save(User user) {
        return repository.save(user);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            // Заменить на свои исключения
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        return save(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }


    /**
     * Выдача прав администратора текущему пользователю
     * <p>
     * Нужен для демонстрации
     */
    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    public void deleteUser(Long id){
//        User currentUser = getCurrentUser();
//        // Проверяем, не является ли удаляемый пользователь текущим пользователем
//        if (currentUser.getId().equals(id)) {
//            try {
//                throw new Exception("Нельзя удалить текущего пользователя");
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//        // Удаление пользователя, если он не является текущим
//        repository.deleteById(id);
//    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAllUsers() {
        List<User> users = repository.findAll();
        List<UserDTO> dtos = new ArrayList<>();
        for (User user : users) {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            dto.setRole(String.valueOf(user.getRole()));
            dtos.add(dto);
        }
        return dtos;
    }
}

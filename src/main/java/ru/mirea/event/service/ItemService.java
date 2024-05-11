package ru.mirea.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.event.domain.dto.*;
import ru.mirea.event.domain.model.Item;
import ru.mirea.event.domain.model.User;
import ru.mirea.event.repository.ItemRepository;
import ru.mirea.event.domain.dto.ItemDTO;
import ru.mirea.event.domain.dto.ItemRequest;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository repository;
    private final UserService userService;
    public Item save(Item item) {
        return repository.save(item);
    }

    public Item create(Item item) {
        return save(item);
    }


    public List<ItemDTO> getAllItems() {
        List<Item> items = repository.findAll();
        List<ItemDTO> dtos = new ArrayList<>();
        for (Item item : items) {
            ItemDTO dto = new ItemDTO();
            dto.setName(item.getName());
            dto.setText(item.getText());
            dto.setImage(item.getImage());
            dtos.add(dto);
        }
        return dtos;
    }
    public void createItem(ItemRequest request) {
        User user_cur = userService.getCurrentUser();
        var item = Item.builder()
                .name(request.getName())
                .text(request.getText())
                .image(request.getImage())
                .author(user_cur)
                .build();

        create(item);
    }
}


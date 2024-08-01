package enigma.to_do_list.service;

import enigma.to_do_list.model.Status;
import enigma.to_do_list.model.Todo;
import enigma.to_do_list.utils.DTO.TodoCreateDTO;
import enigma.to_do_list.utils.DTO.TodoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.Date;

public interface TodoService {
    Todo create(TodoCreateDTO request, Authentication authentication);
    Page<Todo> getAll(Pageable pageable, String status, Date due_date, Authentication authentication);
    Todo getOne(Integer id, Authentication authentication);
    Todo update(Integer id, TodoUpdateDTO request, Authentication authentication);
    Todo updateStatus(TodoUpdateDTO request, Integer id, Authentication authentication);
    void delete(Integer id, Authentication authentication);
}

package enigma.to_do_list.service.implementation;

import enigma.to_do_list.model.Status;
import enigma.to_do_list.model.Todo;
import enigma.to_do_list.model.UserEntity;
import enigma.to_do_list.repository.TodoRepository;
import enigma.to_do_list.service.TodoService;
import enigma.to_do_list.service.UserService;
import enigma.to_do_list.utils.DTO.TodoCreateDTO;
import enigma.to_do_list.utils.DTO.TodoUpdateDTO;
import enigma.to_do_list.utils.specification.TodoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoRepository todoRepository;
    private final UserService userService;

    @Override
    public Todo create(TodoCreateDTO request, Authentication auth) {
        UserEntity user = userService.getByEmail(auth.getName());
        Todo todo = new Todo();
        todo.setUser(user);
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setDueDate(request.getDueDate());
        todo.setCreatedAt(LocalDate.now());
        todo.setStatus(Status.IN_PROGRESS);
        return todoRepository.save(todo);
    }

    @Override
    public Page<Todo> getAll(Pageable pageable, String status, Date due_date, Authentication auth) {
        UserEntity user = userService.getByEmail(auth.getName());
        Integer id = user.getId();

        if (auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            Specification<Todo> specification = TodoSpecification.getSpecification(null, status, due_date);
            return todoRepository.findAll(specification, pageable);
        } else {
            Specification<Todo> specification = TodoSpecification.getSpecification(id, status, due_date);
            return todoRepository.findAll(specification, pageable);
        }
    }

    @Override
    public Todo getOne(Integer id, Authentication auth) {
        UserEntity user = userService.getByEmail(auth.getName());
        Optional<Todo> todo = todoRepository.findById(id);

        if (auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            return todoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException());
        }
        if (todo.get().getUser() == user) {
            return todoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException());
        } else return null;
    }

    @Override
    public Todo update(Integer id, TodoUpdateDTO request, Authentication auth) {
        Todo todo = this.getOne(id, auth);
        UserEntity user = userService.getByEmail(auth.getName());
        todo.setUser(user);
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setDueDate(request.getDueDate());
        Status status = Status.valueOf(request.getStatus().toUpperCase());
        todo.setStatus(status);
        return todoRepository.save(todo);
    }

    @Override
    public Todo updateStatus(TodoUpdateDTO request, Integer id, Authentication auth) {
        Todo updateStatus = this.getOne(id, auth);
        Status status = Status.valueOf(request.getStatus().toUpperCase());
        updateStatus.setStatus(status);
        return todoRepository.save(updateStatus);
    }


    @Override
    public void delete(Integer id, Authentication auth) {
        UserEntity user = userService.getByEmail(auth.getName());
        Todo todo = this.getOne(id, auth);
        if (todo.getUser() == user) {
            todoRepository.deleteById(id);
        }
    }
}

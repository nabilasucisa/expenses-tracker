package enigma.to_do_list.controller;

import enigma.to_do_list.model.Status;
import enigma.to_do_list.model.Todo;
import enigma.to_do_list.service.TodoService;
import enigma.to_do_list.utils.DTO.TodoCreateDTO;
import enigma.to_do_list.utils.DTO.TodoUpdateDTO;
import enigma.to_do_list.utils.PageResponse;
import enigma.to_do_list.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody TodoCreateDTO request, Authentication authentication) {
        return Response.renderJson(
                HttpStatus.CREATED,
                todoService.create(request, authentication)
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(size = 10) Pageable pageable,
                                    @RequestParam(required = false) String status,
                                    @RequestParam(required = false) Date due_date,
                                    Authentication authentication) {
        Page<Todo> task = todoService.getAll(pageable, status, due_date, authentication);
        PageResponse<Todo> result = new PageResponse<>(task);
        return Response.renderJson(
                HttpStatus.OK,
                result
        );
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getOne(@PathVariable Integer id, Authentication authentication) {
        return Response.renderJson(
                HttpStatus.OK,
                todoService.getOne(id, authentication)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody TodoUpdateDTO request,
                                    @PathVariable Integer id,
                                    Authentication authentication) {
        return Response.renderJson(
                HttpStatus.OK,
                todoService.update(id, request, authentication)
        );
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@RequestBody TodoUpdateDTO request,
                                          @PathVariable Integer id,
                                          Authentication authentication) {
        return Response.renderJson(
                HttpStatus.OK,
                todoService.updateStatus(request, id, authentication)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id, Authentication authentication) {
        todoService.delete(id, authentication);
        return Response.renderJson1(
                HttpStatus.NO_CONTENT
        );
    }
}

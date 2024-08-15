package enigma.expenses_tracker.controller;

import enigma.expenses_tracker.utils.Response;
import enigma.expenses_tracker.model.UserEntity;
import enigma.expenses_tracker.service.UserService;
import enigma.expenses_tracker.utils.DTO.UserDTO;
import enigma.expenses_tracker.utils.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<?> createAdmin(@RequestBody UserDTO request) {
        return Response.renderJson(
                HttpStatus.CREATED,
                userService.create(request)
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(size = 10) Pageable pageable,
                                    @RequestParam(required = false) String name ) {
        Page<UserEntity> user = userService.getAll(pageable, name);
        PageResponse<UserEntity> result = new PageResponse<>(user);
        return Response.renderJson(
                HttpStatus.OK,
                result
        );
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getOne(@PathVariable Integer id) {
        return Response.renderJson(
                HttpStatus.OK,
                userService.getOne(id)
        );
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody UserDTO request,
                                    @PathVariable Integer id) {
        return Response.renderJson(
                HttpStatus.OK,
                userService.update(request, id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        userService.delete(id);
        return Response.renderJson1(
                HttpStatus.NO_CONTENT
        );
    }
}

package enigma.to_do_list.controller;

import enigma.to_do_list.model.UserEntity;
import enigma.to_do_list.service.UserService;
import enigma.to_do_list.utils.DTO.UserDTO;
import enigma.to_do_list.utils.PageResponse;
import enigma.to_do_list.utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${superadmin.secret}")
    private String superAdminSecretKey;
    @Value("${admin.secret}")
    private String adminSecretKey;

    @PostMapping("/users")
    public ResponseEntity<?> createAdmin(@RequestBody UserDTO request) {
        return Response.renderJson(
                HttpStatus.CREATED,
                userService.create(request)
        );
    }

    @PostMapping("/super-admin")
    public ResponseEntity<?> createSuperAdmin(@RequestBody UserDTO request, @RequestHeader(value = "X-Super-Admin-Secret-Key") String key) {
        if (key == null || !key.equals(superAdminSecretKey)) {
            return Response.renderJson1(
                    HttpStatus.UNAUTHORIZED
            );
        }
        return Response.renderJson(
                HttpStatus.CREATED,
                userService.createSuperAdmin(request)
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

    @PatchMapping("/users/{id}/role")
    public ResponseEntity<?> changeRole(@RequestBody UserDTO request,
                                        @PathVariable Integer id,
                                        @RequestHeader(value = "X-Admin-Secret-Key") String key) {
        if (key == null || !key.equals(adminSecretKey)) {
            return Response.renderJson1(
                    HttpStatus.UNAUTHORIZED
            );
        }
        return Response.renderJson(
                HttpStatus.OK,
                userService.changeRole(request, id)
        );
    }

//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> update(@RequestBody UserDTO request,
//                                    @PathVariable Integer id) {
//        return Response.renderJson(
//                HttpStatus.OK,
//                userService.update(request, id)
//        );
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> delete(@PathVariable Integer id) {
//        userService.delete(id);
//        return Response.renderJson(
//                HttpStatus.NO_CONTENT
//        );
//    }
}

package enigma.expenses_tracker.service;

import enigma.expenses_tracker.model.UserEntity;
import enigma.expenses_tracker.utils.DTO.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserEntity create(UserDTO request);
    Page<UserEntity> getAll(Pageable pageable, String name);
    UserEntity getOne(Integer id);
    UserEntity getByUsername(String username);
    UserEntity getByEmail(String email);
    UserEntity update(UserDTO request, Integer id);
    UserEntity changeRole(UserDTO request, Integer id);
    void delete(Integer id);
}

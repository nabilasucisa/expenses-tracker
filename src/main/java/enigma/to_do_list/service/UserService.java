package enigma.to_do_list.service;

import enigma.to_do_list.model.UserEntity;
import enigma.to_do_list.utils.DTO.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserEntity create(UserDTO request);
    UserEntity createSuperAdmin(UserDTO request);
    Page<UserEntity> getAll(Pageable pageable, String name);
    UserEntity getOne(Integer id);
    UserEntity getByUsername(String username);
    UserEntity getByEmail(String email);
    UserEntity update(UserDTO request, Integer id);
    UserEntity changeRole(UserDTO request, Integer id);
    void delete(Integer id);
}

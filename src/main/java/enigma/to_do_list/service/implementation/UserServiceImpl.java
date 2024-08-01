package enigma.to_do_list.service.implementation;

import enigma.to_do_list.model.Role;
import enigma.to_do_list.model.UserEntity;
import enigma.to_do_list.repository.UserRepository;
import enigma.to_do_list.service.UserService;
import enigma.to_do_list.utils.DTO.UserDTO;
import enigma.to_do_list.utils.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity create(UserDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User with this email already exists");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("User with this username already exists");
        }
        UserEntity createUser = new UserEntity();
        createUser.setEmail(request.getEmail());
        createUser.setUsername(request.getUsername());
        createUser.setPassword(passwordEncoder.encode(request.getPassword()));
        createUser.setRoles(Role.ROLE_ADMIN);
        createUser.setCreatedAt(LocalDate.now());
        return userRepository.save(createUser);
    }

    @Override
    public UserEntity createSuperAdmin(UserDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User with this email already exists");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("User with this username already exists");
        }
        UserEntity createUser = new UserEntity();
        createUser.setEmail(request.getEmail());
        createUser.setUsername(request.getUsername());
        createUser.setPassword(passwordEncoder.encode(request.getPassword()));
        createUser.setRoles(Role.ROLE_SUPER_ADMIN);
        createUser.setCreatedAt(LocalDate.now());
        return userRepository.save(createUser);
    }

    @Override
    public Page<UserEntity> getAll(Pageable pageable, String name) {
        Specification<UserEntity> specification = UserSpecification.getSpecification(name);
        return userRepository.findAll(specification, pageable);
    }

    @Override
    public UserEntity getOne(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("USER WITH ID " + id + " NOT FOUND"));
    }

    @Override
    public UserEntity getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("USER NOT FOUND"));
    }

    @Override
    public UserEntity getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("USER NOT FOUND"));
    }

    @Override
    public UserEntity update(UserDTO request, Integer id) {
        UserEntity updatedUser = this.getOne(id);
        updatedUser.setEmail(request.getEmail());
        updatedUser.setUsername(request.getUsername());
        updatedUser.setPassword(passwordEncoder.encode(request.getPassword()));
        updatedUser.setRoles(Role.ROLE_ADMIN);
        return userRepository.save(updatedUser);
    }

    @Override
    public UserEntity changeRole(UserDTO request, Integer id) {
        UserEntity updateRole = this.getOne(id);
        Role roles = Role.valueOf(request.getRoles().toUpperCase());
        updateRole.setRoles(roles);
        return userRepository.save(updateRole);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}

package sk.practice.project.tomas.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.practice.project.tomas.dto.RoleDto;
import sk.practice.project.tomas.dto.UserDto;
import sk.practice.project.tomas.entity.Role;
import sk.practice.project.tomas.entity.User;
import sk.practice.project.tomas.exception.ResourceNotFoundException;
import sk.practice.project.tomas.repository.RoleRepository;
import sk.practice.project.tomas.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    public UserDetailServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto register(UserDto userDto) {

        User user = new User();
        BeanUtils.copyProperties(userDto, user);

        Set<Role> roles = new HashSet<>();

        for (RoleDto roleDto : userDto.getRoles()) {
            Optional<Role> optionalRole = roleRepository.findById(roleDto.getId());
            if (optionalRole.isPresent()) {
                roles.add(optionalRole.get());
            }
        }
        user.setRoles(roles);
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        user = userRepository.save(user);

        BeanUtils.copyProperties(user, userDto);

        Set<RoleDto> roleDtos = new HashSet<>();
        RoleDto roleDto = null;
        for (Role role : user.getRoles()) {
            roleDto = new RoleDto();
            roleDto.setRoleName(role.getRoleName());
            roleDto.setId(role.getId());
            roleDtos.add(roleDto);
        }
        userDto.setRoles(roleDtos);
        return userDto;
    }

    public Iterable<User> getList() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> userEntities = ((List<User>) userRepository.findAll())
                .stream()
                .filter(a -> a.getUsername().equals(username))
                .collect(Collectors.toList());

        if (userEntities.isEmpty()) {
            throw new ResourceNotFoundException("User not found with username: " + username);
        }

        User userEntity = userEntities.get(0);

        UserDto userModel = new UserDto();
        BeanUtils.copyProperties(userEntity, userModel);

        Set<RoleDto> roleModels = new HashSet<>();
        for (Role re : userEntity.getRoles()) {
            RoleDto roleModel = new RoleDto();
            roleModel.setRoleName(re.getRoleName());
            roleModel.setId(re.getId());
            roleModels.add(roleModel);
        }
        userModel.setRoles(roleModels);

        return userModel;
    }
}

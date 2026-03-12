package sk.practice.project.tomas.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sk.practice.project.tomas.dto.RoleDto;
import sk.practice.project.tomas.dto.UserDto;
import sk.practice.project.tomas.entity.Role;
import sk.practice.project.tomas.entity.User;
import sk.practice.project.tomas.exception.ResourceNotFoundException;
import sk.practice.project.tomas.repository.RoleRepository;
import sk.practice.project.tomas.repository.UserRepository;
import sk.practice.project.tomas.service.impl.UserDetailServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserDetailServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserDetailServiceImpl userDetailService;

    private User userEntity;

    private Role roleEntity;

    private UserDto userDto;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        roleEntity = new Role();
        roleEntity.setId(1L);
        roleEntity.setRoleName("ADMIN");

        userEntity = new User();
        userEntity.setId(1L);
        userEntity.setUsername("Tomas");
        userEntity.setPassword("password");
        userEntity.setRoles(Set.of(roleEntity));

        RoleDto roleDto = new RoleDto();
        roleDto.setId(1L);
        roleDto.setRoleName("ADMIN");

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("Tomas");
        userDto.setPassword("password");
        userDto.setRoles(Set.of(roleDto));
    }

    @Test
    void testRegisterUser(){

        when(roleRepository.findById(1L)).thenReturn(Optional.of(roleEntity));
        when(passwordEncoder.encode("password")).thenReturn("password");
        when(userRepository.save(any(User.class))).thenReturn(userEntity);

        UserDto result = userDetailService.register(userDto);

        assertNotNull(result);
        assertEquals("Tomas", result.getUsername());
        assertEquals(1, result.getRoles().size());
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testGetList(){
        when(userRepository.findAll()).thenReturn(List.of(userEntity));

        Iterable<User> result = userDetailService.getList();

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
        verify(userRepository).findAll();
    }

    @Test
    void testLoadUserByUsername_Success(){
        when(userRepository.findAll()).thenReturn(List.of(userEntity));

        UserDto result = (UserDto) userDetailService.loadUserByUsername("Tomas");

        assertNotNull(result);
        assertEquals("Tomas", result.getUsername());
        assertEquals(1, result.getRoles().size());
    }

    @Test
    void testLoadUserByUsername_NotFound(){
        when(userRepository.findAll()).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> userDetailService.loadUserByUsername("Username not found"));
    }
}

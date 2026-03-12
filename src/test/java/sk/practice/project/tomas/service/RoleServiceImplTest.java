package sk.practice.project.tomas.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sk.practice.project.tomas.dto.RoleDto;
import sk.practice.project.tomas.entity.Role;
import sk.practice.project.tomas.repository.RoleRepository;
import sk.practice.project.tomas.service.impl.RoleServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role roleEntity;

    private RoleDto roleDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        roleEntity = new Role();
        roleEntity.setId(1L);
        roleEntity.setRoleName("ADMIN");

        roleDto = new RoleDto();
        roleDto.setId(1L);
        roleDto.setRoleName("ADMIN");
    }

    @Test
    void testCreateRole() {
        when(roleRepository.save(any(Role.class))).thenReturn(roleEntity);

        RoleDto result = roleService.createRole(roleDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("ADMIN", result.getRoleName());
        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    void testGetAllRoles() {
        Role role2 = new Role();
        role2.setId(2L);
        role2.setRoleName("USER");

        when(roleRepository.findAll()).thenReturn(List.of(roleEntity, role2));

        List<RoleDto> result = roleService.getAllRoles();

        assertEquals(2, result.size());
        assertEquals("ADMIN", result.get(0).getRoleName());
        assertEquals("USER", result.get(1).getRoleName());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    void testGetRoleById() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(roleEntity));

        RoleDto result = roleService.getRoleById(1L);

        assertNotNull(result);
        assertEquals("ADMIN", result.getRoleName());
        verify(roleRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteRoleById() {
        doNothing().when(roleRepository).deleteById(1L);

        roleService.deleteRoleById(1L);

        verify(roleRepository, times(1)).deleteById(1L);
    }
}

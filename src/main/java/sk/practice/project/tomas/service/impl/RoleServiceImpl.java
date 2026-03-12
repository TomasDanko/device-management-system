package sk.practice.project.tomas.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.practice.project.tomas.dto.RoleDto;
import sk.practice.project.tomas.entity.Role;
import sk.practice.project.tomas.repository.RoleRepository;
import sk.practice.project.tomas.service.RoleService;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);

        Role role1 = roleRepository.save(role);

        BeanUtils.copyProperties(role1, roleDto);

        return roleDto;
    }

    @Override
    public List<RoleDto> getAllRoles() {
        Iterable<Role> roles = roleRepository.findAll();
        List<RoleDto> roleDtos = new ArrayList<>();
        RoleDto roleDto = null;
        for (Role role : roles) {
            roleDto = new RoleDto();
            BeanUtils.copyProperties(role, roleDto);
            roleDtos.add(roleDto);
        }
        return roleDtos;
    }

    @Override
    public RoleDto getRoleById(Long roleId) {
        Role role = roleRepository.findById(roleId).get();
        RoleDto roleDto = new RoleDto();
        BeanUtils.copyProperties(role, roleDto);
        return roleDto;
    }

    @Override
    public void deleteRoleById(Long roleId) {
        this.roleRepository.deleteById(roleId);
    }
}

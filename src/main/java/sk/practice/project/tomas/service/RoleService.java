package sk.practice.project.tomas.service;

import sk.practice.project.tomas.dto.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto createRole(RoleDto roleDto);

    List<RoleDto> getAllRoles();

    RoleDto getRoleById(Long roleId);

    void deleteRoleById(Long roleId);
}

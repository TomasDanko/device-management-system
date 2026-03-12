package sk.practice.project.tomas.service;

import sk.practice.project.tomas.dto.UserDto;

public interface UserService {

    Iterable<UserDto> list();
}

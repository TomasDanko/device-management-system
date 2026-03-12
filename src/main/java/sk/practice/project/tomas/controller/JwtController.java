package sk.practice.project.tomas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import sk.practice.project.tomas.dto.JwtRequest;
import sk.practice.project.tomas.dto.JwtResponse;
import sk.practice.project.tomas.dto.UserDto;
import sk.practice.project.tomas.entity.User;
import sk.practice.project.tomas.service.impl.UserDetailServiceImpl;
import sk.practice.project.tomas.utils.JwtUtil;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class JwtController {

    public static final String REGISTER_URI = "/register";

    public static final String LOGIN_URI = "/login";

    public static final String USER_URI = "/currentUser";

    public static final String USER_LIST = "/users";

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(REGISTER_URI)
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        UserDto userDto1 = userDetailService.register(userDto);
        ResponseEntity<UserDto> responseEntity = new ResponseEntity<>(userDto1, HttpStatus.CREATED);
        return responseEntity;
    }

    @PostMapping(LOGIN_URI)
    public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetails userDetails = userDetailService.loadUserByUsername(jwtRequest.getUsername());
        String jwtToken = jwtUtil.generateToken(userDetails);

        JwtResponse jwtResponse = new JwtResponse(jwtToken);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @GetMapping(USER_URI)
    public UserDto getCurrentUser(Principal principal) {
        UserDetails userDetails = this.userDetailService.loadUserByUsername(principal.getName());
        return (UserDto) userDetails;
    }

    @GetMapping(USER_LIST)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity getAll() {
        Iterable<User> list = userDetailService.getList();
        System.out.println(list);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}

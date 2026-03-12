package sk.practice.project.tomas.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import sk.practice.project.tomas.dto.UserDto;
import sk.practice.project.tomas.entity.User;
import sk.practice.project.tomas.service.impl.UserDetailServiceImpl;
import sk.practice.project.tomas.utils.JwtUtil;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sk.practice.project.tomas.controller.JwtController.REGISTER_URI;

@SpringBootTest
public class JwtControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailServiceImpl userDetailService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private JwtController jwtController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(jwtController).build();
    }

    @Test
    void testRegister_success() throws Exception {
        UserDto inputUser = new UserDto();
        inputUser.setUsername("Tomas");

        UserDto savedUser = new UserDto();
        savedUser.setUsername("Tomas");
        savedUser.setId(1L);

        when(userDetailService.register(any(UserDto.class))).thenReturn(savedUser);

        mockMvc.perform(post(REGISTER_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"Tomas\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("Tomas"));

        verify(userDetailService, times(1)).register(any(UserDto.class));
    }

    @Test
    void testGenerateToken_success() throws Exception {
        String username = "Tomas";
        String password = "password";
        String fakeToken = "fake-jwt-token";

        UserDetails mockUserDetails = new org.springframework.security.core.userdetails.User(
                username, password, Collections.emptyList()
        );

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(userDetailService.loadUserByUsername(username)).thenReturn(mockUserDetails);

        when(jwtUtil.generateToken(mockUserDetails)).thenReturn(fakeToken);

        String jsonRequest = String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password);

        mockMvc.perform(post("/api/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(fakeToken));
    }

    @Test
    void testGetCurrentUser_success() throws Exception {
        UserDto mockUser = new UserDto();
        mockUser.setUsername("john");
        mockUser.setId(1L);

        when(userDetailService.loadUserByUsername("john")).thenReturn(mockUser);

        mockMvc.perform(get("/api/user/currentUser")
                        .principal((Principal) () -> "john"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("john"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetAll_success() throws Exception {
        User u1 = new User();
        u1.setId(1L);
        u1.setUsername("Tomas");

        User u2 = new User();
        u2.setId(2L);
        u2.setUsername("Jan");

        when(userDetailService.getList()).thenReturn(List.of(u1, u2));

        mockMvc.perform(get("/api/user/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username").value("Tomas"))
                .andExpect(jsonPath("$[1].username").value("Jan"));

        verify(userDetailService, times(1)).getList();
    }
}

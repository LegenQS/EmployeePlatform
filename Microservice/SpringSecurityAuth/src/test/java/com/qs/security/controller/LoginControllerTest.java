package com.qs.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qs.security.domain.request.LoginRequest;
import com.qs.security.entity.Token;
import com.qs.security.entity.User;
import com.qs.security.domain.request.HRUser;
import com.qs.security.domain.request.UserToken;
import com.qs.security.security.JwtProvider;
import com.qs.security.service.TokenService;
import com.qs.security.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserService userService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private JwtProvider jwtProvider;


    @Test
    public void testLogin() throws Exception {
        String username = "testuser";
        String password = "testpass";
        LoginRequest loginRequest = new LoginRequest(username, password);

        given(authenticationManager.authenticate(any()))
                .willThrow(new BadCredentialsException("Invalid credentials"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(loginRequest)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }

    @Test
    public void testRegisterUser() throws Exception {

        UserToken userToken = new UserToken();
        userToken.setUsername("johndoe");
        userToken.setEmail("johndoe@example.com");
        userToken.setPassword("password");
        userToken.setToken("valid-token");

        Token token = new Token();
        token.setEmail(userToken.getEmail());
        token.setExpiration_date(new Timestamp(System.currentTimeMillis() + 1800000));

        User user = User.builder()
                .username(userToken.getUsername())
                .email(userToken.getEmail())
                .password(userToken.getPassword())
                .build();

        given(tokenService.validateToken("valid-token")).willReturn(token);
        given(userService.getUserByName(user.getUsername())).willReturn(null);
        given(userService.getUserByEmail(user.getEmail())).willReturn(null);
        given(userService.createNewUser(user, false)).willReturn(1);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(userToken);

        mockMvc.perform(MockMvcRequestBuilders.put("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.username").value(user.getUsername()))
                .andExpect(jsonPath("$.user.email").value(user.getEmail()))
                .andExpect(jsonPath("$.user.password").value(user.getPassword()));
    }

    @Test
    public void testRegisterHR() throws Exception {
        HRUser hrUser = new HRUser();
        hrUser.setEmail("test@example.com");
        hrUser.setPassword("password");
        hrUser.setUsername("testuser");
        hrUser.setAuthToken("sample_hr_token");

        User user = User.builder()
                .email(hrUser.getEmail())
                .password(hrUser.getPassword())
                .username(hrUser.getUsername())
                .build();

        given(userService.getUserByName(user.getUsername())).willReturn(null);
        given(userService.getUserByEmail(user.getEmail())).willReturn(null);
        given(userService.createNewUser(user, true)).willReturn(1);

        mockMvc.perform(MockMvcRequestBuilders.put("/hr-register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(hrUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.username").value(hrUser.getUsername()))
                .andExpect(jsonPath("$.user.email").value(hrUser.getEmail()));
//                .andExpect(jsonPath("$.user.password").doesNotExist());
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

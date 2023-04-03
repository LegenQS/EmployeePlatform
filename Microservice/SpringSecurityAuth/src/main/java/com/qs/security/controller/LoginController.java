package com.qs.security.controller;

import com.qs.security.domain.request.LoginRequest;
import com.qs.security.domain.response.LoginResponse;
import com.qs.security.domain.response.UserResponse;
import com.qs.security.entity.Token;
import com.qs.security.entity.User;
import com.qs.security.domain.request.HRUser;
import com.qs.security.domain.request.UserToken;
import com.qs.security.exception.InvalidCredentialsException;
import com.qs.security.exception.TokenExpiredOrWrongException;
import com.qs.security.security.AuthUserDetail;
import com.qs.security.security.JwtProvider;
import com.qs.security.service.TokenService;
import com.qs.security.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@PropertySource("classpath:application.yml")
@Api("authentication endpoints")
public class LoginController {

    private AuthenticationManager authenticationManager;
    private UserService userService;

    private TokenService tokenService;

    @Value("${auth.hr_registry.token}")
    private String hr_token;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager,
                                         UserService userService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    private JwtProvider jwtProvider;

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    //User trying to log in with username and password
    @PostMapping("/login")
    @ApiOperation(value = "all user login", response = LoginResponse.class)
    public LoginResponse login(@RequestBody LoginRequest request) throws InvalidCredentialsException{
        System.out.println(request.getUsername() + " " + request.getPassword());
        Authentication authentication;

        // Try to authenticate the user using the username and password
        try{
          authentication = authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
          );
        } catch (Exception e){
            e.printStackTrace();
            throw new InvalidCredentialsException("Incorrect credentials, please try again.");
        }

        //Successfully authenticated user will be stored in the authUserDetail object
        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal(); //getPrincipal() returns the user object

        System.out.println(authUserDetail);
        // A token wil be created using the username/email/userId and permission
        String token = jwtProvider.createToken(authUserDetail);

        System.out.println(authUserDetail.getAuthorities());
//        System.out.println("return password key");
        //Returns the token as a response to the frontend/postman
        return LoginResponse.builder()
                .message("Welcome " + authUserDetail.getUsername())
                .token(token)
                .build();

    }
    @PutMapping("/register")
    @ApiOperation(value = "register entry for employees only", response = UserResponse.class)
    public UserResponse registerUser(
            @RequestBody UserToken userToken) throws InvalidCredentialsException, TokenExpiredOrWrongException{

        User user = User.builder()
                .username(userToken.getUsername())
                .email(userToken.getEmail())
                .password(userToken.getPassword())
                .build();

        String token = userToken.getToken();

        if (token == null || token.trim().isEmpty()) throw new TokenExpiredOrWrongException("Token missed!");

        System.out.println(token);
        Token myToken = tokenService.validateToken(token);

        if (myToken == null) {
            throw new TokenExpiredOrWrongException("Token incorrect!");
        }
        else if (!myToken.getEmail().equals(user.getEmail())) {
            throw new TokenExpiredOrWrongException("Token not associated with your email!");
        }
        else if (myToken.getExpiration_date().getTime() < System.currentTimeMillis()) {
            throw new TokenExpiredOrWrongException("Token expired!");
        }

        if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null)
            throw new InvalidCredentialsException("Null input not allowed!");

        User exist_user = userService.getUserByName(user.getUsername());

        if (exist_user != null)
            throw new InvalidCredentialsException("User with name: " + user.getUsername() + " already exists!");

        exist_user = userService.getUserByEmail(user.getEmail());

        if (exist_user != null)
            throw new InvalidCredentialsException("User with email: " + user.getEmail() + " already exists!");

        Integer user_id = userService.createNewUser(user, false);

        return UserResponse.builder()
                .message("User registers successfully! Your user_id is: " + user_id)
                .user(user)
                .build();
    }

    @GetMapping("/register/validate/{user_id}")
    public String register_validate(@PathVariable Integer user_id) {
        User user = userService.getUserByID(user_id);

        long diff = System.currentTimeMillis() - user.getCreate_date().getTime();
        System.out.println(diff);
        long minutes = (diff / (60 * 1000));
        System.out.println(minutes);
        if (minutes > 15f) {
            return "time expired";
        }
        else {
            userService.updateUserStatus(user_id);
        }

        return "validated email address";
    }

    @PutMapping("/hr-register")
    @ApiOperation(value = "register entry for hr only", response = UserResponse.class)
    public UserResponse registerHR(
            @RequestBody HRUser hrUser) throws InvalidCredentialsException, TokenExpiredOrWrongException {

        System.out.println(hrUser);
        if (hrUser.getAuthToken() == null || hrUser.getAuthToken().trim().isEmpty()) {
            throw new TokenExpiredOrWrongException("Auth token missed");
        }
        if (!hrUser.getAuthToken().equals(hr_token)) {
            throw new TokenExpiredOrWrongException("Wrong auth token");
        }

        User user = User.builder()
                .email(hrUser.getEmail())
                .password(hrUser.getPassword())
                .username(hrUser.getUsername())
                .build();

        if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null)
            throw new InvalidCredentialsException("Null input not allowed");

        User exist_user = userService.getUserByName(user.getUsername());

        if (exist_user != null)
            throw new InvalidCredentialsException("User with name: " + user.getUsername() + " already exists!");

        exist_user = userService.getUserByEmail(user.getEmail());

        if (exist_user != null)
            throw new InvalidCredentialsException("User with email: " + user.getEmail() + " already exists!");

        Integer user_id = userService.createNewUser(user, true);
        String url = "http://localhost:9012/auth-service/register/validate/" + user_id;

        return UserResponse.builder()
                .message("Registers pending, please go to " + url + " in 15 min to validate your email address")
                .user(user)
                .build();
    }

    @GetMapping("/hr/register")
    @ApiOperation(value = "register entry for hr only", response = Token.class)
    public Token registerForUser(HttpServletRequest request, @RequestParam String email) {
        String username = request.getHeader("user-name");
        System.out.println(request.getHeader("user-name"));
        User user = userService.getUserByName(username);

        if (email == null || email.isEmpty()) return null;

        Token token = tokenService.generateToken(user.getUser_id(), email);

        return token;
    }

    @GetMapping("/user")
    @ApiOperation(value = "get info for user with username", response = User.class)
    public User getUserInfo(@RequestParam String username) {
        if (username.trim().isEmpty()) return new User();

        return userService.getUserByName(username);
    }
}

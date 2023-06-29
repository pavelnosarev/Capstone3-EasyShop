package org.yearup.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;
import org.yearup.models.authentication.LoginDto;
import org.yearup.models.authentication.LoginResponseDto;
import org.yearup.models.authentication.RegisterUserDto;
import org.yearup.security.jwt.JWTFilter;
import org.yearup.security.jwt.TokenProvider;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserDao userDao;
    private final ProfileDao profileDao;

    public AuthenticationController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserDao userDao, ProfileDao profileDao) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDao = userDao;
        this.profileDao = profileDao;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, false);

        try {
            User user = userDao.getByUserName(loginDto.getUsername());

            if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
            return new ResponseEntity<>(new LoginResponseDto(jwt, user), httpHeaders, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody RegisterUserDto newUser) {
        boolean exists = userDao.exists(newUser.getUsername());
        if (exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists.");
        }

        User user = userDao.create(new User(0, newUser.getUsername(), newUser.getPassword(), newUser.getRole()));
        Profile profile = new Profile();
        profile.setUserId(user.getId());
        profileDao.create(profile);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        HttpStatus status = ex.getStatus();
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(status).body(errorMessage);
    }

}

//@Controller
//class CustomErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
//
//    @RequestMapping("/error")
//    public ResponseEntity<String> handleError() {
//        // Handle the error and return an appropriate response
//        // You can customize the error message or perform any other error handling logic here
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred.");
//    }

//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }

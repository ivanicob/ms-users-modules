package com.ivanicob.userservice.controller.v1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivanicob.userservice.dto.model.user.UserResponse;
import com.ivanicob.userservice.model.AuthRequest;
import com.ivanicob.userservice.model.User;
import com.ivanicob.userservice.service.UserService;
import com.ivanicob.userservice.util.security.JwtUtil;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
public class AuthenticationController {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userService;    

    @GetMapping("/")
    public String welcome() {
        return "Welcome to USER SERVICE MODULE !!!";
    }	
    
    @PostMapping("/authenticate")
    public UserResponse generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
        	
        	jwtUtil.initUsers();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            
        }catch (DisabledException e){
        	e.printStackTrace();
        	throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
        	e.printStackTrace();
        	throw new Exception("INVALID_CREDENTIALS", e);
        } catch (Exception ex) {
        	ex.printStackTrace();
            throw new Exception("Inavalid username/password");
        }
        
        User user = userService.findByLogin(authRequest.getUsername());
        String token = jwtUtil.generateToken(authRequest.getUsername());
        UserResponse userResponse = new UserResponse();
        
        return  userResponse.convertEntityToUserResponse(user, token);
    }
    
}


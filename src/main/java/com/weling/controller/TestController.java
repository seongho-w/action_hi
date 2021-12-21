package com.weling.controller;



import com.weling.dto.JwtResponse;
import com.weling.dto.SignupRequestDto;
import com.weling.service.UserService;
import com.weling.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@CrossOrigin(origins = "https://www.weling.site/")
@RequiredArgsConstructor
@RestController
public class TestController {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    @GetMapping("/test")
    public String test(){
        String testStr = "Hi~~";
        System.out.println(testStr);

        return testStr;
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> createUser(@RequestBody SignupRequestDto userDto) throws Exception {
        System.out.println("회원가입");
        userService.registerUser(userDto);
        authenticate(userDto.getUsername(), userDto.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getUsername()));
    }

    @PostMapping("/article")
    public Article setArticle(PostArticleDto.Request request) throws IOException {
        return articleService.setArticle(request);
    }

    @GetMapping("/articles")
    public List<GetArticlesDto.Response> getArticles(){
        int i = 1/0;
        List<Article> articles = articleService.getArticles();
        List<GetArticlesDto.Response> response = modelMapper.map(articles, new TypeToken<List<GetArticlesDto.Response>>() {}.getType());
        return response;
    }




    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }



}

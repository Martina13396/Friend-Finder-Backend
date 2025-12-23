package com.example.friendfinderbackend.service.securityservice.token;

import com.example.friendfinderbackend.helper.JwtToken;
import com.example.friendfinderbackend.service.dto.securitydto.AccountDto;
import com.example.friendfinderbackend.service.securityservice.AccountService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TokenHandler {
    private String secretKey;
    private Duration time;

    private JwtBuilder jwtBuilder;

    private JwtParser jwtParser;

    private AccountService accountService;

    @Autowired
  public TokenHandler(JwtToken jwtToken,  AccountService accountService) {
      this.accountService = accountService;
      this.secretKey = jwtToken.getSecret();
      this.time = jwtToken.getTime();

      Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
      jwtBuilder = Jwts.builder().signWith(key);
      jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
  }

  public String createToken(AccountDto accountDto) {
        Date issueDate = new Date();
        Date exppirationDate = Date.from(issueDate.toInstant().plus(time));
        jwtBuilder.setSubject(accountDto.getEmail());
        jwtBuilder.setIssuedAt(issueDate);
        jwtBuilder.setExpiration(exppirationDate);
        jwtBuilder.claim("roles", accountDto.getRoles().stream().map(
                roleDto -> roleDto.getCode()).collect(Collectors.toList())
        );
        return jwtBuilder.compact();
  }

  public AccountDto validateToken(String token) {
   if(jwtParser.isSigned(token)) {
       Claims claims = jwtParser.parseClaimsJws(token).getBody();
       String email = claims.getSubject();
       Date issueDate = claims.getIssuedAt();
       Date exppirationDate = claims.getExpiration();

       AccountDto accountDto = accountService.getAccountByEmail(email);

       boolean isValidToken = Objects.nonNull(accountDto) && exppirationDate.after(new Date())
               && issueDate.before(exppirationDate);

       if(!isValidToken) {
           return null;
       }
       return accountDto;

   }
   return null;
  }
  
}

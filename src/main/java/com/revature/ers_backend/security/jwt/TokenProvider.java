package com.revature.ers_backend.security.jwt;

import java.security.Key;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
//import io.jsonwebtoken.io.Decoders;

//@Component
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";

    private final String base64Secret;

    private Key key;

    public TokenProvider(
        @Value("${jwt.base-64-secret}") String base64Secret
    ){
        this.base64Secret = base64Secret;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
    }
}

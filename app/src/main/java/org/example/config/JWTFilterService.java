package org.example.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilterService extends OncePerRequestFilter {
  @Value("${nexauth.secret}")
  private String secretKey;

  @Override
  public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws IOException, ServletException {
    String token = null;

    Cookie[] cookies = req.getCookies();
    if (cookies != null && cookies.length > 0) {
      for (Cookie cookie : cookies) {
        if ("next-auth.session-token".equals(cookie.getName())) {
          token = cookie.getValue();
          break;
        }
      }
    }

    if (token == null) {
      String authHeader = req.getHeader("Authorization");

      if (authHeader != null && authHeader.length() > 0 && authHeader.startsWith("Bearer")) {
        token = authHeader.split(" ")[1];
      }
    }

    if (token == null) {
      res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      res.getWriter().write("Missing or malformed Authorization header");
      return;
    }

    try {
      if (secretKey == null) {
        res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        res.getWriter().write("Secret key not configured");
        return;
      }
      Claims claims = Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token).getBody();

      System.out.println("Claims: " + claims);

      String email = claims.get("email", String.class);
      String userId = claims.get("userId", String.class);

      req.setAttribute("email", email);
      req.setAttribute("userId", userId);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Error while verifying JWT token E: " + e.getMessage());
      res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      res.getWriter().write("Invalid or expired JWT token");
      return;
    }

    filterChain.doFilter(req, res);
  }
}

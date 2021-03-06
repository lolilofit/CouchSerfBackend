package nsu.fit.upprpo.csbackend.security;

import nsu.fit.upprpo.csbackend.security.jwt.CookieUtils;
import nsu.fit.upprpo.csbackend.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;

    @Autowired
    public TokenAuthenticationFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        Cookie[] savedCookies = httpServletRequest.getCookies();
        String token = CookieUtils.getValueOf(JwtUtils.getCOOKIE_NAME(), savedCookies);

        if (token == null) {
            String headerToken = httpServletRequest.getHeader("Authorization");

            if (headerToken == null) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }

            //???
            if (headerToken.startsWith("Bearer "))
                token = headerToken.substring(7);
        }
        UserDetails userDetails = (userDetailsService).loadUserByUsername(JwtUtils.getUsernameFromToken(token));

        if (JwtUtils.validateToken(token, userDetails)) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

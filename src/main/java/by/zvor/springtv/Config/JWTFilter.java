package by.zvor.springtv.Config;

import by.zvor.springtv.Security.JWTUtil;
import by.zvor.springtv.Service.Interfaces.UserDetailsServiceImpl;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public JWTFilter(final JWTUtil jwtUtil, final UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, final FilterChain filterChain) throws ServletException, IOException {


        final String authHeader = httpServletRequest.getHeader("Authorization");

        if (null != authHeader && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            final String jwt = authHeader.substring(7);

            if (jwt.isBlank()) {
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid JWT Token in Bearer Header");
            } else {
                try {
                    final String login = this.jwtUtil.validateTokenAndRetrieveClaim(jwt);
                    final UserDetails userDetails = this.userDetailsService.loadUserByUsername(login);

                    final UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    userDetails.getPassword(),
                                    userDetails.getAuthorities());

                    if (null == SecurityContextHolder.getContext().getAuthentication()) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (final JWTVerificationException exc) {
                    httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            "Invalid JWT Token");
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}

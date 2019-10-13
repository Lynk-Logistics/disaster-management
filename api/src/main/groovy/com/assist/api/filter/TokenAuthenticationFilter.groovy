package com.assist.api.filter

import com.assist.api.security.CustomUserDetailsService
import com.assist.api.services.TokenProvider
import com.assist.api.util.Utils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider
    @Autowired
    Environment environment
    @Autowired
    private CustomUserDetailsService customUserDetailsService

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter)

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request)

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Long userId = tokenProvider.getUserIdFromToken(jwt)
                updateSecurityContext(userId,request)
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex)
        }

        filterChain.doFilter(request, response)
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        if(Utils.convertStringToBoolean(environment.getProperty('jwt.enabled'))){
            String bearerToken = request.getHeader("Authorization")
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7, bearerToken.length())
            }
        }else if(environment.getProperty('jwt.dummyUserId')){
            logger.warn('Running service without authentication')
            long userId = Long.parseLong(environment.getProperty('jwt.dummyUserId'))
            updateSecurityContext(userId,request)
        }
        return null
    }

    private updateSecurityContext(Long userId,HttpServletRequest request){
        UserDetails userDetails = customUserDetailsService.loadUserById(userId)
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request))
        SecurityContextHolder.getContext().setAuthentication(authentication)
    }
}

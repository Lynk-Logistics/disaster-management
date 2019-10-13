package com.assist.api.services

import io.jsonwebtoken.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class TokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(TokenProvider)

    @Value('${app.auth.tokenSecret}')
    private String tokenSecret
    @Value('${app.auth.tokenExpirationMsec}')
    private long tokenExpirationMsec

    String createToken(Long userId) {
        Date now = new Date()
        Date expiryDate = new Date(now.getTime() + tokenExpirationMsec)

        return Jwts.builder()
                .setSubject(Long.toString(userId))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact()
    }

    Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody()

        return Long.parseLong(claims.getSubject())
    }

    boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(authToken)
            return true
        } catch (SignatureException se) {
            logger.error("Invalid JWT signature",se)
        } catch (MalformedJwtException mje) {
            logger.error("Invalid JWT token",mje)
        } catch (ExpiredJwtException ejw) {
            logger.error("Expired JWT token",ejw)
        } catch (UnsupportedJwtException uje) {
            logger.error("Unsupported JWT token",uje)
        } catch (IllegalArgumentException iae) {
            logger.error("JWT claims string is empty",iae)
        }
        return false
    }
}

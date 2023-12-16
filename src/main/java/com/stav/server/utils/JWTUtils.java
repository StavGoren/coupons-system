package com.stav.server.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stav.server.consts.Constants;
import com.stav.server.dto.SuccessfulLoginDetails;
import com.stav.server.entities.User;
import com.stav.server.enums.ErrorType;
import com.stav.server.enums.UserType;
import com.stav.server.exceptions.ServerException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import netscape.javascript.JSObject;
import org.apache.tomcat.util.json.JSONParser;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class JWTUtils {

    public static Claims decodeJWT(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(Constants.JWT_KEY))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    public static String createJWT(SuccessfulLoginDetails successfulLoginData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonUserData = objectMapper.writeValueAsString(successfulLoginData);
        String id = String.valueOf(successfulLoginData.getUserId());
        String userType = String.valueOf(successfulLoginData.getUserType());
        String companyId = String.valueOf(successfulLoginData.getCompanyId());
        String token = createJWT(id, userType, jsonUserData, companyId, Constants.PERIOD_OF_24H_IN_MILLS);

        return token;


    }

    public static String getUserFromToken(String token) throws ServerException {
        Claims claims = decodeJWT(token);
        ObjectMapper objectMapper = new ObjectMapper();
//        User user = objectMapper.readValue(claims.getSubject(), User.class);
        return claims.getSubject();
    }

    private static String createJWT(String id, String userType, String subject, String companyId, long ttlMillis) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Constants.JWT_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(userType)
                .setAudience(companyId)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static void validatePermissionByUserType(UserType userType, String authorization) throws ServerException {
        String strUserType = getUserTypeByToken(authorization);
        if(!userType.toString().equals(strUserType)){
            throw new ServerException(ErrorType.ACTION_NOT_ALLOWED, " not authorised");
        }
    }

    public static void validateUserPermission(long id, String authorization) throws ServerException {
        long registeredId = getIdByToken(authorization);
        String registeredUserType = getUserTypeByToken(authorization);

        if (!registeredUserType.equals(UserType.Admin.toString())) {
            if (registeredId != id) {
                throw new ServerException(ErrorType.NOT_LOGGED_IN, " user with ID " + registeredId + " tried to invoke 'getCustomer()' on user with ID " + id);
            }
        }
    }

    public static String getUserTypeByToken(String token) throws ServerException {
        Claims claims = decodeJWT((token));
        return claims.getIssuer();
    }
    public static Long getIdByToken(String token) throws ServerException {
        Claims claims = decodeJWT(token);
        return Long.parseLong(claims.getId());
    }

    public static Long validateToken(String token) throws ServerException {
        Claims claims = decodeJWT(token);
        return Long.parseLong(claims.getId());
    }
}

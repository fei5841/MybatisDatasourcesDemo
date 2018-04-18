package com.liuchunshu.demo.util;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtToken {

//	private static final String SCREAT="chunshuscreat";
	
	public static String createToken(String screat) {
		
		Date iatDate=new Date();
		
		Calendar nowTime=Calendar.getInstance();
		nowTime.add(Calendar.MINUTE, 1);
		Date expiresDate=nowTime.getTime();
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("alg", "HS256");
		map.put("typ", "JWT");

		Algorithm algorithm = getAlgorithm(screat);
		String token= null;
		try {
			
			token=JWT.create().withHeader(map).withIssuer("liuchunshu").withIssuedAt(iatDate).withExpiresAt(expiresDate)
					.withJWTId(UUID.randomUUID().toString().replace("-", "")).withAudience("","","").withClaim("name", "test").withClaim("role", "doctor").withClaim("age", 28).sign(algorithm);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} 
		
		return token;
	}
	
	public static DecodedJWT verifyToken(String token,String screat) {
		Algorithm algorithm = getAlgorithm(screat);
		JWTVerifier verify=JWT.require(algorithm).build();
		DecodedJWT djwt=verify.verify(token);
		return djwt;
		
	}
	
	private static Algorithm getAlgorithm(String screat) {
		Algorithm algorithm = null;
		try {
			algorithm = Algorithm.HMAC256(screat);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return algorithm;
	}
}

package com.awasthi.bishal.Springblog.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.awasthi.bishal.Springblog.exception.BlogException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;



@Service
public class JwtProvider {
	
	
	
	private KeyStore key;
	
	@PostConstruct
	public void init() {
		
		 try {
	            key = KeyStore.getInstance("JKS");
	            InputStream resourceAsStream = getClass().getResourceAsStream("/blogfrontend.jks");
	            key.load(resourceAsStream, "12345678".toCharArray());
		 }
	            catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
	            throw new BlogException("Exception occured while loading keystore");
	        }
		
		
	}
	
	public String generatetoken(Authentication authentication) {
		
		User principal =(User)authentication.getPrincipal();
				return Jwts.builder()
		                .setSubject(principal.getUsername())
		                .signWith(getPrivateKey())
		                .compact();
	}
	
	private PrivateKey getPrivateKey() {
	
		try {
			return (PrivateKey) key.getKey("blogfrontend", "12345678".toCharArray());
		} catch (UnrecoverableKeyException |KeyStoreException|NoSuchAlgorithmException e) {
			
	
			 throw new BlogException("Exception occured while retrieving private key from keystore");
	}
	}

	public boolean validateToken(String jwt){
		Jwts.parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
		return true;
	}

	private PublicKey getPublickey() {
		try {
			return key.getCertificate("blogfrontend").getPublicKey();
		} catch (KeyStoreException e) {
			 throw new BlogException("Exception occured while retrieving public key from keystore");
		
		}

		
	}

	public String getUsernameFromJWT(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(getPublickey())
				.parseClaimsJws(token)
				.getBody();
		
		return claims.getSubject();
		
	}

}

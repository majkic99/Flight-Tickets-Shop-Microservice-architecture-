package softverskekomponente.userservice.security;



import java.io.IOException;
import java.util.ArrayList;
import static softverskekomponente.userservice.security.SecurityConstants.*;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import softverskekomponente.userservice.repositories.AdminRepository;
import softverskekomponente.userservice.repositories.UserRepository;


/**
 * Autorizacioja sluzi da proveri validnost JSON Web Tokena koji se nalazi u
 * poslatom requestu
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private UserRepository userRepo;
	private AdminRepository adminRepo;

	@Autowired
	public JWTAuthorizationFilter(AuthenticationManager authManager, UserRepository userRepo, AdminRepository adminRepo) {
		super(authManager);
		this.userRepo = userRepo;
		this.adminRepo = adminRepo;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		String token = req.getHeader(HEADER_STRING);

		UsernamePasswordAuthenticationToken authentication = getAuthentication(req, token);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, String token) {

		if (token != null) {
			// parsiranje tokena
			DecodedJWT jwt = JWT.require(Algorithm.HMAC512(SECRET.getBytes())).build()
					.verify(token.replace(TOKEN_PREFIX, ""));

			// subject je email od korisnika i spakovan je u JWT
			String email = jwt.getSubject();
			
			// Provera da li se nalazi user u bazi
			
			if (userRepo.existsByEmail(email) == false && adminRepo.existsByEmail(email) == false) {
				return null;
			}
			

			if (email != null) {
				
				return new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
			}
			return null;
		}
		return null;
	}
}
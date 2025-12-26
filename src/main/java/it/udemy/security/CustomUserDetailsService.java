package it.udemy.security;

import it.udemy.entities.Utenti;
import it.udemy.repositories.UtentiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final UtentiRepository utentiRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String errMsg = "";
		if(username == null || username.length() < 5) {
			errMsg = "Nome utente assente o non valido";
			log.warning(errMsg);
			throw new UsernameNotFoundException(errMsg);
		}
		
		Utenti utente = this.getUtente(username);
		if(utente == null) {
			errMsg = "Utente "+ username +" non trovato";
			log.warning(errMsg);
			throw new UsernameNotFoundException(errMsg);
		}
		
		User.UserBuilder builder = User.withUsername(utente.getUserid().trim());
		builder.disabled(!"Si".equalsIgnoreCase(utente.getAttivo()));
		builder.password(utente.getPassword().trim());
		
		String[] profili = utente.getRuoli()
							     .stream()
							     .map(ruolo -> ruolo.getRuolo().trim())
							     .toArray(String[]::new);
		builder.authorities(profili);
		
		return builder.build();
	}
	
	private Utenti getUtente(String username) {
		try {
			return utentiRepository.findByUserid(username);
		} catch(Exception e) {
			log.warning("Errore nel recupero dell'utente: " + e.getMessage());
			return null;
		}
	}
}

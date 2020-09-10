package br.com.casadocodigo.loja.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.casadocodigo.loja.models.Role;
import br.com.casadocodigo.loja.models.Usuario;

@Repository
@Transactional
public class UsuarioDAO implements UserDetailsService{

	@PersistenceContext
	private EntityManager manager;
	
	public Usuario loadUserByUsername(String email) {
		List<Usuario> usuarios = manager.createQuery("select u from Usuario u where email = :email", Usuario.class)
				.setParameter("email", email)
				.getResultList();
		
		if(usuarios.isEmpty()) {
			throw new UsernameNotFoundException("Usuario " + email + " não foi encontrado");
		}
		
		return usuarios.get(0);
	}

	public void gravar(Usuario usuario) {
		manager.persist(usuario);
	}

    public Optional<Usuario> procuraEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return Optional.empty();
        }

        return Optional.ofNullable(manager.find(Usuario.class, email));
    }

	public List<Usuario> listar() {
		
		return manager.createQuery("select u from Usuario u", Usuario.class)
				.getResultList();
	}

	public Usuario alteraRole(List<Role> roles, Usuario usuario) {
		
		Usuario user = manager.createQuery("select u from Usuario join fetch u.roles u where u.email = :email", Usuario.class).setParameter("email", usuario.getEmail()).getSingleResult();	
		
		user.setRoles(roles);
						
		return user;
		
	}
	
    public void atualizar(Usuario usuario) {
        manager.merge(usuario);
    }


}
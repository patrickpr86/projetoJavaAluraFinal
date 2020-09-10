package br.com.casadocodigo.loja.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.casadocodigo.loja.dao.UsuarioDAO;
import br.com.casadocodigo.loja.models.Usuario;

public class UsuarioValidation implements Validator{

	private UsuarioDAO usuarioDao;
	
	public UsuarioValidation(UsuarioDAO usuarioDao) {
		this.usuarioDao = usuarioDao;
	}


	@Override
	public boolean supports(Class<?> clazz) {
		return Usuario.class.isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "nome", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "email", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "senha", "field.required" );
		ValidationUtils.rejectIfEmpty(errors, "senhaRepetida", "field.required");
		
		
		
		Usuario usuario = (Usuario) target;
		if(!usuario.getSenha().equals(usuario.getSenhaRepetida())) {
			errors.reject("senha","field.notEquals");	
		}
		
		if(usuario.getSenha().length() < 5) {
			errors.rejectValue("senha", "field.notEnough");
			errors.reject("senhaRepetida");
		}
		
        if(usuarioDao.procuraEmail(usuario.getEmail()).isPresent()) {
            errors.rejectValue("email", "field.duplicate");
        }
		
		
	}
	
}

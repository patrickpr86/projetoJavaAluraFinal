package br.com.casadocodigo.loja.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.dao.RoleDAO;
import br.com.casadocodigo.loja.dao.UsuarioDAO;
import br.com.casadocodigo.loja.models.Role;
import br.com.casadocodigo.loja.models.Usuario;
import br.com.casadocodigo.loja.validation.UsuarioValidation;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioDAO usuarioDao;
	
	@Autowired
	private RoleDAO roleDao;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new UsuarioValidation(usuarioDao));
	}

	@RequestMapping("/form")
	public ModelAndView form(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView("usuarios/form");

		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET)    
    public ModelAndView listar() {
    	ModelAndView modelAndView = new ModelAndView("usuarios/lista").addObject("usuarios", usuarioDao.listar());
        return modelAndView;
    }

	@RequestMapping(value = "/roles" , method = RequestMethod.GET)
	public ModelAndView Role(@RequestParam String email, RedirectAttributes redirectAttributes) {
		
		Optional<Usuario> usuario = usuarioDao.procuraEmail(email);

        if (usuario.isPresent()) {
        	
        	 Collection<Role> rolesList = roleDao.listar();
             ModelAndView modelAndView = new ModelAndView("usuarios/roles");
             modelAndView.addObject("roles", rolesList );
             modelAndView.addObject("usuario", usuario.get());
             System.out.println(rolesList + " + " + rolesList.size());
             return modelAndView;             
            
        }

        return new ModelAndView("redirect:/usuarios");
	}

	@RequestMapping(value = "/roles" , method = RequestMethod.POST)
	public ModelAndView alteraRole(@ModelAttribute Usuario usuario ,  RedirectAttributes redirectAttributes) {

		System.out.println("Email: " + usuario.getEmail());
//		Usuario user = usuarioDao.loadUserByUsername(usuario.getEmail());
//		user.setRoles(usuario.getRoles());
//		
//		usuarioDao.atualizar(user);
        Optional<Usuario> optional = usuarioDao.procuraEmail(usuario.getEmail());

        if (optional.isPresent()) {
            Usuario user = optional.get();
            user.setRoles(usuario.getRoles());
            usuarioDao.atualizar(user);
        }
		
		return new ModelAndView("redirect:/usuarios");

	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView gravar(@Valid Usuario usuario, BindingResult result, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return form(usuario);
		}
		
		Role user = new Role("ROLE_USER");
		List<Role> roles = new ArrayList<>();
		roles.add(user);
		
		usuario.setRoles(roles);
		
		usuarioDao.gravar(usuario);

		redirectAttributes.addFlashAttribute("sucesso", "Novo usuario cadastrado");

		return new ModelAndView("redirect:/usuarios");
	}

}

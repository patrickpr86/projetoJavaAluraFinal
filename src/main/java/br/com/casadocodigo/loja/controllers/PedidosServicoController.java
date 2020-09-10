package br.com.casadocodigo.loja.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.models.Pedido;

@Controller
@RequestMapping("/pedidos")
public class PedidosServicoController {

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("")
	public ModelAndView pedidos(){
		
		String uri = "https://book-payment.herokuapp.com/orders";
		ResponseEntity<Pedido[]> pedidos = restTemplate.getForEntity(uri, Pedido[].class);
		
		ModelAndView modelAndView = new ModelAndView("pedidos/listar");
		modelAndView.addObject("pedidos" , pedidos.getBody());

		
		return modelAndView;
	}


}

package com.marcelomartins.desafiopitang.web.controller;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.marcelomartins.desafiopitang.model.Usuario;
import com.marcelomartins.desafiopitang.service.UsuarioService;


@Controller
@Path("/usuarios")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;

	@GetMapping("/cadastrar")
	public String cadastrar(Usuario obj) {
		return "usuario/insere";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "usuario/insere";
		}
		else {
			usuarioService.insert(usuario);
		}
		
		attr.addFlashAttribute("success", "Autor salvo com Sucesso.");
		return "redirect:/usuarios/listar";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("usuarios", usuarioService.findAll());
		return "usuario/lista";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Integer id, ModelMap model) {
		model.addAttribute("usuario", usuarioService.find(id));
		return "usuario/insere";
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/usuario/insere";
		} else {
			try {				
				usuarioService.update(usuario);
			} catch (DataIntegrityViolationException div) {
				result.reject("Este usuário não pôde ser inserido");
				return "usuario/insere";
			}
		}

		attr.addFlashAttribute("success", "Usuario atualizado com sucesso.");
		return "redirect:/usuarios/listar";
	}
	
	@GetMapping("/excluir/{id}")
	public String exluir(@PathVariable("id") Integer id, RedirectAttributes attr) {
		usuarioService.delete(id);
		attr.addFlashAttribute("success", "Usuário excluído com sucesso.");
		return "redirect:/usuarios/listar";
	}

}

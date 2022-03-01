package com.example.demo.controlador;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.objetos.Usuario;
import com.example.demo.service.IUsuarioService;


@Controller
public class MainController {
	
	@Autowired
	private IUsuarioService usuarioService;

	@GetMapping(value = {"/","/index"})
	public ModelAndView index() {
		
		ModelAndView mav = new ModelAndView();
		ArrayList<Usuario> usuarios= usuarioService.listarTodos();
		mav.addObject("usuarios", usuarios);
		mav.setViewName("index");
		return mav;
	}
	
	@GetMapping(value = "/signup")
	public ModelAndView getRegistrarse() {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("registro");
		
		return mav;
	}
	
	@PostMapping(value = "/signup")
	public String postRegistrarse(@RequestParam String nombre,@RequestParam String contrasenna) {
		
		
		Usuario u  = usuarioService.buscarPorNombre(nombre);
		String redirectCorrecto = "redirect:/";
		String redirectIncorrecto = "redirect:/signup";
		
		if (u.getId() == -1L) {
			Usuario usuario = new Usuario(nombre,contrasenna);
			usuarioService.guardar(usuario);
			return redirectCorrecto;
		}else {
			return redirectIncorrecto;
		}
			
	}
	
	@GetMapping(value = "/login")
	public ModelAndView getLogin(HttpSession session) {

		ModelAndView mav = new ModelAndView();

		mav.setViewName("login");
		return mav;
	}
	
	@GetMapping(value = "/perfil/{id}" )
	public ModelAndView getPerfil(@PathVariable Long id,HttpSession session) {

		Long iduser=(Long) session.getAttribute("idUsuario");
		ModelAndView mav = new ModelAndView();
		Usuario u = usuarioService.buscarPorId(id);
		mav.addObject("usuario", u);
		mav.addObject("iduser", iduser);
		mav.setViewName("perfil");
		return mav;
	}
	@GetMapping(value = "/actualizar/{id}" )
	public ModelAndView getActuaizar(@PathVariable Long id,HttpSession session) {

		Long iduser=(Long) session.getAttribute("idUsuario");
		ModelAndView mav = new ModelAndView();
		Usuario u = usuarioService.buscarPorId(id);
		mav.addObject("usuario", u);
		mav.addObject("iduser", iduser);
		mav.setViewName("actualizar");
		return mav;
	}
	
	@PostMapping(value = "/actualizar")
	public String postActualizar(@RequestParam String username,@RequestParam Long id) {
		
		
		Usuario u  = usuarioService.buscarPorId(id);
		String redirectCorrecto = "redirect:/perfil/"+u.getId();
		
			u.setNombre(username);
			usuarioService.actualizar(u);
			return redirectCorrecto;

			
	}
	@GetMapping(value = "/borrar/{id}")
	public String borrar(Model modelo,@PathVariable long id) {


		Usuario u = usuarioService.borrarPorId(id);
		if (u.getId() != -1) {

			return "redirect:/logout";
		} else {

			return "redirect:/perfil/" + u.getId();
		}

	}
	//a
}

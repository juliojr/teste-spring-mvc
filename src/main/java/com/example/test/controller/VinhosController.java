package com.example.test.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.test.model.TipoVinho;
import com.example.test.model.Vinho;
import com.example.test.repository.Vinhos;

@Controller
@RequestMapping("/vinhos")
public class VinhosController {

	@Autowired
	private Vinhos vinhos;

	@DeleteMapping("/{id}")
	public String remover(@PathVariable Long id, RedirectAttributes attributes) {
		vinhos.deleteById(id);

		attributes.addFlashAttribute("mensagem", "Vinho removido com sucesso!");

		return "redirect:/vinhos"; // redireciona para listar()
	}

	@GetMapping
	public ModelAndView listar() {

		ModelAndView mv = new ModelAndView("vinhos/lista-vinhos");
		mv.addObject("vinhos", vinhos.findAll());
		return mv;
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {

		return novo(vinhos.findById(id).get());

	}

	@GetMapping("/novo")
	public ModelAndView novo(Vinho vinho) {

		ModelAndView mv = new ModelAndView("vinhos/cadastro-vinho");
		mv.addObject(vinho);
		mv.addObject("tipos", TipoVinho.values());
		return mv;
	}

	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Vinho vinho, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return novo(vinho);
		}

		vinhos.save(vinho);

		attributes.addFlashAttribute("mensagem", "Vinho salvo com sucesso!");

		return new ModelAndView("redirect:/vinhos/novo");
	}
}

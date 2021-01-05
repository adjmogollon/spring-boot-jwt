package com.bolsadeideas.springboot.app.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("test")
public class TestController {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@RequestMapping(value = { "/test/inicio" }, method = RequestMethod.GET)
	public String inicio(Model model) {

		model.addAttribute("titulo", "Pagina de Prueba");
		logger.info("Estas en la pagina de prueba");
		return "/test/inicio.html";
	}

}

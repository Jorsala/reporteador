package org.fhi.reporteador;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws SQLException 
	 */
	@RequestMapping(value = "/hola", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws SQLException {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		String recibir = ""; 
		String listaDepto = "";
		String listasede="";
		
		mysqlConnection lista = new mysqlConnection();	
		//recibir = lista.consulta("");
		listaDepto = lista.Deptos();
		//Prueba P=new Prueba();
		
		model.addAttribute("listaDepto", listaDepto );
		model.addAttribute("listasede", listasede);
		model.addAttribute("tabla", recibir );
		
		return "graph";
	}
	
	
	@RequestMapping(value = "/Tipos", method = RequestMethod.GET)
	public @ResponseBody String Munis(@RequestParam("camp") String nomCampo, HttpServletRequest request) {		
		
		String response = "";
		mysqlConnection db = null;
		
		db = new mysqlConnection();
		response = db.Munis(nomCampo);
		db.Cerrar();				
		
		return response;
	}
	
	@RequestMapping(value = "/Datos", method = RequestMethod.GET)
	public @ResponseBody String tablaDatos(@RequestParam("depto") String depto, @RequestParam("sede") String muni, HttpServletRequest request) throws SQLException {		
		
		String response = "";
		String condicion = "";
		mysqlConnection db = null;
		if(!Objects.equals("0", depto)) {
			condicion += "and no.departamento = '" + depto + "'";
		}
		if(!Objects.equals("0", muni)) {
			condicion += " and no.sede = '" + muni + "'";
		}		
		db = new mysqlConnection();
		response = db.consulta(condicion);
		db.Cerrar();				
		
		return response;
	}
	
}

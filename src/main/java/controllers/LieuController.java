package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import models.Lieu;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import services.DepartementService;
import services.LieuService;

@Controller
public class LieuController {

	@Autowired
	private LieuService lieuService;

	@Autowired
	private DepartementService departementService;

	public LieuController(LieuService lieuService, DepartementService departementService) {
		this.lieuService = lieuService;
		this.departementService = departementService;
	}
	//---------

	@GetMapping("/lieux")
	public String listLieux(Model model, @RequestParam(name = "message", required = false) String message) {
		// Add the message to the model only if it's not empty
		if (message != null && !message.isEmpty()) {
			model.addAttribute("message", message);
		}
		List<Lieu> lieux = lieuService.getLieux();
		model.addAttribute("lieux", lieux);
		return "List_Lieux";
	}
	//---------

	@GetMapping("/lieu/new")
	public String createLieu(Model model) {

		Lieu lieu = new Lieu();
		model.addAttribute("lieu", lieu);

		model.addAttribute("departements", departementService.getDepartements());

		return "Create_Lieu";
	}
	
	@PostMapping("/lieu/save")
	public String registerLieu(@ModelAttribute("lieu") Lieu lieu) {
		// Save the Lieu using the service
		lieuService.saveLieu(lieu);

		// Redirect to the form
		return "redirect:/lieux";
	}

	@GetMapping("/lieu/{codeInsee}/delete")
	public String deleteLieu(@PathVariable("codeInsee") String codeInsee, RedirectAttributes redirectAttributes) {
		String message = "";
		if(!lieuService.getLieuById(codeInsee).getDepartement().getChefLieu().getCodeInsee().equals(codeInsee)){
		lieuService.deleteLieu(codeInsee);
		}
		else {
			message = "interdit de supprimer un chef lieu";
		}
		// Add the message to redirect attributes
		redirectAttributes.addAttribute("message", message);
		return "redirect:/lieux";
	}

}

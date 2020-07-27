package com.example.ejemploAppRecetas.contoller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.VeterinarySystem.entities.*;
import com.example.VeterinarySystem.repository.*;
import com.example.VeterinarySystem.service.PictureService;
import com.sun.xml.bind.v2.runtime.IllegalAnnotationsException;

@Controller
@RequestMapping("/recetas")
public class RecetasController {
	@Autowired
	private VeterinaryRepo repo;
	
	@Autowired
	PictureService picService;
	
	@RequestMapping("")
	public String index()
	{
		return "index";
	}
	
	@GetMapping("/signup")
	public String showSignUpForm(Recetas receta)
	{
		return "add_recipe";
	}
	
	@GetMapping("/list")
	public String showRecipes(Model model)
	{
		model.addAttribute("recipes", repo.findAll());
		return "list_recipes";
	}
	
	@RequestMapping("/login")
	public String showLogin()
	{
		return "login";
	}
	
	@GetMapping("/sc")
	public String showRecipes()
	{
		return "soundcloud.html";
	}
	
	@PreAuthorize("hasAuthority('Admin')")
	@RequestMapping
	public String showPrivate(Model model)
	{
		model.addAttribute("recipes",repo.findAll());
		return "list_recipes";
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@PostMapping("/add")
	public String addRecipe(Receta receta,BindingResult result, Model model, @RequestParam("file") MultipartFile file)
	{
		if(result.hasErrors())
		{
			return "add_recipe";
		}
		UUID idPic = UUID.randomUUID();
		picService.uploadPicture(file, idPic);
		receta.setFoto(idPic);
		repo.save(receta);
		return "redirect:list";
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") Long id,Model model)
	{
		Receta receta = repo.findById(id).orElseThrow(()->new IllegalArgumentException("Invalid recipe Id:")+id));
		model.addAttribute("recipe",receta);
		return "update_recipe";
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@PostMapping("/update/{id}")
	public String updateRecipe(@PathVariable("id") Long id, Receta receta, BindingResult result, Model model, @RequestParam("file") MultipartFile file)
	{
		if(result.hasErrors())
		{
			receta.setId(id);
			return "update_recipe";
		}
		if(!file.isEmpty())
		{
			picService.deletePicture(receta.getFoto());
			UUID idPic = UUID.randomUUID();
			picService.uploadPicture(file, idPic);
			receta.setFoto(idPic);
		}
		repo.save(receta);
		return "redirect:/recetas/list";
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@GetMapping("/delete/{id}")
	public String deleteRecipe(@PathVariable("id") Long id, Model model)
	{
		Receta receta= repo.findById(id).orElseThrow(() ->new IllegalAnnotationsException("Invalid recipe Id:"+id));
		repo.delete(receta);
		picService.deletePicture(receta.getFoto());
		model.addAttribute("recipes",repo.findAll());
		return "list_recipes";
	}
	}
		
	
	
}
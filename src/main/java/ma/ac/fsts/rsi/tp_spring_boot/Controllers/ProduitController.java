package ma.ac.fsts.rsi.tp_spring_boot.Controllers;


import ma.ac.fsts.rsi.tp_spring_boot.Entities.Produit;
import ma.ac.fsts.rsi.tp_spring_boot.Repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class ProduitController {

    @Autowired
    ProduitRepository repository;

    @GetMapping("/produits")
    //@ResponseBody
    public String getAllProduits(Model model) {
        List<Produit> produits = repository.findAll();
        model.addAttribute("produits", produits);
        return "produits";

    }

    @PostMapping("/produits")
    public String saveOrUpdateProduit(Produit produit) {
        repository.save(produit);
        return "redirect:/produits";
    }

    @GetMapping("/produits/{id}")
    public String deleteById(Model model, @PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/produits";
    }
}

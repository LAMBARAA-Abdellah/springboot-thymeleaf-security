package org.example.secureproductmanager.web;

import jakarta.validation.Valid;
import org.example.secureproductmanager.entities.Product;
import org.example.secureproductmanager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/user/index")
    public String index(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("productList", products);
        return "products";
    }
    @GetMapping("/")
    public String home() {
        return "redirect:/user/index";
    }

    @GetMapping("/admin/create")
    //@PreAuthorize("hasRole('ADMIN')")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("formTitle", "➕ Ajouter un nouveau produit");
        model.addAttribute("formAction", "/admin/save");
        return "product-form";
    }

    @PostMapping("/admin/save")
    public String save(@Valid @ModelAttribute Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("formTitle", "Ajouter un produit");
            model.addAttribute("formAction", "/admin/save");
            return "product-form";
        }
        productRepository.save(product);
        return "redirect:/";
    }

    @GetMapping("/admin/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        model.addAttribute("product", product);
        model.addAttribute("formTitle", "✏️ Modifier le produit");
        model.addAttribute("formAction", "/admin/save");
        return "product-form";
    }

    @GetMapping("/admin/delete/{id}")
    public String delete(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/user/index";
    }
}
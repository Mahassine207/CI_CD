package ma.ac.fsts.rsi.tp_spring_boot.Controllers;

import ma.ac.fsts.rsi.tp_spring_boot.Entities.Produit;
import ma.ac.fsts.rsi.tp_spring_boot.Repositories.ProduitRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProduitController.class)
class ProduitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProduitRepository repository;

    @Test
    void testGetAllProduits() throws Exception {
        List<Produit> produits = List.of(
                new Produit("Produit1", 10, 50.0),
                new Produit("Produit2", 5, 30.0));
        Mockito.when(repository.findAll()).thenReturn(produits);

        mockMvc.perform(get("/produits"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("produits"))
                .andExpect(view().name("produits"));
    }

    @Test
    void testSaveOrUpdateProduit() throws Exception {
        Produit p = new Produit("Produit3", 8, 20.0);
        Mockito.when(repository.save(Mockito.any(Produit.class))).thenReturn(p);

        mockMvc.perform(post("/produits")
                .param("designation", "Produit3")
                .param("quantite", "8")
                .param("prix", "20.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/produits"));
    }

    @Test
    void testDeleteById() throws Exception {
        long idToDelete = 1L;

        Mockito.doNothing().when(repository).deleteById(idToDelete);

        mockMvc.perform(get("/produits/{id}", idToDelete))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/produits"));
    }
}

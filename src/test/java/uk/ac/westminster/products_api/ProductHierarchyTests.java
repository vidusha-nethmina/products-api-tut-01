package uk.ac.westminster.products_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ProductHierarchyTests {
    @Autowired private WebApplicationContext context;
    @Autowired private ProductService service;

    @Test
    void mixedProductsRoundTripThroughExistingEndpoints() throws Exception {
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(context).build();
        mvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Clean Code (E-book)"))
                .andExpect(jsonPath("$[0].price").value(29.99))
                .andExpect(jsonPath("$[0].downloadUrl").value("/downloads/clean-code.pdf"))
                .andExpect(jsonPath("$[0].fileSizeMB").value(4.2))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].weightKg").value(5.4))
                .andExpect(jsonPath("$[1].dimensions").value("61cm x 41cm x 18cm"));

        mvc.perform(post("/products").contentType("application/json").content("""
                {"name":"Java Guide","price":12.5,"downloadUrl":"/downloads/java.pdf","fileSizeMB":2.5}
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.downloadUrl").value("/downloads/java.pdf"));
        assertInstanceOf(DigitalProduct.class, service.getProductById(3L).orElseThrow());
        mvc.perform(get("/products/3"))
                .andExpect(jsonPath("$.name").value("Java Guide"))
                .andExpect(jsonPath("$.fileSizeMB").value(2.5));
        mvc.perform(get("/products"))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[2].downloadUrl").value("/downloads/java.pdf"));
        mvc.perform(get("/products/search").param("name", "Java"))
                .andExpect(jsonPath("$[0].id").value(3));

        mvc.perform(post("/products").contentType("application/json").content("""
                {"name":"Keyboard","price":45,"weightKg":0.8,"dimensions":"40 x 15 x 3 cm"}
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.weightKg").value(0.8));
        assertInstanceOf(PhysicalProduct.class, service.getProductById(4L).orElseThrow());
        mvc.perform(post("/products").contentType("application/json").content("""
                {"name":"Generic item","price":10}
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5));
        assertEquals(Product.class, service.getProductById(5L).orElseThrow().getClass());
        mvc.perform(delete("/products/3")).andExpect(content().string("true"));
        assertTrue(service.getProductById(3L).isEmpty());
        mvc.perform(delete("/products/3")).andExpect(content().string("false"));
    }

    @Test
    void constructorsInitializeInheritedAndSpecializedState() {
        DigitalProduct digital = new DigitalProduct(10L, "Book", 20, "/book.pdf", 3);
        PhysicalProduct physical = new PhysicalProduct(11L, "Screen", 200, 5, "60 x 40 cm");
        assertEquals(10L, digital.getId());
        assertEquals("Book", digital.getName());
        assertEquals(20, digital.getPrice());
        assertEquals(3, digital.getFileSizeMB());
        assertEquals(11L, physical.getId());
        assertEquals("Screen", physical.getName());
        assertEquals(200, physical.getPrice());
        assertEquals(5, physical.getWeightKg());
        assertEquals("60 x 40 cm", physical.getDimensions());
    }
}

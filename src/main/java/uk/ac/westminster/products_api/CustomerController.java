package uk.ac.westminster.products_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @GetMapping("/{id}")
    public Customer getById(@PathVariable Long id) {
        Address address = new Address("seeduwa", "muklangamuwa", "11410");

        return new Customer(id,"vidusha","vidusha2gmail.com",address);

    }


    }

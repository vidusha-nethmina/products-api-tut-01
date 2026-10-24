package uk.ac.westminster.products_api;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

// Infer the subtype from its unique JSON fields; ordinary Product requests still work.
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, defaultImpl = Product.class)
@JsonSubTypes({
    @JsonSubTypes.Type(DigitalProduct.class),
    @JsonSubTypes.Type(PhysicalProduct.class)
})
public class Product {
    private Long id;
    private String name;
    private double price;

    public Product(){

    }

    public Product(Long id,String name,double price){
        this.id=id;
        this.name=name;
        this.price=price;
    }
    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
   }
    public double getPrice(){
        return price;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}

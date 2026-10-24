package uk.ac.westminster.products_api;

// A PhysicalProduct IS-A Product because it is a sellable item that can be shipped.
// A ShoppingCart HAS-A collection of products; it is not itself a Product.
public class PhysicalProduct extends Product {
    private double weightKg;
    private String dimensions;

    // Required for JSON deserialization.
    public PhysicalProduct() {
    }

    public PhysicalProduct(Long id, String name, double price, double weightKg, String dimensions) {
        super(id, name, price);
        this.weightKg = weightKg;
        this.dimensions = dimensions;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(double weightKg) {
        this.weightKg = weightKg;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }
}

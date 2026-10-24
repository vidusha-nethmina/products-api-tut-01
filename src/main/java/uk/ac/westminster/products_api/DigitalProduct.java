package uk.ac.westminster.products_api;

// A DigitalProduct IS-A Product because it is a sellable item delivered by download.
public class DigitalProduct extends Product {
    private String downloadUrl;
    private double fileSizeMB;

    // Required for JSON deserialization.
    public DigitalProduct() {
    }

    public DigitalProduct(Long id, String name, double price, String downloadUrl, double fileSizeMB) {
        super(id, name, price);
        this.downloadUrl = downloadUrl;
        this.fileSizeMB = fileSizeMB;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public double getFileSizeMB() {
        return fileSizeMB;
    }

    public void setFileSizeMB(double fileSizeMB) {
        this.fileSizeMB = fileSizeMB;
    }
}

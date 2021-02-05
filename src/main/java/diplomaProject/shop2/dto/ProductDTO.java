package diplomaProject.shop2.dto;

import java.math.BigDecimal;

public class ProductDTO {
    private Long id;

    //название товара
    private String productName;

    //описание товара
    //стандарный String ограничен в 255 charts
    private String productDescription;

    //цена товара
    private BigDecimal price;

    public ProductDTO(){}

    public ProductDTO (Long id, String productName, String productDescription, BigDecimal price) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getProductName () {
        return productName;
    }

    public void setProductName (String productName) {
        this.productName = productName;
    }

    public String getProductDescription () {
        return productDescription;
    }

    public void setProductDescription (String productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getPrice () {
        return price;
    }

    public void setPrice (BigDecimal price) {
        this.price = price;
    }
}

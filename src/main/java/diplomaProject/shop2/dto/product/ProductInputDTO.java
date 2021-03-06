package diplomaProject.shop2.dto.product;

import diplomaProject.shop2.model.Product;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductInputDTO {
    private Long id;

    //название товара
    private String productName;

    //описание товара
    //стандарный String ограничен в 255 charts
    private String productDescription;

    //цена товара
    private BigDecimal price;

    public ProductInputDTO (){}

    public ProductInputDTO (Long id, String productName, String productDescription, BigDecimal price) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
    }

    public static Product toProduct (ProductInputDTO product) {
        return new Product (
                product.getId (),
                product.getProductName (),
                product.getProductDescription (),
                product.getPrice ()
        );
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


    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null) return false;
        ProductInputDTO that = (ProductInputDTO) o;
        return Objects.equals (id, that.id) &&
                Objects.equals (productName, that.productName) &&
                Objects.equals (productDescription, that.productDescription) &&
                Objects.equals (price, that.price);
    }

    @Override
    public int hashCode () {
        return Objects.hash (id, productName, productDescription, price);
    }
}

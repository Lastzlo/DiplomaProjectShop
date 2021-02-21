package diplomaProject.shop2.dto.product;

import diplomaProject.shop2.model.Photo;
import diplomaProject.shop2.model.Product;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ProductOutputDTO {
    private Long id;

    //название товара
    private String productName;

    //описание товара
    //стандарный String ограничен в 255 charts
    private String productDescription;

    //цена товара
    private BigDecimal price;

    //список фото
    private Set<Photo> photos = new HashSet<> ();

    public ProductOutputDTO (){}

    public ProductOutputDTO (Long id, String productName, String productDescription, BigDecimal price, Set<Photo> photos) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.photos = photos;
    }

    public static ProductOutputDTO fromProduct (Product product) {
        return new ProductOutputDTO (
                product.getId (),
                product.getProductName (),
                product.getProductDescription (),
                product.getPrice (),
                product.getPhotos ()
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

    public Set<Photo> getPhotos () {
        return photos;
    }

    public void setPhotos (Set<Photo> photos) {
        this.photos = photos;
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null) return false;

        ProductOutputDTO that = (ProductOutputDTO) o;

        if (id != null ? !id.equals (that.id) : that.id != null) return false;
        if (productName != null ? !productName.equals (that.productName) : that.productName != null) return false;
        if (productDescription != null ? !productDescription.equals (that.productDescription) : that.productDescription != null)
            return false;
        if (price != null ? !price.equals (that.price) : that.price != null) return false;
        return photos != null ? photos.equals (that.photos) : that.photos == null;
    }

    @Override
    public int hashCode () {
        int result = id != null ? id.hashCode () : 0;
        result = 31 * result + (productName != null ? productName.hashCode () : 0);
        result = 31 * result + (productDescription != null ? productDescription.hashCode () : 0);
        result = 31 * result + (price != null ? price.hashCode () : 0);
        result = 31 * result + (photos != null ? photos.hashCode () : 0);
        return result;
    }
}

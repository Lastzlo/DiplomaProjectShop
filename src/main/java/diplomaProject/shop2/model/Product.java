package diplomaProject.shop2.model;

import diplomaProject.shop2.dto.ProductDTO;
import diplomaProject.shop2.dto.product.ProductOutputDTO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //название товара
    private String productName;

    //описание товара
    //стандарный String ограничен в 255 charts
    private String productDescription;

    //цена товара
    private BigDecimal price;

    //список фотографий
    @OneToMany
    private Set<Photo> photos;

    public Product(){}

    public Product (Long id, String productName, String productDescription, BigDecimal price) {
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
    }

    public static ProductDTO toDTO (Product productFromDB) {
        return new ProductDTO (
                productFromDB.getId (),
                productFromDB.getProductName (),
                productFromDB.getProductDescription (),
                productFromDB.getPrice ()
        );
    }

    public static ProductOutputDTO toOutputDTO (Product productFromDB) {
        return new ProductOutputDTO (
                productFromDB.getId (),
                productFromDB.getProductName (),
                productFromDB.getProductDescription (),
                productFromDB.getPrice ()
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

    public void addPhoto (Photo photo) {
        this.photos.add (photo);
    }

    public void deletePhoto(Photo photo){
        this.photos.remove (photo);
    }
}

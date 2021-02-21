package diplomaProject.shop2.dto.product;

import java.util.Optional;

public class ProductResultDTO {
    protected String message = "";

    protected Optional<ProductOutputDTO> product = Optional.empty ();

    public ProductResultDTO (String message) {
        this.message = message;
    }

    public ProductResultDTO (String message, Optional<ProductOutputDTO> product) {
        this.message = message;
        this.product = product;
    }

    public boolean isSuccess(){
        return product.isPresent ();
    }

    public String getMessage () {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    public Optional<ProductOutputDTO> getProduct () {
        return product;
    }

    public void setProduct (Optional<ProductOutputDTO> product) {
        this.product = product;
    }
}

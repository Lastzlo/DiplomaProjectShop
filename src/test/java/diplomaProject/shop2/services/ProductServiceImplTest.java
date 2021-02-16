package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.product.ProductInputDTO;
import diplomaProject.shop2.dto.product.ProductOutputDTO;
import diplomaProject.shop2.model.Product;
import diplomaProject.shop2.repos.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void saveProduct_whenProductHaveProductNameAndProductDescriptionAndPrice () {
        //given
        final ProductInputDTO productInputDTO = new ProductInputDTO (){{
            this.setPrice (new BigDecimal (1000));
            this.setProductDescription ("ProductDescription");
            this.setProductName ("ProductName");
        }};

        final Product productFromDB = new Product (){{
            this.setId (10l);
            this.setPrice (new BigDecimal (1000));
            this.setProductDescription ("ProductDescription");
            this.setProductName ("ProductName");
        }};

        Mockito.when (productRepository.save (
                ArgumentMatchers.any (Product.class)
        ))
                .thenReturn (productFromDB);

        final ProductOutputDTO expectedProductDTO =  Product.toOutputDTO (productFromDB);


        //when
        ProductOutputDTO actualProductDTO = productService.saveProduct (productInputDTO);

        //then
        Assertions.assertEquals (expectedProductDTO,actualProductDTO);

    }

    @Test
    void getProducts_whenStoredInDatabase3Item () {
        //give
        final List<Product> productsFromDB = new LinkedList<Product> (){{
            add (new Product (){{
                     this.setId (10l);
                     this.setPrice (new BigDecimal (1000));
                     this.setProductDescription ("ProductDescription");
                     this.setProductName ("ProductName");
                 }}
            );
            add (new Product (){{
                     this.setId (10l);
                     this.setPrice (new BigDecimal (1000));
                     this.setProductDescription ("ProductDescription");
                     this.setProductName ("ProductName");
                 }}
            );
            add (new Product (){{
                     this.setId (10l);
                     this.setPrice (new BigDecimal (1000));
                     this.setProductDescription ("ProductDescription");
                     this.setProductName ("ProductName");
                 }}
            );
        }};

        Mockito.when (productRepository.findAll ())
                .thenReturn (productsFromDB);


        //when
        final List<ProductOutputDTO> actualProducts = productService.getProducts ();

        //then
        Assertions.assertFalse (actualProducts.isEmpty ());
        Assertions.assertEquals (3, actualProducts.size ());

        Mockito.verify (productRepository, Mockito.times (1)).findAll ();
    }

    @Test
    void deleteProductById_whenProductFound(){
        //give
        final Product product = new Product (){{
            this.setId (10l);
            this.setPrice (new BigDecimal (1000));
            this.setProductDescription ("ProductDescription");
            this.setProductName ("ProductName");
        }};

        Mockito.when (productRepository.findById (product.getId ()))
                .thenReturn (Optional.of (product));

        //when
        final String productId = "10";

        final boolean result = productService.deleteProductById (productId);

        //then
        Assertions.assertTrue (result);
    }

    @Test
    void deleteProductById_whenProductNotFound(){
        //give

        Mockito.when (productRepository.findById (ArgumentMatchers.anyLong ()))
                .thenReturn (Optional.empty ());

        //when
        final String productId = "10";

        final boolean result = productService.deleteProductById (productId);

        //then
        Assertions.assertFalse (result);
    }

}
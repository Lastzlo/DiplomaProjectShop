package diplomaProject.shop2.controllers;

import diplomaProject.shop2.dto.ProductDTO;
import diplomaProject.shop2.model.Product;
import diplomaProject.shop2.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class ProductControllerImplTest {

    @Autowired
    private ProductControllerImpl productController;

    @MockBean
    private ProductService productService;

    @Test
    void addProduct_whenProductHaveProductNameAndProductDescriptionAndPrice () {
        //given
        final ProductDTO product = new ProductDTO (){{
            this.setPrice (new BigDecimal (1000));
            this.setProductDescription ("ProductDescription");
            this.setProductName ("ProductName");
        }};

        final ProductDTO productDTO = new ProductDTO (){{
            this.setId (10l);
            this.setPrice (new BigDecimal (1000));
            this.setProductDescription ("ProductDescription");
            this.setProductName ("ProductName");
        }};

        Mockito.when (productService.saveProduct (ArgumentMatchers.any (ProductDTO.class)))
                .thenReturn (productDTO);

        //when
        ResponseEntity<ProductDTO> productDTOResponseEntity = productController.addProduct (product);

        //then
        Assertions.assertTrue (productDTOResponseEntity.getStatusCode ().is2xxSuccessful ());
        Assertions.assertEquals (productDTO, productDTOResponseEntity.getBody ());
    }

    @Test
    void products_whenStoredInDatabase3Item () {
        //give
        final List<ProductDTO> productDTOS = new LinkedList<ProductDTO> (){{
            add (new ProductDTO (){{
                     this.setId (10l);
                     this.setPrice (new BigDecimal (1000));
                     this.setProductDescription ("ProductDescription");
                     this.setProductName ("ProductName");
                 }}
            );
            add (new ProductDTO (){{
                     this.setId (10l);
                     this.setPrice (new BigDecimal (1000));
                     this.setProductDescription ("ProductDescription");
                     this.setProductName ("ProductName");
                 }}
            );
            add (new ProductDTO (){{
                     this.setId (10l);
                     this.setPrice (new BigDecimal (1000));
                     this.setProductDescription ("ProductDescription");
                     this.setProductName ("ProductName");
                 }}
            );
        }};

        Mockito.when (productService.getProducts ())
                .thenReturn (productDTOS);


        //when
        final ResponseEntity<List<ProductDTO>> actualProducts = productController.products ();


        //then
        Assertions.assertTrue (actualProducts.getStatusCode ().is2xxSuccessful ());
        Assertions.assertEquals (productDTOS, actualProducts.getBody ());
    }

    @Test
    void deleteProduct_whenProductWasRemoved(){
        //given
        final String productId = "10";

        Mockito.when (productService.deleteProductById (productId))
                .thenReturn (true);

        //when
        final ResponseEntity responseEntity = productController.deleteProduct (productId);

        //then
        Assertions.assertTrue (responseEntity.getStatusCode ().is2xxSuccessful ());
    }

    @Test
    void deleteProduct_whenProductWasNotRemoved(){
        //given
        final String productId = "10";

        Mockito.when (productService.deleteProductById (productId))
                .thenReturn (false);

        //when
        final ResponseEntity responseEntity = productController.deleteProduct (productId);

        //then
        Assertions.assertTrue (responseEntity.getStatusCode ().is4xxClientError ());
    }
}
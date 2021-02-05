package diplomaProject.shop2.controllers;

import diplomaProject.shop2.dto.ProductDTO;
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
}
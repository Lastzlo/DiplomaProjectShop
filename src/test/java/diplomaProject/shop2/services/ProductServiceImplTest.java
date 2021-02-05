package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.ProductDTO;
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

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void saveProduct_whenProductHaveProductNameAndProductDescriptionAndPrice () {
        //given
        final ProductDTO product = new ProductDTO (){{
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

        final ProductDTO expectedProductDTO =  Product.toDTO(productFromDB);


        //when
        ProductDTO actualProductDTO = productService.saveProduct (product);

        //then
        Assertions.assertEquals (expectedProductDTO,actualProductDTO);

    }
}
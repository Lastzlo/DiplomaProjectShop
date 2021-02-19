package diplomaProject.shop2.controllers;

import diplomaProject.shop2.dto.product.ProductInputDTO;
import diplomaProject.shop2.dto.product.ProductOutputDTO;
import diplomaProject.shop2.dto.results.SuccessResult;
import diplomaProject.shop2.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerImplTest {

    @Autowired
    private ProductControllerImpl productController;

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addProduct_whenProductHaveProductNameAndProductDescriptionAndPrice () {
        //given
        final ProductInputDTO productInputDTO = new ProductInputDTO (){{
            this.setPrice (new BigDecimal (1000));
            this.setProductDescription ("ProductDescription");
            this.setProductName ("ProductName");
        }};

        final ProductOutputDTO productOutputDTO = new ProductOutputDTO (){{
            this.setId (10l);
            this.setPrice (new BigDecimal (1000));
            this.setProductDescription ("ProductDescription");
            this.setProductName ("ProductName");
        }};

        when (productService.saveProduct (ArgumentMatchers.any (ProductInputDTO.class)))
                .thenReturn (productOutputDTO);

        //when
        ResponseEntity<ProductOutputDTO> productDTOResponseEntity = productController.addProduct (productInputDTO);

        //then
        Assertions.assertTrue (productDTOResponseEntity.getStatusCode ().is2xxSuccessful ());
        Assertions.assertEquals (productOutputDTO, productDTOResponseEntity.getBody ());
    }

    @Test
    void products_whenStoredInDatabase3Item () {
        //give
        final List<ProductOutputDTO> productDTOS = new LinkedList<ProductOutputDTO> (){{
            add (new ProductOutputDTO (){{
                     this.setId (10l);
                     this.setPrice (new BigDecimal (1000));
                     this.setProductDescription ("ProductDescription");
                     this.setProductName ("ProductName");
                 }}
            );
            add (new ProductOutputDTO (){{
                     this.setId (10l);
                     this.setPrice (new BigDecimal (1000));
                     this.setProductDescription ("ProductDescription");
                     this.setProductName ("ProductName");
                 }}
            );
            add (new ProductOutputDTO (){{
                     this.setId (10l);
                     this.setPrice (new BigDecimal (1000));
                     this.setProductDescription ("ProductDescription");
                     this.setProductName ("ProductName");
                 }}
            );
        }};

        when (productService.getProducts ())
                .thenReturn (productDTOS);


        //when
        final ResponseEntity<List<ProductOutputDTO>> actualProducts = productController.products ();


        //then
        Assertions.assertTrue (actualProducts.getStatusCode ().is2xxSuccessful ());
        Assertions.assertEquals (productDTOS, actualProducts.getBody ());
    }

    @Test
    void deleteProduct_whenProductWasRemoved(){
        //given
        final String productId = "10";

        when (productService.deleteProductById (productId))
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

        when (productService.deleteProductById (productId))
                .thenReturn (false);

        //when
        final ResponseEntity responseEntity = productController.deleteProduct (productId);

        //then
        Assertions.assertTrue (responseEntity.getStatusCode ().is4xxClientError ());
    }


    @Test
    void addPhotoToProduct_thenCorrect () throws Exception {
        // given
        final String urlTemplate = "/product/addPhotoToProduct/{productId}";
        // and
        final Long productId = 10L;
        // and
        final MockMultipartFile multipartFile = new MockMultipartFile (
                "file",
                "fileThatDoesNotExists.txt",
                "text/plain",
                "This is a dummy file content".getBytes(StandardCharsets.UTF_8));

        // Setup our mocked service
        doReturn (new ResponseEntity<> (new SuccessResult (), HttpStatus.OK))
                .when (productService)
                .addPhotoToProduct (
                        ArgumentMatchers.any (MultipartFile.class), ArgumentMatchers.anyLong ()
                );


        // Execute the POST request
        mockMvc.perform (
                multipart(urlTemplate,productId)
                .file (multipartFile))

                // Validate the response code and content type
                .andExpect (status().isOk ())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.description", is("OK")));

    }
}
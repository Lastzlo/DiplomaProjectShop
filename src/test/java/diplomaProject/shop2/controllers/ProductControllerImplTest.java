package diplomaProject.shop2.controllers;

import diplomaProject.shop2.dto.product.ProductInputDTO;
import diplomaProject.shop2.dto.product.ProductOutputDTO;
import diplomaProject.shop2.dto.product.ProductResultDTO;
import diplomaProject.shop2.dto.results.BadResult;
import diplomaProject.shop2.dto.results.ResultDTO;
import diplomaProject.shop2.dto.results.SuccessResult;
import diplomaProject.shop2.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
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

//    @Test
//    void deleteProduct_whenProductWasRemoved(){
//        //given
//        final String productId = "10";
//
//        when (productService.deleteProductById (productId))
//                .thenReturn (true);
//
//        //when
//        final ResponseEntity responseEntity = productController.deleteProduct (productId);
//
//        //then
//        Assertions.assertTrue (responseEntity.getStatusCode ().is2xxSuccessful ());
//    }

//    @Test
//    void deleteProduct_whenProductWasNotRemoved(){
//        //given
//        final String productId = "10";
//
//        when (productService.deleteProductById (productId))
//                .thenReturn (false);
//
//        //when
//        final ResponseEntity responseEntity = productController.deleteProduct (productId);
//
//        //then
//        Assertions.assertTrue (responseEntity.getStatusCode ().is4xxClientError ());
//    }


    @Test
    void deleteProduct_thenOK () throws Exception {
        // given
        String urlTemplate = "/product/delete/{productId}";
        Long productId = 10L;

        // Setup our mocked service
        when (productService.deleteProductById (
                ArgumentMatchers.eq (productId)
        )).thenReturn (new SuccessResult ());

        // Execute the POST request
        mockMvc.perform (
                post (urlTemplate, productId))
                // Print
                .andDo (print ())

                // Validate the response code and content type
                .andExpect (status ().isOk ())
                .andExpect (content ().contentType (MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect (jsonPath ("$.message", is ("OK")));
    }

    @Test
    void deleteProduct_thenBadRequest () throws Exception {
        // given
        String urlTemplate = "/product/delete/{productId}";
        Long productId = 10L;
        String message = "what's the problem";

        // Setup our mocked service
        when (productService.deleteProductById (
                ArgumentMatchers.eq (productId)
        )).thenReturn (
                new BadResult (message)
        );

        // Execute the POST request
        mockMvc.perform (
                post (urlTemplate, productId))
                // Print
                .andDo (print ())

                // Validate the response code and content type
                .andExpect (status ().isBadRequest ())
                .andExpect (content ().contentType (MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect (jsonPath ("$.message", is ("what's the problem")));
    }

    @Test
    void addPhotoToProduct_thenOK () throws Exception {
        // given
        String urlTemplate = "/product/addPhotoToProduct/{productId}";
        Long productId = 10L;
        MockMultipartFile multipartFile = mock (MockMultipartFile.class);
        when (multipartFile.getName ()).thenReturn ("file");
        ProductOutputDTO productOutputDTO = new ProductOutputDTO ();

        String message = "message";

        // Setup our mocked service
        when (productService.addPhotoToProduct (
                ArgumentMatchers.any (MultipartFile.class),
                ArgumentMatchers.eq (productId)
        )).thenReturn (
                new ProductResultDTO (message, Optional.of (productOutputDTO))
        );

        // Execute the POST request
        mockMvc.perform (
                multipart (urlTemplate, productId)
                        .file (multipartFile))
                // Print
                .andDo (print ())

                // Validate the response code and content type
                .andExpect (status ().isOk ())
                .andExpect (content ().contentType (MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect (jsonPath ("$.message", is ("message")))
                .andExpect (jsonPath ("$.product.id", is (nullValue ())))
                .andExpect (jsonPath ("$.product.productName", is (nullValue ())))
                .andExpect (jsonPath ("$.product.photos", hasSize (0)))
                .andExpect (jsonPath ("$.success", is (true)));
    }

    @Test
    void addPhotoToProduct_thenBadRequest () throws Exception {
        // given
        String urlTemplate = "/product/addPhotoToProduct/{productId}";
        Long productId = 10L;
        MockMultipartFile multipartFile = mock (MockMultipartFile.class);
        when (multipartFile.getName ()).thenReturn ("file");

        String message = "what's the problem";

        // Setup our mocked service
        when (productService.addPhotoToProduct (
                ArgumentMatchers.any (MultipartFile.class),
                ArgumentMatchers.eq (productId)
        )).thenReturn (
                new ProductResultDTO (message)
        );

        // Execute the POST request
        mockMvc.perform (
                multipart (urlTemplate, productId)
                        .file (multipartFile))
                // Print
                .andDo (print ())

                // Validate the response code and content type
                .andExpect (status ().isBadRequest ())
                .andExpect (content ().contentType (MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect (jsonPath ("$.message", is ("what's the problem")))
                .andExpect (jsonPath ("$.product", is (nullValue ())))
                .andExpect (jsonPath ("$.success", is (false)));
    }

    @Test
    void deletePhotoFromProduct_thenOK () throws Exception {
        // given
        String urlTemplate = "/product/deletePhotoFromProduct";
        String productId = "5";
        String photoId = "10";

        SuccessResult successResult = new SuccessResult ();

        // Setup our mocked service
        when (productService.deletePhotoFromProduct (
                ArgumentMatchers.eq (Long.valueOf (photoId)),
                ArgumentMatchers.eq (Long.valueOf (productId))
        )).thenReturn (successResult);

        // Execute the POST request
        mockMvc.perform (
                post (urlTemplate)
                        .param ("photoId", photoId)
                        .param ("productId", productId))

                // Print
                .andDo (print ())

                // Validate the response code and content type
                .andExpect (status ().isOk ())
                .andExpect (content ().contentType (MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect (jsonPath ("$.message", is ("OK")));
    }

    @Test
    void deletePhotoFromProduct_thenBadRequest () throws Exception {
        // given
        String urlTemplate = "/product/deletePhotoFromProduct";
        String productId = "5";
        String photoId = "10";

        ResultDTO badResult = new BadResult (
                "Problem with delete photo from AWS");

        // Setup our mocked service
        when (productService.deletePhotoFromProduct (
                ArgumentMatchers.eq (Long.valueOf (photoId)),
                ArgumentMatchers.eq (Long.valueOf (productId))
        )).thenReturn (badResult);

        // Execute the POST request
        mockMvc.perform (
                post (urlTemplate)
                        .param ("photoId", photoId)
                        .param ("productId", productId))

                // Print
                .andDo (print ())

                // Validate the response code and content type
                .andExpect (status ().isBadRequest ())
                .andExpect (content ().contentType (MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect (jsonPath ("$.message", is ("Problem with delete photo from AWS")));
    }

}
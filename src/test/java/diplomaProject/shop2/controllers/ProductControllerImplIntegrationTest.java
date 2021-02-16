package diplomaProject.shop2.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import diplomaProject.shop2.dto.ProductDTO;
import diplomaProject.shop2.dto.product.ProductInputDTO;
import diplomaProject.shop2.dto.product.ProductOutputDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//Спринг пытаеться автоматически создать структуру
//классов поторая подменяет слой MVC
//все будет проходить в фейковом окружении
@AutoConfigureMockMvc
//аннотация указывает на новый файл с настройками
@TestPropertySource("/application-test.properties")
class ProductControllerImplIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @Sql(value = {"/forProductController/create-product-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/forProductController/create-product-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addProduct_whenProductHaveProductNameAndProductDescriptionAndPrice () throws Exception {
        //given
        final ProductInputDTO product = new ProductInputDTO (){{
            this.setPrice (new BigDecimal (1000));
            this.setProductDescription ("ProductDescription");
            this.setProductName ("ProductName");
        }};

        String productToString = objectMapper.writeValueAsString (product);

        //when
        String resultJson= this.mockMvc.perform(MockMvcRequestBuilders
                .post ("/product/add")
                .contentType (MediaType.APPLICATION_JSON)
                .content (productToString))

                //Print result
                .andDo (print ())

                //Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andReturn ()
                .getResponse ()
                .getContentAsString ();

        final ProductOutputDTO actualProduct = objectMapper.readValue(resultJson, ProductOutputDTO.class);

        final ProductOutputDTO expectedProduct = new ProductOutputDTO (){{
            this.setId (10l);
            this.setPrice (new BigDecimal (1000));
            this.setProductDescription ("ProductDescription");
            this.setProductName ("ProductName");
        }};

        //then
        Assertions.assertEquals (expectedProduct,actualProduct);
    }

    @Test
    @Sql(value = {"/forProductController/products-product-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/forProductController/products-product-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void products_whenStoredInDatabase3Item() throws Exception {
        //given

        //that productDTO should be contain in actualProductDTOS
        final ProductDTO productDTO = new ProductDTO (){{
            this.setId (10l);
            //when transmitted as a string, the same value is assigned without rounding
            //this.setPrice (new BigDecimal (700.50)); result = 700.5 it`s bad
            this.setPrice (new BigDecimal ("700.50")); //result 700.50 it`s good
            this.setProductDescription ("product_description 10");
            this.setProductName ("product_name 10");
        }};


        //when
        String resultJson = this.mockMvc.perform(MockMvcRequestBuilders.get ("/product/getAllProducts"))

                //Print result
                .andDo (print ())

                //Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andReturn ()
                .getResponse ()
                .getContentAsString ();


        final List<ProductDTO> actualProductDTOS = objectMapper.readValue(resultJson, new TypeReference<List<ProductDTO>> () { });

        //then
        Assertions.assertFalse (actualProductDTOS.isEmpty ());
        Assertions.assertEquals (2,actualProductDTOS.size ());
        Assertions.assertTrue (productDTO.equals (actualProductDTOS.get (1)), "productDTO should be equal to actualProductDTOS.get (1)");
        Assertions.assertTrue (actualProductDTOS.contains (productDTO), "actualProductDTOS should be contains productDTO");


    }

    @Test
    @Sql(value = {"/forProductController/deleteProduct-product-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/forProductController/deleteProduct-product-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteProduct_whenProductWasRemoved() throws Exception {
        //given
        final String productId = "10";

        //when
        this.mockMvc.perform(MockMvcRequestBuilders.post ("/product/delete/"+productId))

                //Print result
                .andDo (print ())

                //Validate the response code and content type
                .andExpect(status().isOk());
    }

    @Test
    @Sql(value = {"/forProductController/deleteProduct-product-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/forProductController/deleteProduct-product-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteProduct_whenProductWasNotRemoved() throws Exception {
        //given
        final String productId = "1000";

        //when
        this.mockMvc.perform(MockMvcRequestBuilders.post ("/product/delete/"+productId))

                //Print result
                .andDo (print ())

                //Validate the response code and content type
                .andExpect(status().is4xxClientError ());
    }
}
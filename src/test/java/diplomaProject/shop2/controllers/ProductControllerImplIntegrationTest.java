package diplomaProject.shop2.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import diplomaProject.shop2.dto.ProductDTO;
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
    private ProductControllerImpl productController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @Sql(value = {"/forProductController/create-product-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/forProductController/create-product-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addProduct_whenProductHaveProductNameAndProductDescriptionAndPrice () throws Exception {
        //given
        final ProductDTO product = new ProductDTO (){{
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

        final ProductDTO actualProduct = objectMapper.readValue(resultJson, ProductDTO.class);

        final ProductDTO expectedProduct = new ProductDTO (){{
            this.setId (10l);
            this.setPrice (new BigDecimal (1000));
            this.setProductDescription ("ProductDescription");
            this.setProductName ("ProductName");
        }};

        //then
        Assertions.assertEquals (expectedProduct,actualProduct);
    }
}
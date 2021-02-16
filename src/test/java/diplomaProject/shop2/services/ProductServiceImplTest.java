package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.product.ProductInputDTO;
import diplomaProject.shop2.dto.product.ProductOutputDTO;
import diplomaProject.shop2.model.Photo;
import diplomaProject.shop2.model.Product;
import diplomaProject.shop2.repos.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class ProductServiceImplTest {

    @Value ("${testPicture.location}")
    private String testPictureLocation;

    @Autowired
    private ProductService productService;

    @MockBean
    private PhotoService photoService;

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

    @Test
    void whenAddPhotoToProduct_thenTrue() throws IOException {
        //give

        //file
        File file = new File (testPictureLocation);

        //photo
        Photo photo = new Photo (){{
            this.setId (12l);
            this.setSrc (file.getPath ());
        }};
        Mockito.when (photoService.savePhoto(ArgumentMatchers.any (MultipartFile.class)))
                .thenReturn (Optional.of (photo));

        //productFromDB
        Product productFromDB = new Product (){{
            this.setId (10l);
            this.setPrice (new BigDecimal (1000));
            this.setProductDescription ("ProductDescription");
            this.setProductName ("ProductName");
            this.setPhotos (new HashSet<> ());
        }};
        Mockito.when (productRepository.findById (ArgumentMatchers.anyLong ()))
                .thenReturn (Optional.of (productFromDB));

        //multipartFile
        MultipartFile multipartFile = new MockMultipartFile (file.getName (), new FileInputStream (file));


        //when
        final boolean result = productService.addPhotoToProduct(multipartFile,productFromDB.getId ());


        //then
        Assertions.assertTrue (result);
        Assertions.assertTrue (productFromDB.getPhotos().contains(photo));
    }


}
package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.photo.PhotoResultDTO;
import diplomaProject.shop2.dto.product.ProductInputDTO;
import diplomaProject.shop2.dto.product.ProductOutputDTO;
import diplomaProject.shop2.dto.results.ResultDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

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
    void addPhotoToProduct_whenProductNotFound_thenBadRequest(){
        // given
        final Long productId = 10L;
        // and
        final MockMultipartFile multipartFile = new MockMultipartFile (
                "file",
                "fileThatDoesNotExists.txt",
                "text/plain",
                "This is a dummy file content".getBytes(StandardCharsets.UTF_8));

        // Setup our mocked service
        doReturn (Optional.empty ())
                .when (productRepository)
                .findById (ArgumentMatchers.anyLong ());

        // Execute the service call
        final ResponseEntity<ResultDTO> resultDTO = productService.addPhotoToProduct (multipartFile, productId);

        // Assert the response
        Assertions.assertEquals (HttpStatus.BAD_REQUEST, resultDTO.getStatusCode ());
        Assertions.assertFalse (resultDTO.getBody ().getDescription ().isEmpty ());
    }

    @Test
    void addPhotoToProduct_whenFailedToSavePhoto_thenBadRequest (){
        // given
        final Long productId = 10L;
        // and
        final MockMultipartFile multipartFile = new MockMultipartFile (
                "file",
                "fileThatDoesNotExists.txt",
                "text/plain",
                "This is a dummy file content".getBytes(StandardCharsets.UTF_8));
        // and
        final Product product = new Product ();
        doReturn (Optional.of(product))
                .when (productRepository)
                .findById (ArgumentMatchers.anyLong ());

        final PhotoResultDTO photoResultDTO = new PhotoResultDTO ();
        doReturn (photoResultDTO)
                .when (photoService)
                .savePhoto (any (MultipartFile.class));


        // Execute the service call
        final ResponseEntity<ResultDTO> resultDTO = productService.addPhotoToProduct (multipartFile, productId);

        // Assert the response
        Assertions.assertEquals (HttpStatus.BAD_REQUEST, resultDTO.getStatusCode ());
        Assertions.assertFalse (resultDTO.getBody ().getDescription ().isEmpty ());

        verify (photoService, Mockito.times (1)).savePhoto (any (MultipartFile.class));

    }


//    @Test
//    void whenAddPhotoToProduct_thenTrue() throws IOException {
//        //give
//
//        //file
//        File file = new File (testPictureLocation);
//
//        //photo
//        Photo photo = new Photo (){{
//            this.setId (12l);
//            this.setSrc (file.getPath ());
//        }};
//        Mockito.when (photoService.savePhoto(ArgumentMatchers.any (MultipartFile.class)))
//                .thenReturn (Optional.of (photo));
//
//        //productFromDB
//        Product productFromDB = new Product (){{
//            this.setId (10l);
//            this.setPrice (new BigDecimal (1000));
//            this.setProductDescription ("ProductDescription");
//            this.setProductName ("ProductName");
//            this.setPhotos (new HashSet<> ());
//        }};
//        Mockito.when (productRepository.findById (ArgumentMatchers.anyLong ()))
//                .thenReturn (Optional.of (productFromDB));
//
//        //multipartFile
//        MultipartFile multipartFile = new MockMultipartFile (file.getName (), new FileInputStream (file));
//
//
//        //when
//        final boolean result = productService.addPhotoToProduct(multipartFile,productFromDB.getId ());
//
//
//        //then
//        Assertions.assertTrue (result);
//        Assertions.assertTrue (productFromDB.getPhotos().contains(photo));
//    }


}
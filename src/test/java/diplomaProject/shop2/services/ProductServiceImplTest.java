package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.photo.PhotoResultDTO;
import diplomaProject.shop2.dto.product.ProductInputDTO;
import diplomaProject.shop2.dto.product.ProductOutputDTO;
import diplomaProject.shop2.dto.product.ProductResultDTO;
import diplomaProject.shop2.dto.results.BadResult;
import diplomaProject.shop2.dto.results.ResultDTO;
import diplomaProject.shop2.dto.results.SuccessResult;
import diplomaProject.shop2.model.Photo;
import diplomaProject.shop2.model.Product;
import diplomaProject.shop2.repos.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource("/application-test.properties")
class ProductServiceImplTest {

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

        final ProductOutputDTO expectedProductDTO =  ProductOutputDTO.fromProduct (productFromDB);


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

//    @Test
//    void deleteProductById_whenProductFound(){
//        //give
//        final Product product = new Product (){{
//            this.setId (10l);
//            this.setPrice (new BigDecimal (1000));
//            this.setProductDescription ("ProductDescription");
//            this.setProductName ("ProductName");
//        }};
//
//        Mockito.when (productRepository.findById (product.getId ()))
//                .thenReturn (Optional.of (product));
//
//        //when
//        final String productId = "10";
//
//        final boolean result = productService.deleteProductById (productId);
//
//        //then
//        Assertions.assertTrue (result);
//    }
//
//    @Test
//    void deleteProductById_whenProductNotFound(){
//        //give
//
//        Mockito.when (productRepository.findById (ArgumentMatchers.anyLong ()))
//                .thenReturn (Optional.empty ());
//
//        //when
//        final String productId = "10";
//
//        final boolean result = productService.deleteProductById (productId);
//
//        //then
//        Assertions.assertFalse (result);
//    }

    @Test
    void deleteProductById_whenProductNotFound (){
        // given
        Long productId = 10L;

        // Setup our mocked service
        when (productRepository.findById (productId))
                .thenReturn (Optional.empty ());

        // Execute the service call
        ResultDTO resultDTO = productService.deleteProductById (productId);

        // Assert the response
        Assertions.assertTrue (
                resultDTO.getMessage ().contains ("Not found product with id")
        );

        verify (productRepository).findById (productId);
    }

    @Test
    void deleteProductById_whenPhotoServiceResultNotSuccess (){
        // given
        Long productId = 10L;

        Product product = new Product ();

        // Setup our mocked service
        when (productRepository.findById (productId))
                .thenReturn (Optional.of (product));

        when (photoService.deletePhotos (ArgumentMatchers.anySet ()))
                .thenReturn (new BadResult ("sorry"));

        // Execute the service call
        ResultDTO resultDTO = productService.deleteProductById (productId);

        // Assert the response
        Assertions.assertTrue (
                resultDTO.getMessage ().contains ("sorry")
        );
        Assertions.assertFalse (resultDTO.isSuccess ());

        verify (productRepository).findById (productId);
        verify (productRepository).delete (product);
        verify (photoService).deletePhotos (ArgumentMatchers.anySet ());
    }

    @Test
    void deleteProductById_thenSuccessResult (){
        // given
        Long productId = 10L;

        Product product = new Product ();

        // Setup our mocked service
        when (productRepository.findById (productId))
                .thenReturn (Optional.of (product));

        when (photoService.deletePhotos (ArgumentMatchers.anySet ()))
                .thenReturn (new SuccessResult ());

        // Execute the service call
        ResultDTO resultDTO = productService.deleteProductById (productId);

        // Assert the response
        Assertions.assertTrue (
                resultDTO.getMessage ().contains ("delete product complete")
        );
        Assertions.assertTrue (resultDTO.isSuccess ());

        verify (productRepository).findById (productId);
        verify (productRepository).delete (product);
        verify (photoService).deletePhotos (ArgumentMatchers.anySet ());
    }

    @Test
    void addPhotoToProduct_whenProductNotFound (){
        // given
        Long productId = 10L;
        // and
        MultipartFile multipartFile = mock (MultipartFile.class);

        // Setup our mocked service
        doReturn (Optional.empty ())
                .when (productRepository)
                .findById (productId);

        // Execute the service call
        ProductResultDTO resultDTO = productService.addPhotoToProduct (multipartFile, productId);

        // Assert the response
        Assertions.assertTrue (
                resultDTO.getMessage ().contains ("Not found product with id")
        );

        verify (productRepository).findById (productId);
    }

    @Test
    void addPhotoToProduct_whenPhotoResultDTOGetPhotoNotPresent (){
        // given
        Long productId = 10L;
        Product product = new Product ();
        MultipartFile multipartFile = mock (MultipartFile.class);
        PhotoResultDTO photoResultDTO = new PhotoResultDTO ();

        doReturn (Optional.of(product))
                .when (productRepository)
                .findById (productId);

        doReturn (photoResultDTO)
                .when (photoService)
                .savePhoto (multipartFile);

        // when
        ProductResultDTO resultDTO = productService.addPhotoToProduct (multipartFile, productId);

        // then
        Assertions.assertFalse (resultDTO.getProduct ().isPresent ());
        Assertions.assertFalse (resultDTO.getMessage ().isEmpty ());
        Assertions.assertFalse (resultDTO.isSuccess ());

        verify (productRepository).findById (productId);
        verify (photoService).savePhoto (multipartFile);
    }

    @Test
    void addPhotoToProduct_thanCorrect (){
        // given
        Long productId = 10L;
        Product product = new Product ();
        MultipartFile multipartFile = mock (MultipartFile.class);
        Photo photo = new Photo ();
        PhotoResultDTO photoResultDTO = new PhotoResultDTO ("", Optional.of (photo));

        doReturn (Optional.of(product))
                .when (productRepository)
                .findById (productId);

        doReturn (photoResultDTO)
                .when (photoService)
                .savePhoto (multipartFile);

        doReturn (product)
                .when (productRepository)
                .save (any (Product.class));

        // when
        ProductResultDTO resultDTO = productService.addPhotoToProduct (multipartFile, productId);

        // then
        Assertions.assertTrue (
                resultDTO.getMessage ().equals ("Add photo to product complete")
        );
        Assertions.assertTrue (resultDTO.isSuccess ());

        verify (productRepository).findById (productId);
        verify (photoService).savePhoto (multipartFile);
    }

    @Test
    void deletePhotoFromProduct_whenProductNotFound(){
        // given
        Long photoId = 15L;
        Long productId = 10L;

        // Setup our mocked service
        doReturn (Optional.empty ())
                .when (productRepository)
                .findById (productId);

        // Execute the service call
        ResultDTO resultDTO = productService.deletePhotoFromProduct (photoId, productId);

        // Assert the response
        Assertions.assertTrue (
                resultDTO.getMessage ().contains ("Not found product with id")
        );

        verify (productRepository).findById (productId);
    }

    @Test
    void deletePhotoFromProduct_whenHasPhotoByIdInPhotosReturnFalse(){
        // given
        Long photoId = 15L;
        Long productId = 10L;
        Product product = new Product ();

        // Setup our mocked service
        doReturn (Optional.of (product))
                .when (productRepository)
                .findById (productId);

        // Execute the service call
        ResultDTO resultDTO = productService.deletePhotoFromProduct (photoId, productId);

        // Assert the response
        Assertions.assertTrue (
                resultDTO.getMessage ().contains ("Not found photo with id")
        );

        verify (productRepository).findById (productId);
    }

    @Test
    void deletePhotoFromProduct_whenPhotoServiceResultNotSuccess(){
        // given
        Long photoId = 15L;
        Long productId = 10L;
        Product product = mock (Product.class);

        // Setup our mocked service
        when (productRepository.findById (productId))
                .thenReturn (Optional.of (product));

        when (product.hasPhotoByIdInPhotos (photoId))
                .thenReturn (true);

        when (photoService.deletePhotoById (photoId))
                .thenReturn (new BadResult ("Sorry"));


        // Execute the service call
        ResultDTO resultDTO = productService.deletePhotoFromProduct (photoId, productId);

        // Assert the response
        Assertions.assertTrue (
                resultDTO.getMessage ().contains ("Sorry")
        );
        Assertions.assertFalse (resultDTO.isSuccess ());

        verify (productRepository).findById (productId);
        verify (productRepository).save (product);
        verify (photoService).deletePhotoById (photoId);
    }

    @Test
    void deletePhotoFromProduct_thenSuccessResult(){
        // given
        Long photoId = 15L;
        Long productId = 10L;
        Product product = mock (Product.class);

        // Setup our mocked service
        when (productRepository.findById (productId))
                .thenReturn (Optional.of (product));

        when (product.hasPhotoByIdInPhotos (photoId))
                .thenReturn (true);

        when (photoService.deletePhotoById (photoId))
                .thenReturn (new SuccessResult ());


        // Execute the service call
        ResultDTO resultDTO = productService.deletePhotoFromProduct (photoId, productId);

        // Assert the response
        Assertions.assertTrue (
                resultDTO.getMessage ().contains ("delete photo from product complete")
        );
        Assertions.assertTrue (resultDTO.isSuccess ());

        verify (productRepository).findById (productId);
        verify (productRepository).save (product);
        verify (photoService).deletePhotoById (photoId);
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
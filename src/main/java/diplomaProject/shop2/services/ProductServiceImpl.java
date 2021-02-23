package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.photo.PhotoResultDTO;
import diplomaProject.shop2.dto.product.ProductInputDTO;
import diplomaProject.shop2.dto.product.ProductOutputDTO;
import diplomaProject.shop2.dto.product.ProductResultDTO;
import diplomaProject.shop2.dto.results.BadResult;
import diplomaProject.shop2.dto.results.ResultDTO;
import diplomaProject.shop2.model.Photo;
import diplomaProject.shop2.model.Product;
import diplomaProject.shop2.repos.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LogManager.getLogger(PhotoService.class);

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private PhotoService photoService;

    @Override
    public ProductOutputDTO saveProduct (ProductInputDTO product) {
        final Product productFromDTO = ProductInputDTO.toProduct(product);

        final Product productFromDB = productRepository.save (productFromDTO);

        return ProductOutputDTO.fromProduct (productFromDB);
    }

    @Override
    public List<ProductOutputDTO> getProducts () {
        final List<Product> products = productRepository.findAll ();

        final List<ProductOutputDTO> productDTOS = new LinkedList<ProductOutputDTO> (){{
            products.forEach (product -> add (ProductOutputDTO.fromProduct (product)));
        }};

        return productDTOS;
    }

//    @Override
//    public boolean deleteProductById (String id) {
//
//        final long productId = Long.parseLong (id);
//
//        Optional<Product> product = productRepository.findById (productId);
//
//        if (product.isPresent ()){
//            productRepository.delete (product.get ());
//            return true;
//        } else {
//            return false;
//        }
//
//    }


    @Override
    public ResultDTO deleteProductById (Long productId) {
        final Optional<Product> optionalProduct = productRepository.findById (productId);
        if(optionalProduct.isPresent ()) {
            Product product = optionalProduct.get ();

            // copy product photos
            Set<Photo> photos = new HashSet<> (product.getPhotos ());

            productRepository.delete (product);
            logger.info ("product with id = "+productId+"was removed from the repository");

            photoService.deletePhotos(photos);

            return null;
        } else {
            final String message = "Not found product with id = "+ productId;

            logger.info (message);
            return new BadResult (message);
        }
    }

    @Override
    public ProductResultDTO addPhotoToProduct (MultipartFile multipartFile, Long id) {
        final Optional<Product> optionalProduct = productRepository.findById (id);
        if(optionalProduct.isPresent ()) {
            Product product = optionalProduct.get ();

            PhotoResultDTO photoResultDTO = photoService.savePhoto (multipartFile);
            if(photoResultDTO.isSuccess ()) {
                final Optional<Photo> photo = photoResultDTO.getPhoto ();

                product.addPhoto (photo.get ());
                final Product updateProduct = productRepository.save (product);

                final ProductOutputDTO productDTO = ProductOutputDTO.fromProduct (updateProduct);

                final String message = "Add photo to product complete";
                logger.info (message);
                return new ProductResultDTO (message, Optional.of (productDTO));
            } else {
                return new ProductResultDTO (photoResultDTO.getMessage ());
            }
        } else {
            final String message = "Not found product with id = "+ id;

            logger.info (message);
            return new ProductResultDTO (message);
        }
    }

    @Override
    public ResultDTO deletePhotoFromProduct (Long photoId, Long productId) {
        return null;
    }

//    @Override
//    public boolean addPhotoToProduct (MultipartFile multipartFile, Long productId) {
//        final Optional<Product> productFromDB = productRepository.findById (productId);
//
//        if (productFromDB.isPresent ()){
//            Product product = productFromDB.get ();
//
//            final Optional<Photo> photoFromDB = photoService.savePhoto (multipartFile);
//            if(photoFromDB.isPresent ()){
//                final Photo photo = photoFromDB.get ();
//                product.addPhoto (photo);
//
//                productRepository.save (product);
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            return false;
//        }
//    }
}

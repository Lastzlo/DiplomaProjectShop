package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.photo.PhotoResultDTO;
import diplomaProject.shop2.dto.product.ProductInputDTO;
import diplomaProject.shop2.dto.product.ProductOutputDTO;
import diplomaProject.shop2.dto.results.BadRequestResult;
import diplomaProject.shop2.dto.results.ResultDTO;
import diplomaProject.shop2.model.Product;
import diplomaProject.shop2.repos.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public boolean deleteProductById (String id) {

        final long productId = Long.parseLong (id);

        Optional<Product> product = productRepository.findById (productId);

        if (product.isPresent ()){
            productRepository.delete (product.get ());
            return true;
        } else {
            return false;
        }

    }

    @Override
    public ResponseEntity<ResultDTO> addPhotoToProduct (MultipartFile multipartFile, Long id) {
        final Optional<Product> optionalProduct = productRepository.findById (id);
        if(optionalProduct.isPresent ()){
            Product product = optionalProduct.get ();

            PhotoResultDTO photoResultDTO = photoService.savePhoto (multipartFile);
            if(photoResultDTO.isSuccessResult ()){
                return null;

            } else {
                return new ResponseEntity<> (
                        new BadRequestResult (photoResultDTO.getMessage ()),
                        HttpStatus.BAD_REQUEST
                );
            }

        } else {
            return new ResponseEntity<> (
                    new BadRequestResult (
                    "Not found product with id = "+ id),
                    HttpStatus.BAD_REQUEST
            );
        }
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

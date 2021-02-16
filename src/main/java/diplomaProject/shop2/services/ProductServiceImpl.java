package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.product.ProductInputDTO;
import diplomaProject.shop2.dto.product.ProductOutputDTO;
import diplomaProject.shop2.model.Photo;
import diplomaProject.shop2.model.Product;
import diplomaProject.shop2.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    private PhotoService photoService;

    @Override
    public ProductOutputDTO saveProduct (ProductInputDTO product) {
        final Product productFromDTO = ProductInputDTO.toProduct(product);

        final Product productFromDB = productRepository.save (productFromDTO);

        return Product.toOutputDTO (productFromDB);
    }

    @Override
    public List<ProductOutputDTO> getProducts () {
        final List<Product> products = productRepository.findAll ();

        final List<ProductOutputDTO> productDTOS = new LinkedList<ProductOutputDTO> (){{
            products.forEach (product -> add (Product.toOutputDTO (product)));
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
    public boolean addPhotoToProduct (MultipartFile multipartFile, Long productId) {
        final Optional<Product> productFromDB = productRepository.findById (productId);

        if (productFromDB.isPresent ()){
            Product product = productFromDB.get ();

            final Optional<Photo> photoFromDB = photoService.savePhoto (multipartFile);
            if(photoFromDB.isPresent ()){
                final Photo photo = photoFromDB.get ();
                product.addPhoto (photo);

                productRepository.save (product);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}

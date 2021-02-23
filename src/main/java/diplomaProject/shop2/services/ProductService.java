package diplomaProject.shop2.services;

import diplomaProject.shop2.dto.product.ProductInputDTO;
import diplomaProject.shop2.dto.product.ProductOutputDTO;
import diplomaProject.shop2.dto.product.ProductResultDTO;
import diplomaProject.shop2.dto.results.ResultDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductOutputDTO saveProduct (ProductInputDTO product);

    List<ProductOutputDTO> getProducts ();

//    boolean deleteProductById (String id);

    ResultDTO deleteProductById (Long productId);

    ProductResultDTO addPhotoToProduct (MultipartFile multipartFile, Long id);

    ResultDTO deletePhotoFromProduct (Long photoId, Long productId);
}

package diplomaProject.shop2.controllers;

import diplomaProject.shop2.dto.product.ProductInputDTO;
import diplomaProject.shop2.dto.product.ProductOutputDTO;
import diplomaProject.shop2.dto.product.ProductResultDTO;
import diplomaProject.shop2.dto.results.BadResult;
import diplomaProject.shop2.dto.results.ResultDTO;
import diplomaProject.shop2.dto.results.SuccessResult;
import diplomaProject.shop2.services.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductControllerImpl implements ProductController {
    private static final Logger logger = LogManager.getLogger(ProductControllerImpl.class);

    @Autowired
    private ProductService productService;

    @GetMapping("getAllProducts")
    public ResponseEntity<List<ProductOutputDTO>> products(){
        logger.info("ProductControllerImpl.products is executed");
        return ResponseEntity.ok()
                .body(productService.getProducts());
    }

    @PostMapping("add")
    public ResponseEntity<ProductOutputDTO> addProduct(
            @RequestBody ProductInputDTO product
    ){
        logger.info("ProductControllerImpl.addProduct is executed");
        return ResponseEntity.ok()
                .body(productService.saveProduct(product));
        //сделать чтобы возвращалось сообщение с продуктом ProductResultDTO
    }

//    @PostMapping("delete/{id}")
//    public ResponseEntity deleteProduct(
//            @PathVariable String id
//    ){
//        logger.info("ProductControllerImpl.deleteProduct is executed");
//        logger.info("deleteProduct id = "+id);
//        if(productService.deleteProductById(id)){
//            logger.info("Product with id = %1 removed from the database", id);
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.badRequest ().build ();
//        }
//    }

    @PostMapping("delete/{productId}")
    public ResponseEntity<ResultDTO> deleteProduct(@PathVariable Long productId){
        logger.info("ProductControllerImpl.deleteProduct is executed");
        logger.info("deleteProduct id = "+productId);

        ResultDTO result = productService.deleteProductById (productId);

        if(result.isSuccess ()){
            return new ResponseEntity<>(
                    new SuccessResult (result.getMessage ()),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new BadResult (result.getMessage ()),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping("addPhotoToProduct/{productId}")
    public ResponseEntity<ProductResultDTO> addPhotoToProduct(
            @RequestPart(value = "file") MultipartFile multipartFile,
            @PathVariable Long productId
    ){
        ProductResultDTO productResultDTO = productService.addPhotoToProduct(multipartFile, productId);

        if(productResultDTO.isSuccess ()){
            return new ResponseEntity<>(
                    productResultDTO,
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    productResultDTO,
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping("deletePhotoToProduct")
    public ResponseEntity<ResultDTO> deletePhotoFromProduct(
            @RequestParam (value = "photoId") Long photoId,
            @RequestParam (value = "productId") Long productId
    ){
        ResultDTO result = productService.deletePhotoFromProduct (photoId, productId);

        if(result.isSuccess ()){
            return new ResponseEntity<>(
                    new SuccessResult (result.getMessage ()),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new BadResult (result.getMessage ()),
                    HttpStatus.BAD_REQUEST
            );
        }

    }

}

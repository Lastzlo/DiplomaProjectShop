package diplomaProject.shop2.controllers;

import diplomaProject.shop2.dto.ProductDTO;
import diplomaProject.shop2.services.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductControllerImpl implements ProductController {
    private static final Logger logger = LogManager.getLogger(ProductControllerImpl.class);

    @Autowired
    private ProductService productService;



    @PostMapping("add")
    public ResponseEntity<ProductDTO> addProduct(
            @RequestBody ProductDTO product
    ){
        logger.info("ProductControllerImpl.addProduct is executed");
        return ResponseEntity.ok()
                .body(productService.saveProduct(product));
    }

}

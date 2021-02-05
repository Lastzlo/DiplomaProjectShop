package diplomaProject.shop2.controllers;

import diplomaProject.shop2.services.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductControllerImpl implements ProductController {
    private static final Logger logger = LogManager.getLogger(ProductControllerImpl.class);

    @Autowired
    private ProductService productService;

}

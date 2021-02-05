package diplomaProject.shop2.controllers;

import diplomaProject.shop2.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductControllerImpl implements ProductController {

    @Autowired
    private ProductService productService;

}

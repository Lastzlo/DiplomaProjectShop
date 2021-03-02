package diplomaProject.shop2.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("/application-test.properties")
class PhotoTest {

    @Test
    void whenEquals_thenTrue () {
        Photo photo1 = new Photo ("/src");
        photo1.setId (10l);
        Photo photo2 = new Photo ("/src");
        photo2.setId (10l);

        Assertions.assertTrue (photo1.equals (photo2));

    }

    @Test
    void whenCheckEqualsWithOtherType_thenFalse () {
        Photo photo1 = new Photo ("/src");
        photo1.setId (10l);
        Product product = new Product ();
        product.setId (10l);
        product.setProductName ("Product");

        Assertions.assertFalse (photo1.equals (product));
    }

}
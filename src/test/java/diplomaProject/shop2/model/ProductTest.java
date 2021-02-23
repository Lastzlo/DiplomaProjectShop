package diplomaProject.shop2.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void hasPhotoByIdInPhotos_thenTrue () {
        // given
        Long photoId = 10L;

        Product product = new Product (){{
            addPhoto (
                    new Photo (){{
                        setId (photoId);
                    }}
            );
        }};

        //when
        boolean result = product.hasPhotoByIdInPhotos (photoId);

        //then
        Assertions.assertTrue (result);
    }

    @Test
    void hasPhotoByIdInPhotos_thenFalse () {
        // given
        Long photoId = 10L;
        Long notFoundPhotoId = 20L;

        Product product = new Product (){{
            addPhoto (
                    new Photo (){{
                        setId (photoId);
                    }}
            );
        }};

        //when
        boolean result = product.hasPhotoByIdInPhotos (notFoundPhotoId);

        //then
        Assertions.assertFalse (result);
    }
}
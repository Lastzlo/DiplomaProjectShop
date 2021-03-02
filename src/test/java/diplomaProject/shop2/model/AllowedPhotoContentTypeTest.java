package diplomaProject.shop2.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("/application-test.properties")
class AllowedPhotoContentTypeTest {

    @Test
    void getContentType () {
        String contentType = "image/jpeg";

        String jpegContentType = AllowedPhotoContentType.Jpeg.getContentType ();

        Assertions.assertEquals (contentType, jpegContentType);
    }

    @Test
    void isAllowedPhotoContentType_thenTrue () {
        String contentType = "image/jpeg";

        boolean result = AllowedPhotoContentType.isAllowedPhotoContentType (contentType);

        Assertions.assertTrue (result);
    }

    @Test
    void isAllowedPhotoContentType_thenFalse () {
        String contentType = "its content type not is not exits in AllowedPhotoContentType";

        boolean result = AllowedPhotoContentType.isAllowedPhotoContentType (contentType);

        Assertions.assertFalse (result);
    }
}
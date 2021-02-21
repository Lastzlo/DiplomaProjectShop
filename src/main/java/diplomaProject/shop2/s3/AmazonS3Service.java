package diplomaProject.shop2.s3;

import java.io.File;

public interface AmazonS3Service {
    String saveFile (File file, String fileName);
}

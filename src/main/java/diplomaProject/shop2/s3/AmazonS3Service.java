package diplomaProject.shop2.s3;

import java.io.File;

public interface AmazonS3Service {
    boolean saveFile (File file, String fileName);

    String getPathByFileName (String newFileName);
}

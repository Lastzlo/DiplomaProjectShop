package diplomaProject.shop2.s3;

import diplomaProject.shop2.dto.amazonS3.S3ServiceResultDTO;

import java.io.File;

public interface AmazonS3Service {
    S3ServiceResultDTO saveFile (File file, String fileName);
}

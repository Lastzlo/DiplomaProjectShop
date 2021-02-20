package diplomaProject.shop2.s3;

import diplomaProject.shop2.dto.amazonS3.S3ServiceResultDTO;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service{

    @Override
    public S3ServiceResultDTO saveFile (File file, String fileName) {
        return new S3ServiceResultDTO ();
    }

    @Override
    public String getPathByFileName (String newFileName) {
        return null;
    }

}

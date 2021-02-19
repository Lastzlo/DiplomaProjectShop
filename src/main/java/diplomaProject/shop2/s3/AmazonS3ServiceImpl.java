package diplomaProject.shop2.s3;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service{

    @Override
    public boolean saveFile (File file, String fileName) {
        return false;
    }

    @Override
    public String getPathByFileName (String newFileName) {
        return null;
    }

}

package diplomaProject.shop2.dto.results;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource("/application-test.properties")
class ResultDTOTest {


    @Test
    void isSuccess_whenNewBadResultWithMessage_thenIsNotSuccess() {
        ResultDTO resultDTO = new BadResult ("Some bugs");

        Assertions.assertFalse (resultDTO.isSuccess ());
        Assertions.assertEquals ("Some bugs", resultDTO.getMessage ());
    }

    @Test
    void isSuccess_whenNewSuccessResult_thenIsSuccess() {
        ResultDTO resultDTO = new SuccessResult ();

        Assertions.assertTrue (resultDTO.isSuccess ());
    }

    @Test
    void isSuccess_whenNewSuccessResultWithMessage_thenIsSuccess() {
        ResultDTO resultDTO = new SuccessResult ("product added successfully");

        Assertions.assertTrue (resultDTO.isSuccess ());
        Assertions.assertEquals ("product added successfully", resultDTO.getMessage ());
    }

}
package umcTask.umcAPI.baseResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseException extends Exception {
    private BaseResponseStatus status;
}

package umcTask.umcAPI.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class User {
    private Integer userIdx;
    private String userName;
    private String userId;
    private String userPw;
    private String status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul") @Column(name = "createdAt", insertable = false)
    private Date createdAt;
}

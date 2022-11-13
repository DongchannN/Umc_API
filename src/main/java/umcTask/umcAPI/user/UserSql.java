package umcTask.umcAPI.user;

public class UserSql {
    public static final String SELECT = "SELECT userIdx, userId, userName, userPw, createdAt, status FROM user WHERE 1=1";

    public static final String STATUS_CODE = " AND status = :status;";

    public static final String INSERT = "INSERT INTO user (userId, userName, userPw) values (:userId, :userName, :userPw);";

    public static final String DELETE = "DELETE FROM user WHERE 1=1";

    public static final String ID_CONDITION = " AND userIdx = :userIdx";

    public static final String UPDATE = "UPDATE user SET userName = :userName, userPw = :userPw WHERE 1=1";
}

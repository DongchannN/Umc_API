package umcTask.umcAPI.repository;

public class UserSql {
    public static final String SELECT = "SELECT userId, userName, userPw, createdAt, status FROM user WHERE 1=1;";

    public static final String STATUS_CODE = "AND status = :status;";

    public static final String INSERT = "INSERT INTO user (userId, userName, userPw, createdAt) values (:userId, :userName, :userPw, :createdAt);";
}

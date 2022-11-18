package umcTask.umcAPI.user;

public class UserSql {
    public static final String SELECT = "SELECT userIdx, userId, userName, userPw, createdAt, status FROM User WHERE 1=1";

    public static final String STATUS_CODE = " AND status = :status;";

    public static final String USER_ID = " AND userId = :userId;";

    public static final String INSERT = "INSERT INTO User (userId, userName, userPw) values (:userId, :userName, :userPw);";

    public static final String DELETE = "DELETE FROM User WHERE 1=1";

    public static final String ID_CONDITION = " AND userIdx = :userIdx";

    public static final String UPDATE = "UPDATE User SET userName = :userName, userPw = :userPw WHERE 1=1";
}

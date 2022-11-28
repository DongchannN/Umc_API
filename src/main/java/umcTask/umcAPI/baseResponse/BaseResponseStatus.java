package umcTask.umcAPI.baseResponse;

import lombok.Getter;


@Getter
public enum BaseResponseStatus {

    /**
     * 2000 : 성공
     */
    SUCCESS(true, 2000, "요청에 성공하였습니다."),

    /**
     * 4000 : 클라이언트 에러
     */
    BAD_REQUEST(false, 4000, "서버가 요청을 이해할 수 없습니다."),

    UNAUTHORIZED(false, 4001, "권한이 없습니다"),

    FORBIDDEN(false, 4003, "접근 권한이 없습니다."),

    NOT_FOUND(false, 4004, "없는 요청입니다."),

    WRONG_PASSWORD(false, 4005, "비밀번호가 잘못되었습니다."),

    WRONG_ID(false, 4006, "없는 아이디입니다"),

    EMPTY_JWT(false, 4007, "JWT가 없습니다."),

    INVALID_JWT(false, 4008, "유효하지 않은 JWT입니다."),

    RE_LOGIN(false, 4009, "다시 로그인 하세요"),

    /**
     * 5000 : 서버, 데이터베이스 에러
     */
    DATABASE_ERROR(false, 5000, "DB연결에 실패하였습니다."),

    SERVER_ERROR(false, 5001, "서버 연결에 실패하였습니다."),

    ENCRYPT_PASSWORD_ERROR(false, 5002, "비밀번호 암호화에 실패하였습니다.");

    private final boolean isSuccess;
    private final int statusCode;
    private final String message;

    BaseResponseStatus(boolean isSuccess, int statusCode, String message) {
        this.isSuccess = isSuccess;
        this.statusCode = statusCode;
        this.message = message;
    }
}

package umcTask.umcAPI;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import umcTask.invisible.JwtSecurityKey;
import umcTask.umcAPI.baseResponse.BaseException;
import umcTask.umcAPI.baseResponse.BaseResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class JwtService {

    public String createJwt(int userIdx) {
        Date date = new Date();
        return Jwts.builder().setHeaderParam("type", "jwt")
                .claim("userIdx", userIdx).setIssuedAt(date)
                .setExpiration(new Date(System.currentTimeMillis() + 1 * (1000 * 60 * 60 * 24 * 365)))
                .signWith(SignatureAlgorithm.HS256, JwtSecurityKey.JWT_SECURITY_KEY).compact();
    }

    public String getJwt() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    public int getUserIdx() throws BaseException {
        String accessToken = getJwt();
        if (accessToken == null || accessToken.length() == 0) {
            throw new BaseException(BaseResponseStatus.EMPTY_JWT);
        }

        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parser().setSigningKey(JwtSecurityKey.JWT_SECURITY_KEY).parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(BaseResponseStatus.INVALID_JWT);
        }

        return claimsJws.getBody().get("userIdx", Integer.class);
    }

}

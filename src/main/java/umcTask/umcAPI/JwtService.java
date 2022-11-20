package umcTask.umcAPI;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import umcTask.secure.JwtSecurityKey;

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

    public int getUserIdx() throws Exception {
        String accessToken = getJwt();
        if (accessToken == null || accessToken.length() == 0) {
            throw new Exception("no token");
        }

        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parser().setSigningKey(JwtSecurityKey.JWT_SECURITY_KEY).parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new Exception("invalid token");
        }

        return claimsJws.getBody().get("userIdx", Integer.class);
    }
}

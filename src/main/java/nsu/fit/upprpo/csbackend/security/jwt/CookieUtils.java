package nsu.fit.upprpo.csbackend.security.jwt;

import org.springframework.lang.Nullable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

    private CookieUtils() {}

    public static void saveCookie(String name, String value, int maxAge, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setSecure(false);
        response.addCookie(cookie);
    }

    @Nullable
    public static String getValueOf(String name, Cookie[] collection) {
        if (collection != null)
            for (Cookie cookie : collection) {
                if (cookie.getName().equals(name))
                    return cookie.getValue();
            }
        return null;
    }
}

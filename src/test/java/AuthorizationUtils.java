import com.fasterxml.jackson.databind.ObjectMapper;
import nsu.fit.upprpo.csbackend.dto.SecuredUserDTO;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AuthorizationUtils {
    static Cookie takeToken(String username, String password, MockMvc mockMvc, ObjectMapper objectMapper) throws Exception {
        SecuredUserDTO securedUserDTO = new SecuredUserDTO();
        securedUserDTO.setPassword(password);
        securedUserDTO.setUsername(username);

        String user = objectMapper.writeValueAsString(securedUserDTO);

        MvcResult mvcResult = mockMvc.perform(post("/auth/login").content(user).contentType("application/json")).andReturn();
        return mvcResult.getResponse().getCookie("JWTToken");
    }
}

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nsu.fit.upprpo.csbackend.dto.SecuredUserDTO;
import nsu.fit.upprpo.csbackend.security.data.types.SecuredUser;
import nsu.fit.upprpo.csbackend.shortentity.UserRegisterInfo;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthorizationUtils {
    static Cookie takeToken(String username, String password, MockMvc mockMvc, ObjectMapper objectMapper) throws Exception {
        SecuredUserDTO securedUserDTO = new SecuredUserDTO();
        securedUserDTO.setPassword(password);
        securedUserDTO.setUsername(username);

        String user = objectMapper.writeValueAsString(securedUserDTO);

        MvcResult mvcResult = mockMvc.perform(post("/auth/login").content(user).contentType("application/json")).andReturn();
        return mvcResult.getResponse().getCookie("JWTToken");
    }

    static MvcResult registerNewUser(String username, String password, ObjectMapper objectMapper, MockMvc mockMvc) throws Exception {
        UserRegisterInfo userRegisterInfo = new UserRegisterInfo();
        userRegisterInfo.setAge(20);
        SecuredUser securedUser = new SecuredUser();
        securedUser.setUsername(username);
        securedUser.setPassword(password);
        userRegisterInfo.setSecuredUser(securedUser);

        return mockMvc.perform(post("/auth/register")
                .content(objectMapper.writeValueAsString(userRegisterInfo))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }
}

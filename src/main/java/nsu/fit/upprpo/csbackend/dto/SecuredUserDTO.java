package nsu.fit.upprpo.csbackend.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nsu.fit.upprpo.csbackend.security.data.types.SecuredUser;

public class SecuredUserDTO {
    private String username;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public int hashCode() {
        return username.hashCode() + password.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof SecuredUserDTO))
            return false;
        SecuredUserDTO securedUser = (SecuredUserDTO) o;
        return securedUser.getUsername().equals(this.username) && securedUser.getPassword().equals(this.password);
    }

}

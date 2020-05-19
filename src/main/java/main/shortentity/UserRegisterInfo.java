package main.shortentity;

import main.rm.security.data.types.SecuredUser;

public class UserRegisterInfo {
    private SecuredUser securedUser;
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setSecuredUser(SecuredUser securedUser) {
        this.securedUser = securedUser;
    }

    public SecuredUser getSecuredUser() {
        return securedUser;
    }
}

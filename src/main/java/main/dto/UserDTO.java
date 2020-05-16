package main.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private String username;
    private int age;
    private float couchSerferRating = 0.0f;
    private long couchSerferRatingsNum = 0;
    private float houseProvisionRating = 0.0f;
    private long  houseProvisionRatingsNum = 0;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getCouchSerferRating() {
        return couchSerferRating;
    }

    public float getHouseProvisionRating() {
        return houseProvisionRating;
    }

    public int getAge() {
        return age;
    }

    public long getCouchSerferRatingsNum() {
        return couchSerferRatingsNum;
    }

    public long getHouseProvisionRatingsNum() {
        return houseProvisionRatingsNum;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCouchSerferRating(float couchSerferRating) {
        this.couchSerferRating = couchSerferRating;
    }

    public void setCouchSerferRatingsNum(long couchSerferRatingsNum) {
        this.couchSerferRatingsNum = couchSerferRatingsNum;
    }

    public void setHouseProvisionRating(float houseProvisionRating) {
        this.houseProvisionRating = houseProvisionRating;
    }

    public void setHouseProvisionRatingsNum(long houseProvisionRatingsNum) {
        this.houseProvisionRatingsNum = houseProvisionRatingsNum;
    }
}

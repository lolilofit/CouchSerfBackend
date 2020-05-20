package main.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class UserDTO implements Serializable {
    private String username;
    private int age;
    private float couchSerferRating = 0.0f;
    private long couchSerferRatingsNum = 0;
    private float houseProvisionRating = 0.0f;
    private long  houseProvisionRatingsNum = 0;
}

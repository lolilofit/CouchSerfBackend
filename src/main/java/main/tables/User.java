package main.tables;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Users")
public class User implements Serializable {
    private static final long serialVersionUID = -5170875020617735653L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "my_entity_seq_gen")
    @SequenceGenerator(name = "my_entity_seq_gen", sequenceName = "USERS_TRG")
    @Column(name = "userid")
    private long userid;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "age")
    private int age;

    @Column(name = "couch_serfer_rating")
    private float couchSerferRating = 0.0f;

    @Column(name = "couch_serfer_ratings_num")
    private long couchSerferRatingsNum = 0;

    @Column(name = "house_provision_rating")
    private float houseProvisionRating = 0.0f;

    @Column(name = "house_provision_rating_num")
    private long  houseProvisionRatingsNum = 0;

    public long getUserid() {
        return userid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUserid(long userid) {
        this.userid = userid;
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

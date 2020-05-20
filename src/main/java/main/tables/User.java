package main.tables;

import lombok.Data;
import main.dto.UserDTO;

import javax.persistence.*;
import java.io.Serializable;

@Data
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

    public User() {}

    public User(UserDTO userDTO) {
        this.age = userDTO.getAge();
        this.username = userDTO.getUsername();
        this.couchSerferRating = userDTO.getCouchSerferRating();
        this.couchSerferRatingsNum = userDTO.getCouchSerferRatingsNum();
        this.houseProvisionRating = userDTO.getHouseProvisionRating();
        this.houseProvisionRatingsNum = userDTO.getHouseProvisionRatingsNum();
    }
}

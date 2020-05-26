package nsu.fit.upprpo.csbackend.tables;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "Users")
public class User implements Serializable {
    private static final long serialVersionUID = -5170875020617735653L;
    private static final Logger logger = Logger.getLogger(User.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "my_entity_seq_gen")
    @SequenceGenerator(name = "my_entity_seq_gen", sequenceName = "USERS_TRG")
    @Column(name = "user_id")
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

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        return "";
    }

    @Override
    public int hashCode() {
        int sum = age;

        int cr = (int)couchSerferRating;
        int crnum = (int)couchSerferRatingsNum;
        int usernameHash = username.hashCode();
        int hp = (int)houseProvisionRating;
        int hpnum = (int)houseProvisionRatingsNum;

        sum += cr;
        sum += crnum;
        sum += usernameHash;
        sum += hp;
        sum += hpnum;

        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof  User))
            return false;
        User user = (User) o;
        return user.getUsername().equals(username)
                && age == user.getAge()
                && couchSerferRating == user.getCouchSerferRating()
                && couchSerferRatingsNum == user.getCouchSerferRatingsNum()
                && houseProvisionRating == user.getHouseProvisionRating()
                && houseProvisionRatingsNum == user.houseProvisionRatingsNum;
    }
}

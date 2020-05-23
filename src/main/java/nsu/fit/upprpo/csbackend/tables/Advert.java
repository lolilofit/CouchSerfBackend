package nsu.fit.upprpo.csbackend.tables;

import lombok.Data;
import nsu.fit.upprpo.csbackend.dto.AdvertDTO;
import nsu.fit.upprpo.csbackend.dto.UserDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Advert")
public class Advert implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "ad_generator")
    @SequenceGenerator(name = "ad_generator", sequenceName = "AD_SEQ")
    @Column(name = "adId")
    private Long adId;

    @ManyToOne
    @JoinColumn(name = "ownerId")
    private User owner;

    @NotNull
    @Column(name = "publicationDate")
    private Date publicationDate;

    @NotNull
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ADVERT_USER",
            joinColumns = {@JoinColumn(name = "ownerId")},
            inverseJoinColumns = {@JoinColumn(name = "userid")})
    private List<User> subscribers;

    //short header for advert on site
    @NotNull
    @Column(name = "header")
    private String header;

    @NotNull
    @Column(name = "message")
    private String message;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AdvertType advertType;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @NotNull
    @Column(name = "people_number")
    private int peopleNumber;

    @NotNull
    @Column(name = "arriving_date")
    private Date arrivingDate;

    @NotNull
    @Column(name = "check_out_date")
    private Date checkOutDate;


    public Advert() {
        subscribers = new ArrayList<>();
    }

    public Advert(AdvertDTO advertDTO) {
        this.adId = advertDTO.getAdId();
        if(advertDTO.getOwner() != null)
            this.owner = new User(advertDTO.getOwner());

        this.publicationDate = advertDTO.getPublicationDate();
        this.header = advertDTO.getHeader();
        this.message = advertDTO.getMessage();
        this.advertType = advertDTO.getAdvertType();

        if(advertDTO.getPlace() != null)
            this.place = new Place(advertDTO.getPlace());
        this.peopleNumber = advertDTO.getPeopleNumber();
        this.arrivingDate = advertDTO.getArrivingDate();
        this.checkOutDate = advertDTO.getCheckOutDate();
        this.subscribers = new ArrayList<>();

        List<UserDTO> userDTOList = advertDTO.getSubscribers();
        for(int i = 0; i < userDTOList.size(); i++) {
            User user = new User(userDTOList.get(i));
            this.subscribers.add(user);
        }
    }
}

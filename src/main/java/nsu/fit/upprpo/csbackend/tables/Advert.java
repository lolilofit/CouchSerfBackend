package nsu.fit.upprpo.csbackend.tables;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import nsu.fit.upprpo.csbackend.dto.AdvertDTO;

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
    @Column(name = "ad_id")
    private Long adId;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "publication_date")
    private Date publicationDate;

    @NotNull
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ADVERT_USER",
            joinColumns = {@JoinColumn(name = "owner_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
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
        int hc = subscribers.stream().mapToInt(User::hashCode).sum();

        return hc + owner.hashCode() + peopleNumber + publicationDate.hashCode() + arrivingDate.hashCode()
                + checkOutDate.hashCode() + header.hashCode()
                + message.hashCode() + place.hashCode()
                + advertType.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Advert))
            return false;
        Advert advert = (Advert) o;
        List<User> adSubscribers = advert.getSubscribers();

        if(advert.getSubscribers().size() != this.subscribers.size())
            return false;
        for(int i = 0; i < this.subscribers.size(); i++)
            if(this.subscribers.get(i).equals(adSubscribers.get(i)))
                return false;
        return owner.equals(advert.getOwner()) &&
                peopleNumber == advert.getPeopleNumber() &&
                publicationDate.equals(advert.getPublicationDate()) &&
                arrivingDate.equals(advert.getArrivingDate()) &&
                checkOutDate.equals(advert.getCheckOutDate()) &&
                header.equals(advert.getHeader()) &&
                message.equals(advert.getMessage()) &&
                place.equals(advert.getPlace()) &&
                advertType.equals(advert.getAdvertType());
    }
}

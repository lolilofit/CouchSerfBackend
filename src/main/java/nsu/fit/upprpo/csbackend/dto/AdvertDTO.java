package nsu.fit.upprpo.csbackend.dto;

import lombok.Data;
import nsu.fit.upprpo.csbackend.PublicUtils;
import nsu.fit.upprpo.csbackend.tables.AdvertType;

import java.io.Serializable;
import java.util.Date;

@Data
public class AdvertDTO implements Serializable {
    private Long adId;
    private Date publicationDate;
    private String header;
    private String message;
    private AdvertType advertType;
    private PlaceDTO place;
    private int peopleNumber;
    private Date arrivingDate;
    private Date checkOutDate;

    @Override
    public String toString() {
        return PublicUtils.publicToString(this);
    }

    @Override
    public int hashCode() {
        return  peopleNumber + publicationDate.hashCode() + arrivingDate.hashCode()
                + checkOutDate.hashCode() + header.hashCode()
                + message.hashCode() + place.hashCode()
                + advertType.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof AdvertDTO))
            return false;
        AdvertDTO advert = (AdvertDTO) o;

        return peopleNumber == advert.getPeopleNumber() &&
                publicationDate.equals(advert.getPublicationDate()) &&
                arrivingDate.equals(advert.getArrivingDate()) &&
                checkOutDate.equals(advert.getCheckOutDate()) &&
                header.equals(advert.getHeader()) &&
                message.equals(advert.getMessage()) &&
                place.equals(advert.getPlace()) &&
                advertType.equals(advert.getAdvertType());
    }
}

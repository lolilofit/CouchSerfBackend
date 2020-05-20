package nsu.fit.upprpo.csbackend.dto;

import lombok.Data;
import nsu.fit.upprpo.csbackend.tables.AdvertType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AdvertDTO implements Serializable {
    private Long adId;
    private UserDTO owner;
    private Date publicationDate;
    private List<UserDTO> subscribers;
    private String header;
    private String message;
    private AdvertType advertType;
    private PlaceDTO place;
    private int peopleNumber;
    private Date arrivingDate;
    private Date checkOutDate;

    public AdvertDTO() {
        subscribers = new ArrayList<>();
    }
}

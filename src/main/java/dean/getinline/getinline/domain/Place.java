package dean.getinline.getinline.domain;

import dean.getinline.getinline.constant.PlaceType;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class Place {
    private Long id;

    private PlaceType placeType;
    private String placeName;
    private String address;
    private String phoneNumber;
    private Integer capacity;
    private String memo;


    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}

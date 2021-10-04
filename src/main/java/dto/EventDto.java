package dto;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
public class EventDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private UUID eventId = UUID.randomUUID();
    private String transId;
    private String transTms;
    private String rcNum;
    private String clientId;
    private Integer eventCnt;
    private String locationCd;
    private String locationId1;
    private String locationId2;
    private String addrNbr;

}

package dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TestDto {
    String batchId;
    List<EventDto> eventDtos;
}

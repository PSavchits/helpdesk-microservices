package innowise.microservice.helpdesk.ticketsservice.mapper;

import innowise.microservice.helpdesk.ticketsservice.dto.HistoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

    @Mapping(target = "created", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "action", source = "action")
    @Mapping(target = "ticketId", source = "ticketId")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "description", source = "action")
    HistoryDTO toHistoryDTO(Integer userId, Integer ticketId, String action);

    @Mapping(target = "created", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "action", source = "action")
    @Mapping(target = "ticketId", source = "ticketId")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "description", source = "description")
    HistoryDTO toHistoryDTO(Integer userId, Integer ticketId, String action, String description);
}

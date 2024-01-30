package innowise.microservice.helpdesk.historyservice.mapper;

import innowise.microservice.helpdesk.historyservice.dto.HistoryDTO;
import innowise.microservice.helpdesk.historyservice.entity.History;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "action", source = "historyDTO.action")
    @Mapping(target = "ticketId", source = "historyDTO.ticketId")
    @Mapping(target = "userId", source = "historyDTO.userId")
    @Mapping(target = "description", source = "historyDTO.action")
    History toHistory(HistoryDTO historyDTO);
}

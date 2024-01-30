package innowise.microservice.helpdesk.ticketsservice.helpers;

import innowise.microservice.helpdesk.ticketsservice.dto.CommentDTO;
import innowise.microservice.helpdesk.ticketsservice.dto.FeedbackDTO;

public class MapperHelper {
    public static FeedbackDTO createFeedbackDTO() {
        return FeedbackDTO.builder()
                .rate(5)
                .text("Test feedback text")
                .ticketId(1)
                .build();
    }

    public static CommentDTO createCommentDTO() {
        return CommentDTO.builder()
                .text("Test comment text")
                .ticketId(1)
                .build();
    }
}

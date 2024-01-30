package innowise.microservice.helpdesk.ticketsservice.helpers;

import innowise.microservice.helpdesk.ticketsservice.dto.FeedbackDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Feedback;

import java.time.LocalDate;

public class FeedbackTestHelper {

    public static Feedback createFeedbackWithId(Long id) {
        return Feedback.builder()
                .id(id)
                .date(LocalDate.of(2025, 4, 1))
                .rate(5)
                .text("test text")
                .build();
    }

    public static FeedbackDTO createFeedbackDTO() {
        return FeedbackDTO.builder()
                .rate(5)
                .text("test text")
                .build();
    }
}

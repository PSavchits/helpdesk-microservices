package innowise.microservice.helpdesk.ticketsservice.mapper;

import innowise.microservice.helpdesk.ticketsservice.dto.FeedbackDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Feedback;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import innowise.microservice.helpdesk.ticketsservice.helpers.MapperHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FeedbackMapperTest {
    @Autowired
    private FeedbackMapper feedbackMapper;

    @Test
    void feedbackDTOtoFeedback_shouldReturnFeedback() {
        FeedbackDTO feedbackDTO = MapperHelper.createFeedbackDTO();

        Ticket ticket = new Ticket();

        User creator = new User();

        Feedback feedback = feedbackMapper.feedbackDTOtoFeedback(feedbackDTO, ticket, creator);

        Assertions.assertNotNull(feedback);
        Assertions.assertNull(feedback.getId());
        Assertions.assertEquals(creator, feedback.getUser());
        Assertions.assertEquals("Test feedback text", feedback.getText());
        Assertions.assertNotNull(feedback.getDate());
        Assertions.assertEquals(ticket, feedback.getTicket());
        Assertions.assertEquals(5, feedback.getRate());
    }
}

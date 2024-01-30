package innowise.microservice.helpdesk.ticketsservice.mapper;

import innowise.microservice.helpdesk.ticketsservice.dto.CommentDTO;
import innowise.microservice.helpdesk.ticketsservice.dto.TicketDTO;
import innowise.microservice.helpdesk.ticketsservice.entity.Comment;
import innowise.microservice.helpdesk.ticketsservice.entity.Ticket;
import innowise.microservice.helpdesk.ticketsservice.entity.User;
import innowise.microservice.helpdesk.ticketsservice.helpers.MapperHelper;
import innowise.microservice.helpdesk.ticketsservice.helpers.TicketServiceTestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CommentMapperTest {

    @Autowired
    private CommentMapper commentMapper;

    @Test
    void ticketDtoToComment_shouldReturnComment() {
        TicketDTO ticketDTO = TicketServiceTestHelper.createTicketDTO();

        User creator = new User();
        Ticket ticket = new Ticket();

        Comment comment = commentMapper.ticketDtoToComment(ticketDTO, creator, ticket);

        Assertions.assertNotNull(comment);
        Assertions.assertNull(comment.getId());
        Assertions.assertEquals(creator, comment.getUser());
        Assertions.assertEquals("Test comment text", comment.getText());
        Assertions.assertNotNull(comment.getDate());
        Assertions.assertEquals(ticket, comment.getTicket());
    }

    @Test
    void commentDtoToComment_shouldReturnComment() {
        CommentDTO commentDTO = MapperHelper.createCommentDTO();

        User creator = new User();
        Ticket ticket = new Ticket();

        Comment comment = commentMapper.commentDtoToComment(commentDTO, creator, ticket);

        Assertions.assertNotNull(comment);
        Assertions.assertNull(comment.getId());
        Assertions.assertEquals(creator, comment.getUser());
        Assertions.assertEquals("Test comment text", comment.getText());
        Assertions.assertNotNull(comment.getDate());
        Assertions.assertEquals(ticket, comment.getTicket());
    }
}

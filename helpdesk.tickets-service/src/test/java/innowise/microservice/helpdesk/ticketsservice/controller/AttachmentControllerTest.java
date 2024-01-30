package innowise.microservice.helpdesk.ticketsservice.controller;

import innowise.microservice.helpdesk.ticketsservice.services.AttachmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AttachmentControllerTest {

    @Mock
    private AttachmentService attachmentService;

    @InjectMocks
    private AttachmentController attachmentController;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(attachmentController).build();
    }

    @Test
    void downloadAttachment_shouldReturnAttachment_whenAttachmentExists() throws Exception {
        int attachmentId = 1;

        Resource mockResource = new ByteArrayResource("Test attachment content".getBytes());
        when(attachmentService.downloadAttachment(attachmentId)).thenReturn(ResponseEntity.ok().body(mockResource));

        mockMvc.perform(get("/helpdesk-service/attachments/{id}", attachmentId))
                .andExpect(status().isOk())
                .andExpect(content().string("Test attachment content"));

        verify(attachmentService, times(1)).downloadAttachment(attachmentId);
    }

    @Test
    void downloadAttachment_shouldReturnNotFound_whenAttachmentDoesNotExist() throws Exception {
        int nonExistentAttachmentId = 2;

        when(attachmentService.downloadAttachment(nonExistentAttachmentId)).thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(get("/helpdesk-service/attachments/{id}", nonExistentAttachmentId))
                .andExpect(status().isNotFound());

        verify(attachmentService, times(1)).downloadAttachment(nonExistentAttachmentId);
    }
}

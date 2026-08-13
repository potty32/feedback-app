package de.atruvia.feedback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.atruvia.feedback.dto.FeedbackDto;
import de.atruvia.feedback.service.FeedbackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {FeedbackController.class, GlobalExceptionHandler.class, WebConfig.class})
class FeedbackControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    FeedbackService feedbackService;

    @Test
    void getAlleFeedbacks_gibt200Zurueck() throws Exception {
        when(feedbackService.getAlleFeedbacks())
                .thenReturn(List.of(new FeedbackDto(1L, "K-1", 5, "Top")));

        mockMvc.perform(get("/api/v1/feedbacks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].kundennummer").value("K-1"))
                .andExpect(jsonPath("$[0].sterneBewertung").value(5));
    }

    @Test
    void feedbackErstellen_mitGueltigemDto_gibt201Zurueck() throws Exception {
        FeedbackDto dto = new FeedbackDto(null, "K-10", 4, "Guter Service");
        FeedbackDto gespeichert = new FeedbackDto(1L, "K-10", 4, "Guter Service");
        when(feedbackService.feedbackSpeichern(any())).thenReturn(gespeichert);

        mockMvc.perform(post("/api/v1/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void feedbackErstellen_mitUngueltigerKundennummer_gibt400Zurueck() throws Exception {
        FeedbackDto dto = new FeedbackDto(null, "UNGUELTIG", 4, "Kommentar");

        mockMvc.perform(post("/api/v1/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fehler[0]").value(org.hamcrest.Matchers.containsString("kundennummer")));
    }

    @Test
    void feedbackErstellen_mitSterneAusserhalbDesBereichs_gibt400Zurueck() throws Exception {
        FeedbackDto dto = new FeedbackDto(null, "K-10", 0, "Kommentar");

        mockMvc.perform(post("/api/v1/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void feedbackErstellen_mitLeererKommentar_gibt400Zurueck() throws Exception {
        FeedbackDto dto = new FeedbackDto(null, "K-10", 3, "");

        mockMvc.perform(post("/api/v1/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void feedbackErstellen_mitDoppelterKundennummer_gibt409Zurueck() throws Exception {
        FeedbackDto dto = new FeedbackDto(null, "K-1", 3, "Bereits vorhanden");
        when(feedbackService.feedbackSpeichern(any()))
                .thenThrow(new IllegalStateException("Für Kundennummer K-1 existiert bereits ein Feedback."));

        mockMvc.perform(post("/api/v1/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.fehler[0]").value(org.hamcrest.Matchers.containsString("K-1")));
    }

    @Test
    void feedbackLoeschen_gibt204Zurueck() throws Exception {
        doNothing().when(feedbackService).feedbackLoeschen(1L);

        mockMvc.perform(delete("/api/v1/feedbacks/1"))
                .andExpect(status().isNoContent());
    }
}

package de.atruvia.feedback.service;

import de.atruvia.feedback.dto.FeedbackDto;
import de.atruvia.feedback.entity.Feedback;
import de.atruvia.feedback.repository.FeedbackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

    @Mock
    FeedbackRepository feedbackRepository;

    @InjectMocks
    FeedbackService feedbackService;

    @Test
    void getAlleFeedbacks_gibtAlleZurueck() {
        Feedback entity = new Feedback(1L, "K-1", 4, "Gut");
        when(feedbackRepository.findAll()).thenReturn(List.of(entity));

        List<FeedbackDto> result = feedbackService.getAlleFeedbacks();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getKundennummer()).isEqualTo("K-1");
        assertThat(result.get(0).getSterneBewertung()).isEqualTo(4);
        assertThat(result.get(0).getKommentar()).isEqualTo("Gut");
    }

    @Test
    void feedbackSpeichern_speichertUndGibtDtoZurueck() {
        FeedbackDto dto = new FeedbackDto(null, "K-2", 5, "Super");
        Feedback gespeichert = new Feedback(1L, "K-2", 5, "Super");

        when(feedbackRepository.findByKundennummer("K-2")).thenReturn(List.of());
        when(feedbackRepository.save(any())).thenReturn(gespeichert);

        FeedbackDto result = feedbackService.feedbackSpeichern(dto);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getKundennummer()).isEqualTo("K-2");
        verify(feedbackRepository).save(any());
    }

    @Test
    void feedbackSpeichern_wirftExceptionBeiDoppelterKundennummer() {
        FeedbackDto dto = new FeedbackDto(null, "K-1", 3, "Ok");
        when(feedbackRepository.findByKundennummer("K-1"))
                .thenReturn(List.of(new Feedback(1L, "K-1", 3, "Ok")));

        assertThatThrownBy(() -> feedbackService.feedbackSpeichern(dto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("K-1");
    }

    @Test
    void feedbackLoeschen_ruftRepositoryAuf() {
        feedbackService.feedbackLoeschen(42L);
        verify(feedbackRepository).deleteById(42L);
    }
}

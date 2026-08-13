package de.atruvia.feedback.service;

import de.atruvia.feedback.dto.FeedbackDto;
import de.atruvia.feedback.entity.Feedback;
import de.atruvia.feedback.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public List<FeedbackDto> getAlleFeedbacks() {
        return feedbackRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public FeedbackDto getFeedbackById(Long id) {
        return feedbackRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Feedback nicht gefunden: " + id));
    }

    public List<FeedbackDto> getFeedbacksByKundennummer(String kundennummer) {
        return feedbackRepository.findByKundennummer(kundennummer).stream()
                .map(this::toDto)
                .toList();
    }

    public FeedbackDto feedbackSpeichern(FeedbackDto dto) {
        if (!feedbackRepository.findByKundennummer(dto.getKundennummer()).isEmpty()) {
            throw new IllegalStateException(
                "Für Kundennummer " + dto.getKundennummer() + " existiert bereits ein Feedback.");
        }
        Feedback gespeichert = feedbackRepository.save(toEntity(dto));
        return toDto(gespeichert);
    }

    public void feedbackLoeschen(Long id) {
        feedbackRepository.deleteById(id);
    }

    private FeedbackDto toDto(Feedback entity) {
        return new FeedbackDto(
                entity.getId(),
                entity.getKundennummer(),
                entity.getSterneBewertung(),
                entity.getKommentar()
        );
    }

    private Feedback toEntity(FeedbackDto dto) {
        return new Feedback(
                dto.getId(),
                dto.getKundennummer(),
                dto.getSterneBewertung(),
                dto.getKommentar()
        );
    }
}

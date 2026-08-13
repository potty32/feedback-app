package de.atruvia.feedback.controller;

import de.atruvia.feedback.dto.FeedbackDto;
import de.atruvia.feedback.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feedbacks")
@CrossOrigin(origins = "http://localhost:4200")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping
    public ResponseEntity<List<FeedbackDto>> getAlleFeedbacks() {
        return ResponseEntity.ok(feedbackService.getAlleFeedbacks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDto> getFeedbackById(@PathVariable Long id) {
        return ResponseEntity.ok(feedbackService.getFeedbackById(id));
    }

    @GetMapping("/kunde/{kundennummer}")
    public ResponseEntity<List<FeedbackDto>> getFeedbacksByKunde(@PathVariable String kundennummer) {
        return ResponseEntity.ok(feedbackService.getFeedbacksByKundennummer(kundennummer));
    }

    @PostMapping
    public ResponseEntity<FeedbackDto> feedbackErstellen(@Valid @RequestBody FeedbackDto dto) {
        FeedbackDto gespeichert = feedbackService.feedbackSpeichern(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(gespeichert);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> feedbackLoeschen(@PathVariable Long id) {
        feedbackService.feedbackLoeschen(id);
        return ResponseEntity.noContent().build();
    }
}

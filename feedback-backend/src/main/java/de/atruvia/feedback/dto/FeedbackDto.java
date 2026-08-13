package de.atruvia.feedback.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDto {

    private Long id;

    @NotBlank(message = "Kundennummer ist erforderlich")
    @Pattern(regexp = "^K-\\d+$", message = "Kundennummer muss dem Format K-<Zahl> entsprechen")
    private String kundennummer;

    @Min(value = 1, message = "Bewertung muss mindestens 1 Stern sein")
    @Max(value = 5, message = "Bewertung darf maximal 5 Sterne sein")
    private int sterneBewertung;

    @NotBlank(message = "Kommentar ist erforderlich")
    @Size(max = 1000, message = "Kommentar darf maximal 1000 Zeichen lang sein")
    private String kommentar;
}

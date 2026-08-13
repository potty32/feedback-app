import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Feedback } from '../../models/feedback.model';
import { FeedbackService } from '../../services/feedback.service';
import { FeedbackFormComponent } from '../feedback-form/feedback-form.component';

@Component({
  selector: 'app-feedback-list',
  standalone: true,
  imports: [CommonModule, FeedbackFormComponent],
  templateUrl: './feedback-list.component.html',
  styleUrl: './feedback-list.component.css'
})
export class FeedbackListComponent implements OnInit {

  feedbacks: Feedback[] = [];
  fehlerMeldung: string | null = null;

  constructor(private feedbackService: FeedbackService) {}

  ngOnInit(): void {
    this.feedbackLaden();
  }

  feedbackLaden(): void {
    this.feedbackService.getAlleFeedbacks().subscribe({
      next: (daten) => {
        this.feedbacks = daten;
        this.fehlerMeldung = null;
      },
      error: () => {
        this.fehlerMeldung = 'Feedbacks konnten nicht geladen werden.';
      }
    });
  }

  feedbackLoeschen(id: number): void {
    this.feedbackService.feedbackLoeschen(id).subscribe({
      next: () => this.feedbackLaden(),
      error: () => {
        this.fehlerMeldung = 'Feedback konnte nicht gelöscht werden.';
      }
    });
  }

  sterneArray(bewertung: number): number[] {
    return Array.from({ length: bewertung }, (_, i) => i + 1);
  }
}

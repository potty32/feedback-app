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
  aktiverFilter: number | null = null;

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
      error: (err) => {
        this.fehlerMeldung = `Feedbacks konnten nicht geladen werden. [${err.status} ${err.statusText}] ${JSON.stringify(err.error)}`;
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

  get sternStatistik(): { sterne: number; anzahl: number }[] {
    return [5, 4, 3, 2, 1].map(sterne => ({
      sterne,
      anzahl: this.feedbacks.filter(f => f.sterneBewertung === sterne).length
    }));
  }

  get gefilterteFeedbacks(): Feedback[] {
    if (this.aktiverFilter === null) return this.feedbacks;
    return this.feedbacks.filter(f => f.sterneBewertung === this.aktiverFilter);
  }

  filterToggle(sterne: number): void {
    this.aktiverFilter = this.aktiverFilter === sterne ? null : sterne;
  }

  balkenBreite(anzahl: number): string {
    const max = Math.max(...this.sternStatistik.map(s => s.anzahl), 1);
    return `${(anzahl / max) * 100}%`;
  }

  sterneArray(bewertung: number): number[] {
    return Array.from({ length: bewertung }, (_, i) => i + 1);
  }
}

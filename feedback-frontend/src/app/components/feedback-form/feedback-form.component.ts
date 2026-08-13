import { Component, EventEmitter, Output } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { FeedbackService } from '../../services/feedback.service';

@Component({
  selector: 'app-feedback-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './feedback-form.component.html',
  styleUrl: './feedback-form.component.css'
})
export class FeedbackFormComponent {

  @Output() feedbackErstellt = new EventEmitter<void>();

  form: FormGroup;
  sterneOptionen = [1, 2, 3, 4, 5];
  hoveredStern = 0;
  sendenLaeuft = false;
  fehlerMeldung: string | null = null;

  constructor(
    private fb: FormBuilder,
    private feedbackService: FeedbackService
  ) {
    this.form = this.fb.group({
      kundennummer: ['', [Validators.required, Validators.pattern(/^K-\d+$/)]],
      sterneBewertung: [0, [Validators.required, Validators.min(1), Validators.max(5)]],
      kommentar: ['', [Validators.required, Validators.maxLength(1000)]]
    });
  }

  sternSetzen(wert: number): void {
    this.form.patchValue({ sterneBewertung: wert });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.sendenLaeuft = true;
    this.fehlerMeldung = null;

    this.feedbackService.feedbackErstellen({ id: 0, ...this.form.value }).subscribe({
      next: () => {
        this.form.reset({ kundennummer: '', sterneBewertung: 0, kommentar: '' });
        this.hoveredStern = 0;
        this.sendenLaeuft = false;
        this.feedbackErstellt.emit();
      },
      error: () => {
        this.fehlerMeldung = 'Feedback konnte nicht gespeichert werden.';
        this.sendenLaeuft = false;
      }
    });
  }

  istUngueltig(feld: string): boolean {
    const control = this.form.get(feld);
    return !!(control?.invalid && control.touched);
  }
}

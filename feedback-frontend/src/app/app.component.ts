import { Component } from '@angular/core';
import { FeedbackListComponent } from './components/feedback-list/feedback-list.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [FeedbackListComponent],
  template: '<app-feedback-list></app-feedback-list>'
})
export class AppComponent {}

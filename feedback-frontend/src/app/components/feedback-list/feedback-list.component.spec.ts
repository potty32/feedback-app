import { TestBed } from '@angular/core/testing';
import { FeedbackListComponent } from './feedback-list.component';
import { FeedbackService } from '../../services/feedback.service';
import { of } from 'rxjs';
import { Feedback } from '../../models/feedback.model';
import { NO_ERRORS_SCHEMA } from '@angular/core';

const testFeedbacks: Feedback[] = [
  { id: 1, kundennummer: 'K-1', sterneBewertung: 5, kommentar: 'Sehr gut' },
  { id: 2, kundennummer: 'K-2', sterneBewertung: 5, kommentar: 'Toll' },
  { id: 3, kundennummer: 'K-3', sterneBewertung: 3, kommentar: 'Ok' },
  { id: 4, kundennummer: 'K-4', sterneBewertung: 1, kommentar: 'Schlecht' },
];

describe('FeedbackListComponent', () => {
  let component: FeedbackListComponent;
  let feedbackServiceSpy: jasmine.SpyObj<FeedbackService>;

  beforeEach(() => {
    feedbackServiceSpy = jasmine.createSpyObj('FeedbackService', ['getAlleFeedbacks', 'feedbackLoeschen']);
    feedbackServiceSpy.getAlleFeedbacks.and.returnValue(of(testFeedbacks));

    TestBed.configureTestingModule({
      imports: [FeedbackListComponent],
      providers: [{ provide: FeedbackService, useValue: feedbackServiceSpy }],
      schemas: [NO_ERRORS_SCHEMA]
    });

    const fixture = TestBed.createComponent(FeedbackListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('sollte erstellt werden', () => {
    expect(component).toBeTruthy();
  });

  it('sternStatistik berechnet korrekte Anzahlen', () => {
    const statistik = component.sternStatistik;
    expect(statistik.find(s => s.sterne === 5)?.anzahl).toBe(2);
    expect(statistik.find(s => s.sterne === 3)?.anzahl).toBe(1);
    expect(statistik.find(s => s.sterne === 1)?.anzahl).toBe(1);
    expect(statistik.find(s => s.sterne === 2)?.anzahl).toBe(0);
  });

  it('filterToggle setzt aktivenFilter', () => {
    component.filterToggle(5);
    expect(component.aktiverFilter).toBe(5);
  });

  it('filterToggle beim zweiten Klick hebt Filter auf', () => {
    component.filterToggle(5);
    component.filterToggle(5);
    expect(component.aktiverFilter).toBeNull();
  });

  it('gefilterteFeedbacks gibt nur Feedbacks mit gewählten Sternen zurück', () => {
    component.filterToggle(5);
    expect(component.gefilterteFeedbacks.length).toBe(2);
    expect(component.gefilterteFeedbacks.every(f => f.sterneBewertung === 5)).toBeTrue();
  });

  it('gefilterteFeedbacks gibt ohne Filter alle zurück', () => {
    expect(component.gefilterteFeedbacks.length).toBe(4);
  });
});

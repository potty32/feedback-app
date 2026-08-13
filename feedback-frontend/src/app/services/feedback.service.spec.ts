import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { FeedbackService } from './feedback.service';
import { environment } from '../../environments/environment';

describe('FeedbackService', () => {
  let service: FeedbackService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [FeedbackService]
    });
    service = TestBed.inject(FeedbackService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('getAlleFeedbacks schickt GET an die richtige URL', () => {
    service.getAlleFeedbacks().subscribe();
    const req = httpMock.expectOne(environment.apiUrl);
    expect(req.request.method).toBe('GET');
    req.flush([]);
  });

  it('feedbackErstellen schickt POST mit korrektem Body', () => {
    const dto = { id: null, kundennummer: 'K-1', sterneBewertung: 5, kommentar: 'Toll' };
    service.feedbackErstellen(dto as any).subscribe();
    const req = httpMock.expectOne(environment.apiUrl);
    expect(req.request.method).toBe('POST');
    expect(req.request.body.kundennummer).toBe('K-1');
    req.flush({ ...dto, id: 1 });
  });

  it('feedbackLoeschen schickt DELETE mit korrekter ID', () => {
    service.feedbackLoeschen(42).subscribe();
    const req = httpMock.expectOne(`${environment.apiUrl}/42`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });
});

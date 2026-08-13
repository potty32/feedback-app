import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Feedback } from '../models/feedback.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {

  private readonly apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getAlleFeedbacks(): Observable<Feedback[]> {
    return this.http.get<Feedback[]>(this.apiUrl);
  }

  getFeedbackById(id: number): Observable<Feedback> {
    return this.http.get<Feedback>(`${this.apiUrl}/${id}`);
  }

  getFeedbacksByKundennummer(kundennummer: string): Observable<Feedback[]> {
    return this.http.get<Feedback[]>(`${this.apiUrl}/kunde/${kundennummer}`);
  }

  feedbackErstellen(feedback: Feedback): Observable<Feedback> {
    return this.http.post<Feedback>(this.apiUrl, feedback);
  }

  feedbackLoeschen(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}

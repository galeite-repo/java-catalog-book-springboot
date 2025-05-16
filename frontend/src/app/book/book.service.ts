import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { ApiResponse, Book, PageResponse } from "./book.model";

@Injectable({
  providedIn: 'root'
})
export class BookService {
  private apiUrl = 'http://localhost:3000/api/books';

  private username = 'admin';
  private password = 'admin';

  constructor(private http: HttpClient) { }

  private get authHeaders(): HttpHeaders {
    const token = btoa(`${this.username}:${this.password}`);
    return new HttpHeaders({
      'Authorization': `Basic ${token}`
    });
  }

  getBooks(page: number = 0, size: number = 10): Observable<ApiResponse<PageResponse>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<ApiResponse<PageResponse>>(this.apiUrl, { params, headers: this.authHeaders });
  }

  getBookById(id: number): Observable<Book> {
    return this.http.get<Book>(`${this.apiUrl}/${id}`, { headers: this.authHeaders });
  }

  getBooksByGenre(genre: string): Observable<ApiResponse<Book[]>> {
    const params = new HttpParams().set('genre', genre);
    return this.http.get<ApiResponse<Book[]>>(`${this.apiUrl}/genre`, { params, headers: this.authHeaders });
  }

  getBooksByAuthor(author: string): Observable<ApiResponse<Book[]>> {
    const params = new HttpParams().set('author', author.trim());
    return this.http.get<ApiResponse<Book[]>>(`${this.apiUrl}/author`, { params, headers: this.authHeaders });
  }

  getGenres(): Observable<ApiResponse<string[]>> {
    return this.http.get<ApiResponse<string[]>>(`${this.apiUrl}/genres`, { headers: this.authHeaders });
  }
}

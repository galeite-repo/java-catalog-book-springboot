import { CommonModule, } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Book } from './book.model';
import { BookService } from './book.service';

@Component({
  selector: 'app-book',
  standalone: true,
  imports: [CommonModule, HttpClientModule, FormsModule],
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.scss']
})
export class BookComponent implements OnInit {
  books: Book[] = [];
  genres: string[] = [];
  selectedGenre: string = '';
  searchAuthor: string = '';

  // Paginação
  currentPage: number = 0;
  pageSize: number = 10;
  totalPages: number = 0;
  totalElements: number = 0;

  // Modal
  selectedBook: Book | null = null;
  showModal: boolean = false;

  constructor(private bookService: BookService) { }

  ngOnInit(): void {
    this.loadBooks();
    this.loadGenres();
  }

  loadBooks(): void {
    this.bookService.getBooks(this.currentPage, this.pageSize).subscribe({
      next: (response) => {
        console.log(response)
        this.books = response.data.content;
        this.totalPages = response.data.page.totalPages;
        this.totalElements = response.data.page.totalElements;
      },
      error: (error) => {
        console.error('Erro ao carregar livros:', error);
      }
    });
  }

  loadGenres(): void {
    this.bookService.getGenres().subscribe({
      next: (response) => {
        this.genres = response.data;
      },
      error: (error) => {
        console.error('Erro ao carregar gêneros:', error);
      }
    });
  }

  filterByGenre(): void {
    if (this.selectedGenre) {
      this.bookService.getBooksByGenre(this.selectedGenre).subscribe({
        next: (response) => {
          this.books = response.data;
          this.totalPages = 1;
          this.currentPage = 0;
        },
        error: (error) => {
          console.error('Erro ao filtrar por gênero:', error);
        }
      });
    } else {
      this.loadBooks();
    }
  }

  searchByAuthor(): void {
    if (this.searchAuthor.trim()) {
      this.bookService.getBooksByAuthor(this.searchAuthor).subscribe({
        next: (response) => {
          this.books = response.data;
          this.totalPages = 1;
          this.currentPage = 0;
        },
        error: (error) => {
          console.error('Erro ao buscar por autor:', error);
        }
      });
    } else {
      this.loadBooks();
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadBooks();
    }
  }

  prevPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadBooks();
    }
  }

  openBookDetails(book: Book): void {
    this.selectedBook = book;
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.selectedBook = null;
  }

  clearFilters(): void {
    this.selectedGenre = '';
    this.searchAuthor = '';
    this.loadBooks();
  }

}

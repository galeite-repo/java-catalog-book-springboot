// Modelo para representar a entidade Book
export interface Book {
  id: number;
  title: string;
  author: string;
  description: string;
  genre: string;
}

export interface ApiResponse<T> {
  statusCode: number;
  message: string;
  data: T;
}

export interface PageResponse {
  content: Book[];
  page: any;
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

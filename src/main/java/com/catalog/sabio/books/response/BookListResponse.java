package com.catalog.sabio.books.response;

import com.catalog.sabio.books.model.BookEntity;

import java.util.List;


public class BookListResponse extends ApiResponseDto<List<BookEntity>> {
    public BookListResponse(int status, String code, List<BookEntity> data) {
        super(status, code, data);
    }
}
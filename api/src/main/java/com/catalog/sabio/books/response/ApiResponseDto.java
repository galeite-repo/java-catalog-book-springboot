package com.catalog.sabio.books.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseDto<T> {
    private int status;
    private String code;
    private T data;
}

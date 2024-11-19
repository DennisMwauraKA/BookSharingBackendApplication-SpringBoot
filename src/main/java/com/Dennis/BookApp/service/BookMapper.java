package com.Dennis.BookApp.service;

import com.Dennis.BookApp.dtos.BookRequestDto;
import com.Dennis.BookApp.dtos.BorrowedBookResponse;
import com.Dennis.BookApp.dtos.GetBookResponseDto;
import com.Dennis.BookApp.entity.Book;
import com.Dennis.BookApp.entity.BookTransactionHistory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;


@Builder
@Getter
@Setter

@Service
public class BookMapper {
    public Book toBook(BookRequestDto bookRequestDto) {
        return Book.builder()
                .id(bookRequestDto.id())
                .title(bookRequestDto.title())
                .authorName(bookRequestDto.authorName())
                .shareable(bookRequestDto.shareable())
                .synopsis(bookRequestDto.synopsis())
                .archived(false)
                .build();


    }

    public GetBookResponseDto toBookResponse(Book book) {
        return GetBookResponseDto.builder()

                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .isbn(book.getIsbn())
                .synopsis(book.getSynopsis())
                .owner(book.getOwner().getFirstName())
                // .cover(book.getCover)
                .rate(book.getRate())
                .archived(book.isArchived())
                .shareable(book.isShareable())
                .build();

    }


    public BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory history) {
        return BorrowedBookResponse.builder()
                .id(history.getBook().getId())
                .title(history.getBook().getTitle())
                .authorName(history.getBook().getAuthorName())
                .isbn(history.getBook().getIsbn())
                .rate(history.getBook().getRate())
                .returnApproved(history.isReturnApproved())
                .returned(history.isReturned())
                .build();


    }
}

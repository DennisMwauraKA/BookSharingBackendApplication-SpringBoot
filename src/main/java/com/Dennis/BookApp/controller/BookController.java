package com.Dennis.BookApp.controller;


import com.Dennis.BookApp.common.PageResponse;
import com.Dennis.BookApp.dtos.BookRequestDto;
import com.Dennis.BookApp.dtos.BorrowedBookResponse;
import com.Dennis.BookApp.dtos.GetBookResponseDto;
import com.Dennis.BookApp.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/books")
@Tag(name = "Book")
public class BookController {

    @Autowired
    private BookService service;


    @PostMapping("/saveBook")
    public ResponseEntity<Integer> addBook(@RequestBody @Valid BookRequestDto bookRequestDto, Authentication connectedUser) {
        return ResponseEntity.ok(service.save(bookRequestDto, connectedUser));
    }

    @GetMapping("{book-id}")

    public ResponseEntity<GetBookResponseDto> findBookById(@PathVariable("book-id") Integer bookId) {
        return ResponseEntity.ok(service.findById(bookId));
    }

    // implementing paging
    @GetMapping("/")
    public ResponseEntity<PageResponse<GetBookResponseDto>> findAllBooks(@RequestParam(name = "page", defaultValue = "0", required = false) int page, @RequestParam(name = "size", defaultValue = "10", required = false) int size, Authentication connectedUser) {

        return ResponseEntity.ok(service.findByAllBooks(page, size, connectedUser));
    }


    @GetMapping("/owner")
    public ResponseEntity<PageResponse<GetBookResponseDto>> findAllBooksByOwner(@RequestParam(name = "page", defaultValue = "0", required = false)

                                                                                int page, @RequestParam(name = "size", defaultValue = "10", required = false) int size, Authentication connectedUser) {
        return ResponseEntity.ok(service.findByAllBooksByOwner(page, size, connectedUser));
    }


    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(@RequestParam(name = "page", defaultValue = "0", required = false)

                                                                                int page, @RequestParam(name = "size", defaultValue = "10", required = false) int size, Authentication connectedUser) {
        return ResponseEntity.ok(service.findByAllBorrowedBooks(page, size, connectedUser));
    }




    @GetMapping("/returned")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(@RequestParam(name = "page", defaultValue = "0", required = false)

                                                                                   int page, @RequestParam(name = "size", defaultValue = "10", required = false) int size, Authentication connectedUser) {
        return ResponseEntity.ok(service.findByAllReturnedBooks(page, size, connectedUser));
    }


    @PatchMapping("/shareable/{book-id}")
    public ResponseEntity<Integer> updateShareableStatus(
            @PathVariable("book-id")Integer bookId,
            Authentication connectedUser
    ){

        return ResponseEntity.ok(service.updateShareableBookStatus(bookId,connectedUser));
    }


    @PatchMapping("/archive/{book-id}")
    public ResponseEntity<Integer> updateArchiveStatus(
            @PathVariable("book-id")Integer bookId,
            Authentication connectedUser
    ){

        return ResponseEntity.ok(service.updateArchivedStatus(bookId,connectedUser));
    }

    @PostMapping("/borrow/{book-id}")

    public ResponseEntity<Integer>borrowBook(@PathVariable("{book-id}")Integer bookId, Authentication connectedUser){

        return ResponseEntity.ok(service.borrowBook(connectedUser,bookId));
    }



    @PostMapping("/borrow/returned/{book-id}")

    public ResponseEntity<Integer>returnBorrowedBook(@PathVariable("{book-id}")Integer bookId, Authentication connectedUser){

        return ResponseEntity.ok(service.returnBorrowedBook(connectedUser,bookId));
    }






}

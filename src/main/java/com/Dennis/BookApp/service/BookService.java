package com.Dennis.BookApp.service;


import com.Dennis.BookApp.common.PageResponse;
import com.Dennis.BookApp.dtos.BookRequestDto;
import com.Dennis.BookApp.dtos.BorrowedBookResponse;
import com.Dennis.BookApp.dtos.GetBookResponseDto;
import com.Dennis.BookApp.entity.Book;
import com.Dennis.BookApp.entity.BookTransactionHistory;
import com.Dennis.BookApp.entity.User;
import com.Dennis.BookApp.exceptionhandler.OperationNotPermittedException;
import com.Dennis.BookApp.file.FileStorageService;
import com.Dennis.BookApp.repo.BookRepo;
import com.Dennis.BookApp.repo.BookTransactionsHistoryRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static com.Dennis.BookApp.entity.BookSpecification.withOwnerId;

@Service
@RequiredArgsConstructor
public class BookService {



    private final BookRepo bookRepository;

    private final BookMapper bookMapper;
    private final FileStorageService fileStorageService;

    private final BookTransactionsHistoryRepo bookTransactionsHistoryRepo;

    public Integer save(BookRequestDto bookRequestDto, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Book book = bookMapper.toBook(bookRequestDto);
        book.setOwner(user);
        return bookRepository.save(book).getId();


    }

    public GetBookResponseDto findById(Integer bookId) {
        return bookRepository.findById(bookId).map(bookMapper::toBookResponse)
                .orElseThrow(() -> new EntityNotFoundException("No book found"));
    }

    public PageResponse<GetBookResponseDto> findByAllBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllDisplayableBooks(pageable, user.getId());
        List<GetBookResponseDto> getBookResponseDto = books.stream().map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                getBookResponseDto,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()

        );
    }

    public PageResponse<GetBookResponseDto> findByAllBooksByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAll(withOwnerId(user.getId()), pageable);
        List<GetBookResponseDto> getBookResponseDto = books.stream().map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                getBookResponseDto,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()

        );
    }

    public PageResponse<BorrowedBookResponse> findByAllBorrowedBooks(int page, int size, Authentication connectedUser) {

        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> allBorrowedBooks = bookTransactionsHistoryRepo.findAllBorrowedBooks(pageable, user.getId());
        List<BorrowedBookResponse> bookResponse = allBorrowedBooks.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponse,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.getNumberOfElements(),
                allBorrowedBooks.isLast(),
                allBorrowedBooks.isFirst()
        );
    }

    public PageResponse<BorrowedBookResponse> findByAllReturnedBooks(int page, int size, Authentication connectedUser) {

        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> allBorrowedBooks = bookTransactionsHistoryRepo.findAllReturnedBooks(pageable, user.getId());
        List<BorrowedBookResponse> bookResponse = allBorrowedBooks.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();
        return new PageResponse<>(
                bookResponse,
                allBorrowedBooks.getNumber(),
                allBorrowedBooks.getSize(),
                allBorrowedBooks.getTotalPages(),
                allBorrowedBooks.getNumberOfElements(),
                allBorrowedBooks.isLast(),
                allBorrowedBooks.isFirst()
        );

    }


    public Integer updateShareableBookStatus(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("No book found with this id"));
        User user = ((User) connectedUser.getPrincipal());
        if (!Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("you cannot update others' books status");
        }
        book.setShareable(!book.isShareable());
        bookRepository.save(book);
        return bookId;
    }

    public Integer updateArchivedStatus(Integer bookId, Authentication connectedUser) {

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("No book found with this id"));
        User user = ((User) connectedUser.getPrincipal());
        if (!Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("you cannot update others' Archived Book status");
        }
        book.setArchived(!book.isArchived());
        bookRepository.save(book);
        return bookId;


    }

    public Integer borrowBook(Authentication connectedUser, Integer bookId) {

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("No book found in this section" + bookId));
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("The book can not be borrowed because it is either archived or not shareable");
        }
        User user = ((User) connectedUser.getPrincipal());
        if (Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("you cannot Borrow your own book");
        }

        final boolean isAlreadyBorrowed = bookTransactionsHistoryRepo.isAlreadyBorrowedByUser(bookId, user.getId());
        if (isAlreadyBorrowed) {
            throw new OperationNotPermittedException("you can not borrow the book as it is already borrowed");
        }

        BookTransactionHistory bookTransactionHistory = BookTransactionHistory.builder()
                .user(user)
                .book(book)
                .returned(false)
                .returnApproved(false)
                .build();

        return bookTransactionsHistoryRepo.save(bookTransactionHistory).getUser().getId();
    }


    public Integer returnBorrowedBook(Authentication connectedUser, Integer bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("No book found in this section" + bookId));
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("The book can not be returned because it is either archived or not shareable");
        }
        User user = ((User) connectedUser.getPrincipal());
        if (Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("you cannot Borrow or return  your own book");
        }
        BookTransactionHistory bookTransactionHistory = bookTransactionsHistoryRepo.findByBookIdAndUserId(bookId, user.getId())
                .orElseThrow(() -> new OperationNotPermittedException("You did not borrow this book"));
        bookTransactionHistory.setReturned(true);
        return bookTransactionsHistoryRepo.save(bookTransactionHistory).getUser().getId();
    }


    public Integer approveReturnedBorrowedBook(Authentication connectedUser, Integer bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("No book found in this section" + bookId));
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("The book can not be returned because it is either archived or not shareable");
        }
        User user = ((User) connectedUser.getPrincipal());
        if (Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("you cannot Borrow or return  your own book");
        }
        BookTransactionHistory bookTransactionHistory = bookTransactionsHistoryRepo.findByBookIdAndOwnerId(bookId, user.getId())
                .orElseThrow(() -> new OperationNotPermittedException("The book isnot returned yet"));

        bookTransactionHistory.setReturnApproved(true);
        return bookTransactionsHistoryRepo.save(bookTransactionHistory).getUser().getId();
    }

    public void uploadBookCoverPicture(MultipartFile file, Authentication connectedUser, Integer bookId) {

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("No book found in this section" + bookId));
        User user = ((User) connectedUser.getPrincipal());
var bookCover =fileStorageService.saveFile(file,user.getId());
book.setBookCover(bookCover);
bookRepository.save(book);
    }
}

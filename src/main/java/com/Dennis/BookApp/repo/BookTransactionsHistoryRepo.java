package com.Dennis.BookApp.repo;

import com.Dennis.BookApp.entity.BookTransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookTransactionsHistoryRepo extends JpaRepository<BookTransactionHistory, Integer> {

    @Query("""
            SELECT history
            FROM BookTransactionHistory history
            WHERE history.user.Id=:userId
            
            """)
    Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, Integer userId);


    @Query("""
            SELECT history
            FROM BookTransactionHistory history
            WHERE history.book.createdBy = :userId
            
            """)
    Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, Integer userId);

    @Query(
            """
                    SELECT
                    (COUNT(*)>0) AS isBorrowed
                    FROM BookTransactionHistory bookTransactionHistory
                    WHERE bookTransactionHistory.user.Id=:userId
                    AND bookTransactionHistory.book.id=:bookId
                    AND bookTransactionHistory.returnApproved = false
                    """
    )
    boolean isAlreadyBorrowedByUser(@Param("userId") Integer userId, @Param("bookId") Integer bookId);


    @Query("""
            SELECT transaction
            FROM BookTransactionHistory transaction
            WHERE transaction.user.Id = :userId
            AND transaction.book.id =:bookId
            AND transaction.returned=false
            AND transaction.returnApproved=false
            """)
    Optional<BookTransactionHistory> findByBookIdAndUserId(Integer bookId, Integer userId);


    @Query("""
    SELECT transaction
    FROM BookTransactionHistory transaction
    WHERE transaction.book.createdBy = :ownerId
    AND transaction.book.id = :bookId
    AND transaction.returned = true
    AND transaction.returnApproved = false
    """)
    Optional<BookTransactionHistory> findByBookIdAndOwnerId(@Param("bookId") Integer bookId, @Param("ownerId") Integer ownerId);

    }
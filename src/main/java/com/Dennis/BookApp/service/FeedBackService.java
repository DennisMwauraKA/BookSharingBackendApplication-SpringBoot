package com.Dennis.BookApp.service;

import com.Dennis.BookApp.common.PageResponse;
import com.Dennis.BookApp.dtos.FeedBackResponseDto;
import com.Dennis.BookApp.dtos.FeedbackRequestDto;
import com.Dennis.BookApp.entity.Book;
import com.Dennis.BookApp.entity.Feedback;
import com.Dennis.BookApp.entity.User;
import com.Dennis.BookApp.exceptionhandler.OperationNotPermittedException;
import com.Dennis.BookApp.repo.BookRepo;
import com.Dennis.BookApp.repo.FeedBackRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedBackService {
    private final BookRepo bookRepository;
    private final FeedBackMapper feedBackMapper;
    private final FeedBackRepo feedBackRepo;

    public Integer saveFeedBack(@Valid FeedbackRequestDto request, Authentication connectedUser) {
        Book book = bookRepository.findById(request.bookId()).orElseThrow(() -> new EntityNotFoundException("The book with that id does not exist"));
        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("The book is Archived or not shareable");
        }
        User user = ((User) connectedUser.getPrincipal());

        if (Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You are the owner of  the book and therefore cannot provide feedback");
        }
        Feedback feedback = feedBackMapper.toFeedBack(request);
        return feedBackRepo.save(feedback).getId();
    }

    public PageResponse<FeedBackResponseDto> findAllFeedbacks(int size, int page, Integer bookId, Authentication connectedUser) {

        Pageable pageable = PageRequest.of(page, size);

        User user = ((User) connectedUser.getPrincipal());
        Page<Feedback> feedbacks = feedBackRepo.findAllByBookId(bookId, pageable);
        List<FeedBackResponseDto> feedBackResponse = feedbacks.stream()
                .map(f -> feedBackMapper.toFeedBackResponse(f, user.getId()))
                .toList();
        return new PageResponse<>(
                feedBackResponse,
                feedbacks.getNumber(),
                feedbacks.getSize(),
                feedbacks.getTotalElements(),
                feedbacks.getTotalPages(),
                feedbacks.isFirst(),
                feedbacks.isLast()
        );
    }
}

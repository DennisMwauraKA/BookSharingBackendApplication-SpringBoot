package com.Dennis.BookApp.service;

import com.Dennis.BookApp.dtos.FeedBackResponseDto;
import com.Dennis.BookApp.dtos.FeedbackRequestDto;
import com.Dennis.BookApp.entity.Book;
import com.Dennis.BookApp.entity.Feedback;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Getter
@Setter
@Builder
public class FeedBackMapper {
    public Feedback toFeedBack(@Valid FeedbackRequestDto request) {

        return Feedback.builder()
                .note(request.note())
                .comment(request.comment())
                .book(Book.builder()
                        .id(request.bookId())
                        .archived(false)
                        .shareable(false)
                        .build()
                )

                .build();
    }

    public FeedBackResponseDto toFeedBackResponse(Feedback feedback, Integer id) {
        return FeedBackResponseDto.builder()
                .note(feedback.getNote())
                .comment(feedback.getComment())
                .ownFeedback(Objects.equals(feedback.getCreatedBy(),id))
                .build();
    }
}

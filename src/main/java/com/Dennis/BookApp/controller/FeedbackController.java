package com.Dennis.BookApp.controller;


import com.Dennis.BookApp.common.PageResponse;
import com.Dennis.BookApp.dtos.FeedBackResponseDto;
import com.Dennis.BookApp.dtos.FeedbackRequestDto;
import com.Dennis.BookApp.service.FeedBackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("feedbacks")
@RequiredArgsConstructor
@Tag(name="Feedback")
public class FeedbackController {

    private final FeedBackService feedBackService;


    @PostMapping("/addFeedback")
    public ResponseEntity<Integer> saveFeedback(@Valid @RequestBody FeedbackRequestDto request, Authentication connectedUser) {
        return ResponseEntity.ok(feedBackService.saveFeedBack(request, connectedUser));

    }


    @GetMapping("/book/{book-id}")
    public ResponseEntity<PageResponse<FeedBackResponseDto>> findAllFeedbacks(
            @PathVariable("book-id") Integer bookId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                    @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
                    ) {

return ResponseEntity.ok(feedBackService.findAllFeedbacks(size,page,bookId,connectedUser));
    }


}

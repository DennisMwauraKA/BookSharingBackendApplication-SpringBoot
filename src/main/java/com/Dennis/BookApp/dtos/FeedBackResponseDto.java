package com.Dennis.BookApp.dtos;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FeedBackResponseDto {
    private String comment;
    private Double note;
    private boolean ownFeedback;
}

package com.Dennis.BookApp.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetBookResponseDto {

    private Integer id;
    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private String owner;
    private byte[] cover;
    private double rate; // average of feedbacks
    private boolean archived;
    private boolean shareable;


}

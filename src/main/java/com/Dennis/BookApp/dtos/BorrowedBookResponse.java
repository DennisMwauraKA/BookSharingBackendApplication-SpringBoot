package com.Dennis.BookApp.dtos;


import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowedBookResponse {
    private Integer id;
    private String title;
    private String authorName;
    private String isbn;
    private double rate;
   private boolean returned;
    private boolean returnApproved;
}

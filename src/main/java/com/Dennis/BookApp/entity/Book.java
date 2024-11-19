package com.Dennis.BookApp.entity;

import com.Dennis.BookApp.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Entity
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class Book extends BaseEntity {

    private Integer id;
    private String title;
    private String authorName;
    private boolean archived;
    private boolean shareable;
    private String synopsis;
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "book")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> histories;


    @Transient
    public double getRate() {
        if (feedbacks == null || feedbacks.isEmpty()) {
            return 0.0;
        }
        var rate = this.feedbacks.stream()
                .mapToDouble(Feedback::getNote)
                .average()
                .orElse(0.0);
        return Math.round(rate * 10.0) / 10.0;

    }


}
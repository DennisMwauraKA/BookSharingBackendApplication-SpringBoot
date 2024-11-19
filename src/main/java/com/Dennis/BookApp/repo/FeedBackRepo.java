package com.Dennis.BookApp.repo;

import com.Dennis.BookApp.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedBackRepo extends JpaRepository<Feedback, Integer> {
}

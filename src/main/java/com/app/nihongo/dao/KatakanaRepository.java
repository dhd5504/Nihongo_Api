package com.app.nihongo.dao;

import com.app.nihongo.entity.Katakana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KatakanaRepository extends JpaRepository<Katakana, Integer> {
    List<Katakana> findAllByOrderByDisplayOrderAsc();
}

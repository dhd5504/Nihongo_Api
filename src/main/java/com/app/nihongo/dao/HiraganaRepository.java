package com.app.nihongo.dao;

import com.app.nihongo.entity.Hiragana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HiraganaRepository extends JpaRepository<Hiragana, Integer> {
    List<Hiragana> findAllByOrderByDisplayOrderAsc();
}

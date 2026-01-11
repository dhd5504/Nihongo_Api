package com.app.nihongo.service.hiragana;

import com.app.nihongo.dto.HiraganaDTO;
import com.app.nihongo.dao.HiraganaRepository;
import com.app.nihongo.entity.Hiragana;
import com.app.nihongo.mapper.HiraganaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HiraganaServiceImpl implements HiraganaService {

    @Autowired
    private HiraganaRepository hiraganaRepository;

    @Autowired
    private HiraganaMapper hiraganaMapper;

    @Override
    public List<HiraganaDTO> getAllHiraganaCharacters() {
        return hiraganaRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(hiraganaMapper::toDto)
                .collect(Collectors.toList());
    }
}

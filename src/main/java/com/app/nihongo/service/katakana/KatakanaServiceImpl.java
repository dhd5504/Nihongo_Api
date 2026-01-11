package com.app.nihongo.service.katakana;



import com.app.nihongo.dto.KatakanaDTO;
import com.app.nihongo.dao.KatakanaRepository;

import com.app.nihongo.mapper.KatakanaMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KatakanaServiceImpl implements KatakanaService {

    @Autowired
    private KatakanaRepository katakanaRepository;

    @Autowired
    private KatakanaMapper katakanaMapper;

    @Override
    public List<KatakanaDTO> getAllKatakanaCharacters() {
        return katakanaRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(katakanaMapper::toDto)
                .collect(Collectors.toList());
    }
}


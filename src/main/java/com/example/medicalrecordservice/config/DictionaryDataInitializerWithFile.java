package com.example.medicalrecordservice.config;

import com.example.medicalrecordservice.repository.Mkb10DictionaryRepository;
import com.example.medicalrecordservice.utility.mkb10.parser.Mkb10FileParser;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DictionaryDataInitializerWithFile implements DictionaryDataInitializer {
    private final Mkb10DictionaryRepository mkb10DictionaryRepository;

    private final Mkb10FileParser parser;

    @PostConstruct
    @Transactional(readOnly = true)
    public void initDictionary(){
        if(mkb10DictionaryRepository.count() == 0){
            parser.downloadFile();
            mkb10DictionaryRepository.saveAll(parser.parseFromFile());
        }
    }
}

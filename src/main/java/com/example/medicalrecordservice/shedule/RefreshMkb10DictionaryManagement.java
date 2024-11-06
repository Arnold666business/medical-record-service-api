package com.example.medicalrecordservice.shedule;

import com.example.medicalrecordservice.service.Mkb10DictionaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
@Log4j2
public class RefreshMkb10DictionaryManagement {
    private final static Logger logger = LoggerFactory.getLogger(RefreshMkb10DictionaryManagement.class);

    private final Mkb10DictionaryService mkb10DictionaryService;

    @Scheduled(cron = "${refresh_mkb10_dictionary_interval.cron}",
            zone = "${refresh_mkb10_dictionary_interval.time_zone}")
    @CacheEvict(value = "Mkb10", allEntries = true)
    public void refresh(){
        mkb10DictionaryService.refresh();

        logger.info("ICD-10 dictionary update successfully completed");
    }
}

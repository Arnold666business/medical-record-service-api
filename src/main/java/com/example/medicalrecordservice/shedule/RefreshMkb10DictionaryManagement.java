package com.example.medicalrecordservice.shedule;

import com.example.medicalrecordservice.service.Mkb10DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class RefreshMkb10DictionaryManagement {
    private final Mkb10DictionaryService mkb10DictionaryService;

    @Scheduled(cron = "${REFRESH_MKB_DICTIONARY_INTERVAL_CRON}")
    public void refresh() {
        mkb10DictionaryService.refresh();
    }

}

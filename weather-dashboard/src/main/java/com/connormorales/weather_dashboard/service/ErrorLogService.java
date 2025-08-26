package com.connormorales.weather_dashboard.service;

import com.connormorales.weather_dashboard.entity.ErrorLog;
import com.connormorales.weather_dashboard.repository.ErrorLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ErrorLogService {

    @Autowired
    private ErrorLogRepository errorLogRepository;

    public void logError(Exception ex){
        ErrorLog errorLog = ErrorLog.builder()
        .errorType(ex.getClass().getSimpleName())
                .message(ex.getMessage())
                .stackTrace(ex.toString())
                .timestamp(LocalDateTime.now())
                .build();
        errorLogRepository.save(errorLog);
    }

    private String getStackTraceAsString(Exception ex){
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : ex.getStackTrace()){
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}

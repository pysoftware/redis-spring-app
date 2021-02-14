package com.redisfiledb.demo.repositories;

import com.redisfiledb.demo.enteties.File;

import java.text.ParseException;
import java.util.List;

public interface CustomDbFileRepository {
    List<File> searchFilesByFilters(
            String from,
            String to,
            String fileName,
            String fileExtension
    ) throws ParseException;
}

package com.redisfiledb.demo.redisRepositories;

import com.redisfiledb.demo.redisEnteties.RedisEntityFile;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface CustomRedisFileRepository {
    List<RedisEntityFile> searchFilesByFilters(
            String from,
            String to,
            String fileName,
            String fileExtension
    ) throws ParseException;
}

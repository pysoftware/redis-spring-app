package com.redisfiledb.demo.redisRepositories;


import com.redisfiledb.demo.controllers.ExceptionHandlerController;
import com.redisfiledb.demo.redisEnteties.RedisEntityFile;
import lombok.Getter;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class RedisFileRepositoryImpl implements CustomRedisFileRepository {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @Getter
    private final RedisFileRepository redisFileRepository;

    @Autowired
    public RedisFileRepositoryImpl(@Lazy RedisFileRepository redisFileRepository) {
        this.redisFileRepository = redisFileRepository;
    }

    @Override
    public List<RedisEntityFile> searchFilesByFilters(
            String dateFrom,
            String dateTo,
            String fileName,
            String fileExtension
    ) throws ParseException {
        RedisEntityFile redisEntityFile = new RedisEntityFile();
        redisEntityFile.setFileName(fileName);
        redisEntityFile.setFileExtension(fileExtension);

        Example<RedisEntityFile> example = Example.of(redisEntityFile);
        // We need to retrieve filtered data by defined strings 'cause example cannot filter by date (only strings)
        // below we'll filter data by date with list
        List<RedisEntityFile> redisEntityFileList = (List<RedisEntityFile>) redisFileRepository.findAll(example);

        if (Objects.nonNull(dateFrom) && Objects.nonNull(dateTo)) {
            Date from = DateUtils.parseDate(dateFrom, "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy");
            Date to = DateUtils.parseDate(dateTo, "yyyy-MM-dd HH:mm:ss", "dd/MM/yyyy");

            // Removing inappropriate elements
            redisEntityFileList = redisEntityFileList
                    .stream()
                    .filter(item -> item.getUpdatedDate().after(from) && item.getUpdatedDate().before(to))
                    .collect(Collectors.toList());
        }

        return redisEntityFileList;
    }
}

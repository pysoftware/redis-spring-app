package com.redisfiledb.demo.redisServices;

import com.redisfiledb.demo.controllers.ExceptionHandlerController;
import com.redisfiledb.demo.redisEnteties.RedisEntityFile;
import com.redisfiledb.demo.redisRepositories.RedisFileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RedisService {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    private RedisFileRepository redisFileRepository;

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

        List<RedisEntityFile> redisEntityFileList =
            (List<RedisEntityFile>) redisFileRepository.findAll(example);


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

    public void saveMultipartFile(MultipartFile multipartFile) throws IOException {
        RedisEntityFile file = new RedisEntityFile(
            multipartFile.getOriginalFilename(),
            Objects.requireNonNull(multipartFile.getOriginalFilename()).split("\\.")[1],
            multipartFile.getSize(),
            multipartFile.getBytes(),
            "/redis/download-files?fileName=" + multipartFile.getOriginalFilename()
        );

        redisFileRepository.save(file);
    }

    public RedisEntityFile findRedisEntityFileByFileName(String fileName) {
        Optional<RedisEntityFile> redisEntityFile = redisFileRepository.findRedisEntityFileByFileName(fileName);

        return redisEntityFile.orElse(null);
    }

}

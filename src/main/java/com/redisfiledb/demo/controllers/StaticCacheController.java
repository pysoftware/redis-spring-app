package com.redisfiledb.demo.controllers;

import com.redisfiledb.demo.enteties.File;
import com.redisfiledb.demo.jsonResponses.SimpleJsonResponse;
import com.redisfiledb.demo.redisEnteties.RedisEntityFile;
import com.redisfiledb.demo.utilities.Cache;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/static-cache")
public class StaticCacheController {

    final Cache cache;

    @Autowired
    public StaticCacheController(Cache cache) {
        this.cache = cache;
    }

    @GetMapping(value = "/file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    ResponseEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName) {
        Optional<?> file = (Optional<?>) cache.getCachedItemByValue(fileName);
        return file
            .map(value -> ResponseEntity.ok(((File) value).getFile()))
            .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping(value = "/db-files")
    public ResponseEntity<?> getListOfFilesByFilter(
        String dateFrom,
        String dateTo,
        String fileName,
        String fileExtension
    ) throws ParseException {

        List<File> fileList = new ArrayList<>();
        Stream<Object> cachedItems = cache.getCachedObjects().values().stream();
//            .filter()
//            .filter()

        if (Objects.nonNull(dateFrom) && Objects.nonNull(dateTo)) {
            Date from = DateUtils.parseDate(dateFrom, "yyyy-MM-dd HH:mm:ss", "dd.MM.yyyy");
            Date to = DateUtils.parseDate(dateTo, "yyyy-MM-dd HH:mm:ss", "dd.MM.yyyy");

            cachedItems = cachedItems.filter(o -> {
                if (o instanceof File) {
                    File file = (File) o;
                    return file.getUpdatedDate().before(to) && file.getUpdatedDate().after(from);
                }
                return false;
            });
        }

        if (Objects.nonNull(fileName)) {
            cachedItems = cachedItems.filter(o -> {
                if (o instanceof File) {
                    File file = (File) o;
                    return fileName.matches(file.getFileName());
                }
                return false;
            });
        }

        if (Objects.nonNull(fileExtension)) {
            cachedItems = cachedItems.filter(o -> {
                if (o instanceof File) {
                    File file = (File) o;
                    return fileExtension.matches(file.getFileExtension());
                }
                return false;
            });
        }

        return ResponseEntity
            .ok()
            .body(new SimpleJsonResponse(cachedItems.collect(Collectors.toList())));
    }

}

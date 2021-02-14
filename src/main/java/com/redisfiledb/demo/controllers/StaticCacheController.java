package com.redisfiledb.demo.controllers;

import com.redisfiledb.demo.enteties.File;
import com.redisfiledb.demo.enteties.FormFile;
import com.redisfiledb.demo.jsonResponses.SimpleJsonResponse;
import com.redisfiledb.demo.utilities.Cache;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;
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

    @GetMapping(value = "/download-files", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    ResponseEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName) {
        Object file = cache.getCachedItemByValue(fileName);
        if (file instanceof File) {
            return ResponseEntity.ok(((File) file).getFile());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/files")
    public ResponseEntity<?> getListOfFilesByFilter(
            String dateFrom,
            String dateTo,
            String fileName,
            String fileExtension
    ) throws ParseException {

        Stream<Object> cachedItems = cache.getCachedObjects().values().stream();
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

    @PostMapping(value = "/files", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> file(
            @Valid @ModelAttribute FormFile formFile,
            BindingResult bindingResult
    ) throws BindException, IOException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        MultipartFile multipartFile = formFile.getFile();

        File file = new File(
                multipartFile.getOriginalFilename(),
                Objects.requireNonNull(multipartFile.getOriginalFilename()).split("\\.")[1],
                multipartFile.getSize(),
                multipartFile.getBytes(),
                "/static-cache/download-files?fileName=" + multipartFile.getOriginalFilename()
        );

        cache.addCachableItem(file.getFileName(), file);

        return ResponseEntity.ok().build();
    }
}

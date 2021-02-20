package com.redisfiledb.demo.controllers;

import com.redisfiledb.demo.enteties.File;
import com.redisfiledb.demo.enteties.FormFile;
import com.redisfiledb.demo.jsonResponses.SimpleJsonResponse;
import com.redisfiledb.demo.staticCacheServices.StaticCacheService;
import com.redisfiledb.demo.utilities.Cache;
import lombok.AllArgsConstructor;
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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/static-cache")
@AllArgsConstructor
public class StaticCacheController {

    final Cache cache;
    final StaticCacheService staticCacheService;

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
        List<Object> response = staticCacheService.searchFilesByFilters(
            dateFrom,
            dateTo,
            fileName,
            fileExtension
        );
        return ResponseEntity
            .ok()
            .body(new SimpleJsonResponse(response));
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

        staticCacheService.saveMultipartFile(multipartFile);

        return ResponseEntity.ok().build();
    }
}

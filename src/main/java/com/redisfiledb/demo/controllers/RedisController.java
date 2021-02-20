package com.redisfiledb.demo.controllers;

import com.redisfiledb.demo.enteties.FormFile;
import com.redisfiledb.demo.jsonResponses.SimpleJsonResponse;
import com.redisfiledb.demo.redisEnteties.RedisEntityFile;
import com.redisfiledb.demo.redisRepositories.RedisFileRepository;
import com.redisfiledb.demo.redisServices.RedisService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/redis")
@AllArgsConstructor
public class RedisController {

    final RedisService redisService;

    @GetMapping(value = "/download-files", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    ResponseEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName) {
        RedisEntityFile file = redisService.findRedisEntityFileByFileName(fileName);
        if (Objects.nonNull(file)) {
            return ResponseEntity.ok(file.getFile());
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/files")
    public ResponseEntity<?> getListOfFilesByFilter(
        String dateFrom,
        String dateTo,
        String fileName,
        String fileExtension
    ) throws ParseException {
        List<RedisEntityFile> list = redisService.searchFilesByFilters(
            dateFrom,
            dateTo,
            fileName,
            fileExtension
        );

        return ResponseEntity
            .ok()
            .body(new SimpleJsonResponse(list));
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

        redisService.saveMultipartFile(multipartFile);

        return ResponseEntity.ok().build();
    }
}

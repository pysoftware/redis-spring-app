package com.redisfiledb.demo.controllers;

import com.redisfiledb.demo.enteties.FormFile;
import com.redisfiledb.demo.jsonResponses.SimpleJsonResponse;
import com.redisfiledb.demo.redisEnteties.RedisEntityFile;
import com.redisfiledb.demo.redisRepositories.RedisFileRepository;
import com.redisfiledb.demo.redisRepositories.RedisFileRepositoryImpl;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/redis")
public class RedisController {

    final RedisFileRepository redisFileRepository;
    final RedisFileRepositoryImpl redisFileRepositoryImpl;

    @Autowired
    public RedisController(RedisFileRepository redisFileRepository, RedisFileRepositoryImpl redisFileRepositoryImpl) {
        this.redisFileRepository = redisFileRepository;
        this.redisFileRepositoryImpl = redisFileRepositoryImpl;
    }


    @GetMapping(value = "/download-files", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    ResponseEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName) {
        Optional<RedisEntityFile> file = redisFileRepository.findRedisEntityFileByFileName(fileName);
        return file
                .map(redisEntityFile -> ResponseEntity.ok(redisEntityFile.getFile()))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping(value = "/files")
    public ResponseEntity<?> getListOfFilesByFilter(
            String dateFrom,
            String dateTo,
            String fileName,
            String fileExtension
    ) throws ParseException {
        Iterable<RedisEntityFile> redisEntityFileList = redisFileRepository.findAll();
        System.out.println(redisFileRepository.count());
        redisEntityFileList.forEach(System.out::println);

        List<RedisEntityFile> list = redisFileRepositoryImpl.searchFilesByFilters(
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

        RedisEntityFile file = new RedisEntityFile(
                multipartFile.getOriginalFilename(),
                Objects.requireNonNull(multipartFile.getOriginalFilename()).split("\\.")[1],
                multipartFile.getSize(),
                multipartFile.getBytes(),
                "/redis/download-files?fileName=" + multipartFile.getOriginalFilename()
        );

        redisFileRepository.save(file);

        return ResponseEntity.ok().build();
    }

}

package com.redisfiledb.demo.controllers;

import com.redisfiledb.demo.enteties.File;
import com.redisfiledb.demo.enteties.FormFile;
import com.redisfiledb.demo.jsonResponses.SimpleJsonResponse;
import com.redisfiledb.demo.redisEnteties.RedisEntityFile;
import com.redisfiledb.demo.redisRepositories.RedisFileRepositoryImpl;
import com.redisfiledb.demo.repositories.DbFileRepositoryImpl;
import com.redisfiledb.demo.repositories.FilesRepository;
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
@RequestMapping("/db")
public class DbController {

    final FilesRepository filesRepository;
    final DbFileRepositoryImpl dbFileRepositoryImpl;

    @Autowired
    public DbController(FilesRepository filesRepository, DbFileRepositoryImpl dbFileRepositoryImpl) {
        this.filesRepository = filesRepository;
        this.dbFileRepositoryImpl = dbFileRepositoryImpl;
    }


    @GetMapping(value = "/download-files", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    ResponseEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName) {
        Optional<File> file = filesRepository.findFirstByFileName(fileName);
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

        List<File> list = dbFileRepositoryImpl.searchFilesByFilters(
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

        File file = new File(
            multipartFile.getOriginalFilename(),
            Objects.requireNonNull(multipartFile.getOriginalFilename()).split("\\.")[1],
            multipartFile.getSize(),
            multipartFile.getBytes(),
            "/redis/download-files?fileName=" + multipartFile.getOriginalFilename()
        );

        filesRepository.save(file);

        return ResponseEntity.ok().build();
    }

}

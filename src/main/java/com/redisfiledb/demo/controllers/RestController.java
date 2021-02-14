package com.redisfiledb.demo.controllers;

import com.redisfiledb.demo.enteties.File;
import com.redisfiledb.demo.jsonResponses.SimpleJsonResponse;
import com.redisfiledb.demo.jsonResponses.ValidationErrorResponse;
import com.redisfiledb.demo.redisEnteties.RedisEntityFile;
import com.redisfiledb.demo.redisRepositories.RedisFileRepository;
import com.redisfiledb.demo.redisRepositories.RedisFileRepositoryImpl;
import com.redisfiledb.demo.repositories.FilesRepository;
import com.redisfiledb.demo.redisRepositories.RedisFileRepository;
import com.redisfiledb.demo.utilities.Cache;
import com.redisfiledb.demo.validators.FileSize;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@AllArgsConstructor
@Data
@Validated
class FormFile {
    @NotNull(message = "required field")
    @FileSize(max = 15)
    private MultipartFile file;
}

@org.springframework.web.bind.annotation.RestController
public class RestController {

    final FilesRepository filesRepository;
    final RedisFileRepository redisFileRepository;
    final RedisFileRepositoryImpl redisFileRepositoryImpl;
    final Cache cache;

    @Autowired
    public RestController(
            FilesRepository filesRepository,
            RedisFileRepository redisFileRepository,
            RedisFileRepositoryImpl redisFileRepositoryImpl,
            Cache cache
    ) {
        this.filesRepository = filesRepository;
        this.redisFileRepository = redisFileRepository;
        this.redisFileRepositoryImpl = redisFileRepositoryImpl;
        this.cache = cache;
    }

    @GetMapping(value = "/file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    ResponseEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName) {
        Optional<File> file = filesRepository.findFirstByFileName(fileName);
        return file
                .map(value -> ResponseEntity.ok(value.getFile()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/db-files")
    public ResponseEntity<?> getListOfFilesByFilter(
            String dateFrom,
            String dateTo,
            String fileName,
            String fileExtension
    ) {
        try {
            List<RedisEntityFile> redisEntityFileList = redisFileRepositoryImpl.searchFilesByFilters(
                    dateFrom,
                    dateTo,
                    fileName,
                    fileExtension
            );

            return ResponseEntity.ok().body(new SimpleJsonResponse(redisEntityFileList));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/static-cache-files")
    public ResponseEntity<?> staticCacheFiles() {
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/db-files")
    public ResponseEntity<?> dbFile(
            @Valid File file,
            BindingResult bindingResult
    ) throws IOException, NullPointerException {

        System.out.println(file);

        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(BAD_REQUEST)
                    .body(new ValidationErrorResponse(BAD_REQUEST, bindingResult.getFieldErrors()));
        }
//        MultipartFile multipartFile = formFile.getFile();
//        File file = new File(
//                multipartFile.getOriginalFilename(),
//                Objects.requireNonNull(multipartFile.getOriginalFilename()).split("\\.")[1],
//                multipartFile.getSize(),
//                multipartFile.getBytes()
//        );
//
//        filesRepository.save(file);

        return ResponseEntity.ok().body("ok");
    }

    @PostMapping(value = "/file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> file(
            @RequestParam("file") MultipartFile multipartFile,
            BindingResult bindingResult
    ) throws IOException, NullPointerException {
//        redisFileRepository.findAllUsers(Sort.by().or)
//        cache.addCachableItem();
//        System.out.println(multipartFile.getOriginalFilename().split("\\.")[1]);
//        fileValidator.validate(fileUploadModel, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(BAD_REQUEST)
                    .body(new ValidationErrorResponse(BAD_REQUEST, bindingResult.getFieldErrors()));
        }

        RedisEntityFile file = new RedisEntityFile(
                multipartFile.getOriginalFilename(),
                Objects.requireNonNull(multipartFile.getOriginalFilename()).split("\\.")[1],
                multipartFile.getSize(),
                multipartFile.getBytes()
        );

//        RedisEntityFile file = RedisEntityFile.builder()
//                .file(multipartFile.getBytes())
//                .fileSize(multipartFile.getSize())
//                .fileName(Objects.requireNonNull(multipartFile.getOriginalFilename()).split("\\.")[1])
//                .build();

//        filesRepository.save(file);

//        System.out.println(filesRepository.findById("5").get());
//        File sFile = filesRepository.findById("1").get();

//        File sFile = filesRepository.findFirstByFileName(multipartFile.getOriginalFilename());
//        System.out.println(filesRepository.findAll());
        return ResponseEntity.ok().build();
    }
}

package com.redisfiledb.demo.controllers;

import com.redisfiledb.demo.enteties.File;
import com.redisfiledb.demo.enteties.FormFile;
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
import java.util.Objects;
import java.util.Optional;

@RestController
public class MainRestController {

    final FilesRepository filesRepository;

    @Autowired
    public MainRestController(
            FilesRepository filesRepository
    ) {
        this.filesRepository = filesRepository;
    }

    @GetMapping(value = "/download-files", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    ResponseEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName) {
        Optional<File> file = filesRepository.findFirstByFileName(fileName);
        return file
                .map(value -> ResponseEntity.ok(value.getFile()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/files")
    public ResponseEntity<?> uploadFile(
            @Valid @ModelAttribute FormFile formFile,
            BindingResult bindingResult
    ) throws IOException, NullPointerException, BindException {

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        MultipartFile multipartFile = formFile.getFile();
        File file = new File(
                multipartFile.getOriginalFilename(),
                Objects.requireNonNull(multipartFile.getOriginalFilename()).split("\\.")[1],
                multipartFile.getSize(),
                multipartFile.getBytes(),
                "/db/download-files?fileName=" + multipartFile.getOriginalFilename()
        );

        filesRepository.save(file);

        return ResponseEntity.ok().body("ok");
    }
}

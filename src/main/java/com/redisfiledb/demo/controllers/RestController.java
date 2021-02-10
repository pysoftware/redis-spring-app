package com.redisfiledb.demo.controllers;

import com.redisfiledb.demo.enteties.File;
import com.redisfiledb.demo.enteties.Student;
import com.redisfiledb.demo.repositories.FilesRepository;
import com.redisfiledb.demo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@Controller
public class RestController {

    final StudentRepository studentRepository;
    final FilesRepository filesRepository;

    @Autowired
    public RestController(StudentRepository studentRepository, FilesRepository filesRepository) {
        this.studentRepository = studentRepository;
        this.filesRepository = filesRepository;
    }

    @GetMapping(value = "/test")
    public ResponseEntity<?> test() {
        Student student = new Student(
            "Eng2015001",
            "John Doe",
            Student.Gender.MALE,
            1
        );

        studentRepository.save(student);

        Student retrievedStudent =
            studentRepository.findById("Eng2015001").get();

        return ResponseEntity
            .ok(retrievedStudent);
    }

    @PostMapping(value = "/file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    byte[] file(@RequestParam("file") MultipartFile multipartFile) throws IOException {
//        File file = new File(
//            "3",
//            new Date(),
//            new Date(),
//            multipartFile.getOriginalFilename(),
//            multipartFile.getContentType(),
//            multipartFile.getSize(),
//            multipartFile.getBytes()
//        );
////
//        filesRepository.save(file);

        System.out.println(filesRepository.findById("3").get());
//        File sFile = filesRepository.findById("1").get();

//        File sFile = filesRepository.findFirstByFileName(multipartFile.getOriginalFilename());
//        System.out.println(filesRepository.findAll());
//        return sFile.getFile();
//        return sFile.getFile();
        return new byte[2];
    }
}

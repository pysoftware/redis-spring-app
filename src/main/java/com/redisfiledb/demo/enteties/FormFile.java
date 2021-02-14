package com.redisfiledb.demo.enteties;

import com.redisfiledb.demo.validators.FileSize;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Class for validating file from request
 */
@AllArgsConstructor
@Data
public class FormFile {
    @NotNull
    @FileSize(max = 15)
    private MultipartFile file;

    @NotNull
    private String name;
}

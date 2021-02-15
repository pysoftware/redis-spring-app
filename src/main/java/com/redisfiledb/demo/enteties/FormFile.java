package com.redisfiledb.demo.enteties;

import com.redisfiledb.demo.validators.FileExtension;
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
    @FileExtension(regexp = "md|xls|doc|txt|pdf")
    private MultipartFile file;
}

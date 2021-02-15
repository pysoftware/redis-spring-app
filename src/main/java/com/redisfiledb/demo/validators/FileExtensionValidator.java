package com.redisfiledb.demo.validators;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class FileExtensionValidator implements ConstraintValidator<FileExtension, MultipartFile> {

    private String regexp;

    @Override
    public void initialize(FileExtension constraintAnnotation) {
        regexp = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1].matches(regexp);
    }
}

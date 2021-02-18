package com.redisfiledb.demo.dbServices;

import com.redisfiledb.demo.enteties.File;
import com.redisfiledb.demo.repositories.FilesRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DbFileService {

    private FilesRepository filesRepository;

    public void saveMultipartFile(MultipartFile multipartFile) throws IOException {

        File file = new File(
            multipartFile.getOriginalFilename(),
            Objects.requireNonNull(multipartFile.getOriginalFilename()).split("\\.")[1],
            multipartFile.getSize(),
            multipartFile.getBytes(),
            "/db/download-files?fileName=" + multipartFile.getOriginalFilename()
        );

        filesRepository.save(file);

    }

    public File findFileByFileName(String fileName) {
        Optional<File> optionalFile = filesRepository.findFirstByFileName(fileName);

        return optionalFile.orElse(null);
    }

    public List<File> searchFilesByFilters(
        String dateFrom,
        String dateTo,
        String fileName,
        String fileExtension
    ) throws ParseException {
        return filesRepository.searchFilesByFilters(dateFrom, dateTo, fileName, fileExtension);
    }
}

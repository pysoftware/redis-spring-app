package com.redisfiledb.demo.staticCacheServices;

import com.redisfiledb.demo.enteties.File;
import com.redisfiledb.demo.redisEnteties.RedisEntityFile;
import com.redisfiledb.demo.utilities.Cache;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class StaticCacheService {

    private final Cache cache;

    public List<Object> searchFilesByFilters(
        String dateFrom,
        String dateTo,
        String fileName,
        String fileExtension
    ) throws ParseException {
        Stream<Object> cachedItems = cache.getCachedObjects().values().stream();
        if (Objects.nonNull(dateFrom) && Objects.nonNull(dateTo)) {
            Date from = DateUtils.parseDate(dateFrom, "yyyy-MM-dd HH:mm:ss", "dd.MM.yyyy");
            Date to = DateUtils.parseDate(dateTo, "yyyy-MM-dd HH:mm:ss", "dd.MM.yyyy");

            cachedItems = cachedItems.filter(o -> {
                if (o instanceof File) {
                    File file = (File) o;
                    return file.getUpdatedDate().before(to) && file.getUpdatedDate().after(from);
                }
                return false;
            });
        }

        if (Objects.nonNull(fileName)) {
            cachedItems = cachedItems.filter(o -> {
                if (o instanceof File) {
                    File file = (File) o;
                    return fileName.matches(file.getFileName());
                }
                return false;
            });
        }

        if (Objects.nonNull(fileExtension)) {
            cachedItems = cachedItems.filter(o -> {
                if (o instanceof File) {
                    File file = (File) o;
                    return fileExtension.matches(file.getFileExtension());
                }
                return false;
            });
        }
        return cachedItems.collect(Collectors.toList());
    }

    public void saveMultipartFile(MultipartFile multipartFile) throws IOException {

        File file = new File(
            multipartFile.getOriginalFilename(),
            Objects.requireNonNull(multipartFile.getOriginalFilename()).split("\\.")[1],
            multipartFile.getSize(),
            multipartFile.getBytes(),
            "/static-cache/download-files?fileName=" + multipartFile.getOriginalFilename()
        );

        cache.addCachableItem(file.getFileName(), file);
    }

}

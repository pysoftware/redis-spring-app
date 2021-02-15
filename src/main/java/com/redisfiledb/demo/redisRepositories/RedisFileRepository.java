package com.redisfiledb.demo.redisRepositories;

import com.redisfiledb.demo.redisEnteties.RedisEntityFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;
import java.util.Optional;

public interface RedisFileRepository extends
    CrudRepository<RedisEntityFile, Long>,
    QueryByExampleExecutor<RedisEntityFile> {

    List<RedisEntityFile> findByFileExtensionOrFileName(String fileExtension, String fileName);

    Optional<RedisEntityFile> findRedisEntityFileByFileName(String fileName);

}

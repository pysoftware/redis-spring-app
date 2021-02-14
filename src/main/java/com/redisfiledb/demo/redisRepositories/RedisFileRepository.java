package com.redisfiledb.demo.redisRepositories;

import com.redisfiledb.demo.redisEnteties.RedisEntityFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RedisFileRepository extends CrudRepository<RedisEntityFile, Long> {
    Optional<RedisEntityFile> findRedisEntityFileByFileName(String fileName);
}

package com.redisfiledb.demo.redisRepositories;

import com.redisfiledb.demo.redisEnteties.RedisEntityFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisFileRepository extends CrudRepository<RedisEntityFile, Long> {

}

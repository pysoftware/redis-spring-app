package com.redisfiledb.demo.redisEnteties;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Data
public abstract class RedisBaseEntity {

    @Id
    @Indexed
    private Long id;

    @CreatedDate
    private Date createdDate = new Date();

    @LastModifiedDate
    private Date updatedDate = new Date();
}

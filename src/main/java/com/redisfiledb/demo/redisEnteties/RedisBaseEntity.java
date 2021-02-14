package com.redisfiledb.demo.redisEnteties;

import lombok.Data;
import org.hibernate.annotations.Index;
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
    @Column(name = "created_at")
    private final Date createdDate = new Date();

    @LastModifiedDate
    @Column(name = "updated_at")
    private final Date updatedDate = new Date();
}

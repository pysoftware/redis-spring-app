package com.redisfiledb.demo.redisEnteties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@RedisHash("File")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RedisEntityFile extends RedisBaseEntity {

    @Indexed
    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file")
    private byte[] file;
}

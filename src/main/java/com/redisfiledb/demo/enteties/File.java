package com.redisfiledb.demo.enteties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@RedisHash("File")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {

    @Id
    @Indexed
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @CreatedDate
    @Column(name = "created_at")
    private Date createdDate;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updatedDate;

    @Indexed
    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file")
    private byte[] file;
}

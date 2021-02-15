package com.redisfiledb.demo.redisEnteties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@RedisHash("File")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"file"})
public class RedisEntityFile extends RedisBaseEntity implements Serializable {

    @Indexed
    private String fileName;

    @Indexed
    private String fileExtension;

    private Long fileSize;

    private byte[] file;

    private String downloadLink;

    public String getFileSize() {
        return Math.ceil((double) fileSize / 1024.0 / 1024.0) + "MB";
    }

    @Override
    public String toString() {
        return "File{" +
            "fileName='" + fileName + '\'' +
            ", fileExtension='" + fileExtension + '\'' +
            ", fileSize=" + fileSize +
            ", downloadLink='" + downloadLink + '\'' +
            '}';
    }
}

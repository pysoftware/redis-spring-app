package com.redisfiledb.demo.enteties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@JsonIgnoreProperties({"file"})
public class File extends BaseEntity {

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file")
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

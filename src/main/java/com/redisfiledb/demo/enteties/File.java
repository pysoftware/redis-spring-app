package com.redisfiledb.demo.enteties;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "files")
@JsonIgnoreProperties({"file"})
public class File extends BaseEntity {

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "file_size")
    private Long fileSize;

    @Lob
    @Column(name = "file", columnDefinition = "BLOB")
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

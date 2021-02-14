package com.redisfiledb.demo.enteties;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Data
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @CreatedDate
    @Column(name = "created_at")
    protected final Date createdDate = new Date();

    @LastModifiedDate
    @Column(name = "updated_at")
    protected final Date updatedDate = new Date();

}

package com.redisfiledb.demo.repositories;

import com.redisfiledb.demo.enteties.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesRepository extends KeyValueRepository<File, String> {

    File findFirstByFileName(String fileName);

}

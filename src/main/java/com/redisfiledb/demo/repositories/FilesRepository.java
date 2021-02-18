package com.redisfiledb.demo.repositories;

import com.redisfiledb.demo.enteties.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilesRepository extends CrudRepository<File, Long>, CustomDbFileRepository {

    Optional<File> findFirstByFileName(String fileName);

}

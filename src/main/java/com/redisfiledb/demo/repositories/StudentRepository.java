package com.redisfiledb.demo.repositories;

import com.redisfiledb.demo.enteties.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, String> {}

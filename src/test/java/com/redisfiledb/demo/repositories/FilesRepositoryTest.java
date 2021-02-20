package com.redisfiledb.demo.repositories;

import com.redisfiledb.demo.configurations.RedisConfiguration;
import com.redisfiledb.demo.enteties.File;
import com.redisfiledb.demo.redisRepositories.RedisFileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FilesRepositoryTest {


    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private FilesRepository filesRepository;

    @Test
    public void findByFirstNameTest() {

        File file = new File(
            "fileName",
            "fileExt",
            1L,
            new byte[0],
            "link"
        );

        file = testEntityManager.persist(file);

        assertNotNull(filesRepository.findFirstByFileName(file.getFileName()));
    }
}

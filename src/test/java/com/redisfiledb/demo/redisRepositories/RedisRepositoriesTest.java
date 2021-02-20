package com.redisfiledb.demo.redisRepositories;

import com.redisfiledb.demo.repositories.FilesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestConfiguration
@ComponentScan(
    basePackages = "com.redisfiledb.demo.redisRepositories"
)
class RedisRepositoriesContextConfiguration {
//    @MockBean
//    private EntityManager entityManager;
//    @Bean
//    public RedisFileRepositoryImpl redisFileRepositoryImpl(Ent)
}

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RedisRepositoriesContextConfiguration.class)
@DataRedisTest(
//    excludeFilters = @ComponentScan.Filter(
//        type = FilterType.ANNOTATION,
//        classes = {
//            FilesRepository.class
//        }
//    ),
//    excludeAutoConfiguration = {
//        JpaRepositoriesAutoConfiguration.class,
//        RedisRepositoriesAutoConfiguration.class
//    }
)
//@TestPropertySource(properties = {
//    "spring.data.jpa.repositories.enabled=false"
//})
public class RedisRepositoriesTest {
    @Autowired
    private RedisFileRepository filesRepository;

    @Test
    public void test() {
        assertNotNull(true);
    }

}

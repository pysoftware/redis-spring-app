package com.redisfiledb.demo.repositories;


import com.redisfiledb.demo.enteties.File;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class FilesRepositoryImpl implements CustomDbFileRepository {

    private final EntityManager entityManager;

    @Override
    public List<File> searchFilesByFilters(
        String dateFrom,
        String dateTo,
        String fileName,
        String fileExtension
    ) throws ParseException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<File> query = criteriaBuilder.createQuery(File.class);

        Root<File> file = query.from(File.class);
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(dateFrom) && Objects.nonNull(dateTo)) {
            Date from = DateUtils.parseDate(dateFrom, "yyyy-MM-dd HH:mm:ss", "dd.MM.yyyy");
            Date to = DateUtils.parseDate(dateTo, "yyyy-MM-dd HH:mm:ss", "dd.MM.yyyy");

            predicates.add(criteriaBuilder.between(file.get("updatedDate"), from, to));
        }

        if (Objects.nonNull(fileName)) {
            predicates.add(criteriaBuilder.like(file.get("fileName"), "%" + fileName + "%"));
        }

        if (Objects.nonNull(fileExtension)) {
            predicates.add(criteriaBuilder.equal(file.get("fileExtension"), fileExtension));
        }

        query.select(file);
        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
}

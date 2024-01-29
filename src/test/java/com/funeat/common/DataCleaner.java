package com.funeat.common;

import java.util.ArrayList;
import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class DataCleaner {

    private static final String FOREIGN_KEY_CHECK_FORMAT = "SET REFERENTIAL_INTEGRITY %s";
    private static final String TRUNCATE_FORMAT = "TRUNCATE TABLE %s";
    private static final String AUTO_INCREMENT_FORMAT = "ALTER TABLE %s ALTER COLUMN id RESTART WITH 1";

    private final List<String> tableNames = new ArrayList<>();

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void findDatabaseTableNames() {
        List<Object[]> tableInfos = entityManager.createNativeQuery("SHOW TABLES").getResultList();
        for (Object[] tableInfo : tableInfos) {
            String tableName = (String) tableInfo[0];
            tableNames.add(tableName);
        }
    }

    @Transactional
    public void clear() {
        entityManager.clear();
        truncate();
    }

    private void truncate() {
        entityManager.createNativeQuery(String.format(FOREIGN_KEY_CHECK_FORMAT, "FALSE")).executeUpdate();
        for (String tableName : tableNames) {
            if (tableName.equals("SPRING_SESSION") || tableName.equals("SPRING_SESSION_ATTRIBUTES")) {
                entityManager.createNativeQuery(String.format(TRUNCATE_FORMAT, tableName)).executeUpdate();
            } else {
                entityManager.createNativeQuery(String.format(TRUNCATE_FORMAT, tableName)).executeUpdate();
                entityManager.createNativeQuery(String.format(AUTO_INCREMENT_FORMAT, tableName)).executeUpdate();
            }
        }
        entityManager.createNativeQuery(String.format(FOREIGN_KEY_CHECK_FORMAT, "TRUE")).executeUpdate();
    }
}

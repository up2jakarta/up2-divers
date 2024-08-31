package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.entities.batch.JobErrorEntity;
import io.github.up2jakarta.csv.entities.batch.JobExecutionEntity;
import io.github.up2jakarta.csv.entities.batch.JobInstanceEntity;
import io.github.up2jakarta.csv.entities.input.*;
import io.github.up2jakarta.csv.entities.params.SystemEntity;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.mapping.Column;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HibernateTest {

    private static void addEntityClass(MetadataSources metadata) {
        metadata.addAnnotatedClasses(
                InputFileEntity.class, InputFooterEntity.class, InputHeaderEntity.class,
                JobExecutionEntity.class, JobInstanceEntity.class, JobErrorEntity.class,
                InputLoadingEntity.class, InputLoadingErrorEntity.class,
                InputRowEntity.class, InputRowErrorEntity.class,
                SystemEntity.class
        );
    }

    private static void createSchema() {
        MetadataSources metadata = getMetadataSources();
        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setHaltOnError(true);
        schemaExport.setFormat(false);
        schemaExport.setDelimiter(";");
        var md = metadata.buildMetadata();
        {
            var se = md.getEntityBinding(SystemEntity.class.getName());
            System.out.println(se.getTable().getName());
            //print("ID::" +se.getIdentifierProperty().getName(), se.getIdentifier().getColumns());
            for (var c : se.getTable().getColumns()) {
                System.out.println(c.getName() + " -> " + c.getTypeName());
            }
            /*for(var c : se.getProperties()) {
                print(c.getName(), c.getColumns());
            }*/
        }
        //schemaExport.execute(EnumSet.of(STDOUT), CREATE, md);
    }

    private static void print(String prefix, List<Column> columns) {
        var sb = new StringBuilder();
        String[] cs = columns.stream().map(Column::getName).toArray(String[]::new);
        for (var c : columns) {
            sb.append(',').append(c.getName());
        }
        System.out.println(prefix + " -> " + Arrays.toString(cs));
    }

    private static MetadataSources getMetadataSources() {
        MetadataSources metadata = new MetadataSources(
                new StandardServiceRegistryBuilder()
                        .applySettings(getSettings())
                        .build());
        addEntityClass(metadata);
        return metadata;
    }

    private static Map<String, Object> getSettings() {
        Map<String, Object> settings = new HashMap<>();
        settings.put(AvailableSettings.JAKARTA_JDBC_DRIVER, org.h2.Driver.class.getName());
        settings.put(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:h2:~/test;CIPHER=AES");
        settings.put(AvailableSettings.JAKARTA_JDBC_USER, "sa");
        settings.put(AvailableSettings.JAKARTA_JDBC_PASSWORD, "pwd_file pwd_user");
        return settings;
    }

    @Test
    public void generate() {
        createSchema();
    }

}

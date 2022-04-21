package com.myapp.repository.rowmapper;

import com.myapp.domain.Teacher;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Teacher}, with proper type conversions.
 */
@Service
public class TeacherRowMapper implements BiFunction<Row, String, Teacher> {

    private final ColumnConverter converter;

    public TeacherRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Teacher} stored in the database.
     */
    @Override
    public Teacher apply(Row row, String prefix) {
        Teacher entity = new Teacher();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setFirstName(converter.fromRow(row, prefix + "_first_name", String.class));
        entity.setLastName(converter.fromRow(row, prefix + "_last_name", String.class));
        entity.setEmail(converter.fromRow(row, prefix + "_email", String.class));
        return entity;
    }
}

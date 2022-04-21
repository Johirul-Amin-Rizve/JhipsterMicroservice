package com.myapp.repository;

import com.myapp.domain.Teacher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data SQL reactive repository for the Teacher entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeacherRepository extends ReactiveCrudRepository<Teacher, Long>, TeacherRepositoryInternal {
    Flux<Teacher> findAllBy(Pageable pageable);

    @Override
    <S extends Teacher> Mono<S> save(S entity);

    @Override
    Flux<Teacher> findAll();

    @Override
    Mono<Teacher> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface TeacherRepositoryInternal {
    <S extends Teacher> Mono<S> save(S entity);

    Flux<Teacher> findAllBy(Pageable pageable);

    Flux<Teacher> findAll();

    Mono<Teacher> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Teacher> findAllBy(Pageable pageable, Criteria criteria);

}

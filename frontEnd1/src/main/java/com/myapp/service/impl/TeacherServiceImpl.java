package com.myapp.service.impl;

import com.myapp.domain.Teacher;
import com.myapp.repository.TeacherRepository;
import com.myapp.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Teacher}.
 */
@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private final Logger log = LoggerFactory.getLogger(TeacherServiceImpl.class);

    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Mono<Teacher> save(Teacher teacher) {
        log.debug("Request to save Teacher : {}", teacher);
        return teacherRepository.save(teacher);
    }

    @Override
    public Mono<Teacher> update(Teacher teacher) {
        log.debug("Request to save Teacher : {}", teacher);
        return teacherRepository.save(teacher);
    }

    @Override
    public Mono<Teacher> partialUpdate(Teacher teacher) {
        log.debug("Request to partially update Teacher : {}", teacher);

        return teacherRepository
            .findById(teacher.getId())
            .map(existingTeacher -> {
                if (teacher.getFirstName() != null) {
                    existingTeacher.setFirstName(teacher.getFirstName());
                }
                if (teacher.getLastName() != null) {
                    existingTeacher.setLastName(teacher.getLastName());
                }
                if (teacher.getEmail() != null) {
                    existingTeacher.setEmail(teacher.getEmail());
                }

                return existingTeacher;
            })
            .flatMap(teacherRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Teacher> findAll(Pageable pageable) {
        log.debug("Request to get all Teachers");
        return teacherRepository.findAllBy(pageable);
    }

    public Mono<Long> countAll() {
        return teacherRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Teacher> findOne(Long id) {
        log.debug("Request to get Teacher : {}", id);
        return teacherRepository.findById(id);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Teacher : {}", id);
        return teacherRepository.deleteById(id);
    }
}

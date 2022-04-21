package com.myapp.service.impl;

import com.myapp.domain.Student;
import com.myapp.repository.StudentRepository;
import com.myapp.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Student}.
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Mono<Student> save(Student student) {
        log.debug("Request to save Student : {}", student);
        return studentRepository.save(student);
    }

    @Override
    public Mono<Student> update(Student student) {
        log.debug("Request to save Student : {}", student);
        return studentRepository.save(student);
    }

    @Override
    public Mono<Student> partialUpdate(Student student) {
        log.debug("Request to partially update Student : {}", student);

        return studentRepository
            .findById(student.getId())
            .map(existingStudent -> {
                if (student.getFirstName() != null) {
                    existingStudent.setFirstName(student.getFirstName());
                }
                if (student.getLastName() != null) {
                    existingStudent.setLastName(student.getLastName());
                }
                if (student.getEmail() != null) {
                    existingStudent.setEmail(student.getEmail());
                }

                return existingStudent;
            })
            .flatMap(studentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Student> findAll(Pageable pageable) {
        log.debug("Request to get all Students");
        return studentRepository.findAllBy(pageable);
    }

    public Mono<Long> countAll() {
        return studentRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Student> findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        return studentRepository.findById(id);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Student : {}", id);
        return studentRepository.deleteById(id);
    }
}

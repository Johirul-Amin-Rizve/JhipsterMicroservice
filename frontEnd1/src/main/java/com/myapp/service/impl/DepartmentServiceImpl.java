package com.myapp.service.impl;

import com.myapp.domain.Department;
import com.myapp.repository.DepartmentRepository;
import com.myapp.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link Department}.
 */
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final Logger log = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Mono<Department> save(Department department) {
        log.debug("Request to save Department : {}", department);
        return departmentRepository.save(department);
    }

    @Override
    public Mono<Department> update(Department department) {
        log.debug("Request to save Department : {}", department);
        return departmentRepository.save(department);
    }

    @Override
    public Mono<Department> partialUpdate(Department department) {
        log.debug("Request to partially update Department : {}", department);

        return departmentRepository
            .findById(department.getId())
            .map(existingDepartment -> {
                if (department.getName() != null) {
                    existingDepartment.setName(department.getName());
                }
                if (department.getAddress() != null) {
                    existingDepartment.setAddress(department.getAddress());
                }

                return existingDepartment;
            })
            .flatMap(departmentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Department> findAll(Pageable pageable) {
        log.debug("Request to get all Departments");
        return departmentRepository.findAllBy(pageable);
    }

    public Mono<Long> countAll() {
        return departmentRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Department> findOne(Long id) {
        log.debug("Request to get Department : {}", id);
        return departmentRepository.findById(id);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Department : {}", id);
        return departmentRepository.deleteById(id);
    }
}

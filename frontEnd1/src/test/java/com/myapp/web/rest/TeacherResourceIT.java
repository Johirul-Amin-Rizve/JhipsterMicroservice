package com.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.myapp.IntegrationTest;
import com.myapp.domain.Teacher;
import com.myapp.repository.EntityManager;
import com.myapp.repository.TeacherRepository;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link TeacherResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class TeacherResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/teachers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Teacher teacher;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Teacher createEntity(EntityManager em) {
        Teacher teacher = new Teacher().firstName(DEFAULT_FIRST_NAME).lastName(DEFAULT_LAST_NAME).email(DEFAULT_EMAIL);
        return teacher;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Teacher createUpdatedEntity(EntityManager em) {
        Teacher teacher = new Teacher().firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME).email(UPDATED_EMAIL);
        return teacher;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Teacher.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        teacher = createEntity(em);
    }

    @Test
    void createTeacher() throws Exception {
        int databaseSizeBeforeCreate = teacherRepository.findAll().collectList().block().size();
        // Create the Teacher
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(teacher))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll().collectList().block();
        assertThat(teacherList).hasSize(databaseSizeBeforeCreate + 1);
        Teacher testTeacher = teacherList.get(teacherList.size() - 1);
        assertThat(testTeacher.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testTeacher.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testTeacher.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    void createTeacherWithExistingId() throws Exception {
        // Create the Teacher with an existing ID
        teacher.setId(1L);

        int databaseSizeBeforeCreate = teacherRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(teacher))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll().collectList().block();
        assertThat(teacherList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllTeachers() {
        // Initialize the database
        teacherRepository.save(teacher).block();

        // Get all the teacherList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(teacher.getId().intValue()))
            .jsonPath("$.[*].firstName")
            .value(hasItem(DEFAULT_FIRST_NAME))
            .jsonPath("$.[*].lastName")
            .value(hasItem(DEFAULT_LAST_NAME))
            .jsonPath("$.[*].email")
            .value(hasItem(DEFAULT_EMAIL));
    }

    @Test
    void getTeacher() {
        // Initialize the database
        teacherRepository.save(teacher).block();

        // Get the teacher
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, teacher.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(teacher.getId().intValue()))
            .jsonPath("$.firstName")
            .value(is(DEFAULT_FIRST_NAME))
            .jsonPath("$.lastName")
            .value(is(DEFAULT_LAST_NAME))
            .jsonPath("$.email")
            .value(is(DEFAULT_EMAIL));
    }

    @Test
    void getNonExistingTeacher() {
        // Get the teacher
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putNewTeacher() throws Exception {
        // Initialize the database
        teacherRepository.save(teacher).block();

        int databaseSizeBeforeUpdate = teacherRepository.findAll().collectList().block().size();

        // Update the teacher
        Teacher updatedTeacher = teacherRepository.findById(teacher.getId()).block();
        updatedTeacher.firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME).email(UPDATED_EMAIL);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, updatedTeacher.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(updatedTeacher))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll().collectList().block();
        assertThat(teacherList).hasSize(databaseSizeBeforeUpdate);
        Teacher testTeacher = teacherList.get(teacherList.size() - 1);
        assertThat(testTeacher.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testTeacher.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testTeacher.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    void putNonExistingTeacher() throws Exception {
        int databaseSizeBeforeUpdate = teacherRepository.findAll().collectList().block().size();
        teacher.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, teacher.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(teacher))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll().collectList().block();
        assertThat(teacherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchTeacher() throws Exception {
        int databaseSizeBeforeUpdate = teacherRepository.findAll().collectList().block().size();
        teacher.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(teacher))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll().collectList().block();
        assertThat(teacherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamTeacher() throws Exception {
        int databaseSizeBeforeUpdate = teacherRepository.findAll().collectList().block().size();
        teacher.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(teacher))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll().collectList().block();
        assertThat(teacherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateTeacherWithPatch() throws Exception {
        // Initialize the database
        teacherRepository.save(teacher).block();

        int databaseSizeBeforeUpdate = teacherRepository.findAll().collectList().block().size();

        // Update the teacher using partial update
        Teacher partialUpdatedTeacher = new Teacher();
        partialUpdatedTeacher.setId(teacher.getId());

        partialUpdatedTeacher.firstName(UPDATED_FIRST_NAME).email(UPDATED_EMAIL);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTeacher.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedTeacher))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll().collectList().block();
        assertThat(teacherList).hasSize(databaseSizeBeforeUpdate);
        Teacher testTeacher = teacherList.get(teacherList.size() - 1);
        assertThat(testTeacher.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testTeacher.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testTeacher.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    void fullUpdateTeacherWithPatch() throws Exception {
        // Initialize the database
        teacherRepository.save(teacher).block();

        int databaseSizeBeforeUpdate = teacherRepository.findAll().collectList().block().size();

        // Update the teacher using partial update
        Teacher partialUpdatedTeacher = new Teacher();
        partialUpdatedTeacher.setId(teacher.getId());

        partialUpdatedTeacher.firstName(UPDATED_FIRST_NAME).lastName(UPDATED_LAST_NAME).email(UPDATED_EMAIL);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedTeacher.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedTeacher))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll().collectList().block();
        assertThat(teacherList).hasSize(databaseSizeBeforeUpdate);
        Teacher testTeacher = teacherList.get(teacherList.size() - 1);
        assertThat(testTeacher.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testTeacher.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testTeacher.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    void patchNonExistingTeacher() throws Exception {
        int databaseSizeBeforeUpdate = teacherRepository.findAll().collectList().block().size();
        teacher.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, teacher.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(teacher))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll().collectList().block();
        assertThat(teacherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchTeacher() throws Exception {
        int databaseSizeBeforeUpdate = teacherRepository.findAll().collectList().block().size();
        teacher.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, count.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(teacher))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll().collectList().block();
        assertThat(teacherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamTeacher() throws Exception {
        int databaseSizeBeforeUpdate = teacherRepository.findAll().collectList().block().size();
        teacher.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(teacher))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll().collectList().block();
        assertThat(teacherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteTeacher() {
        // Initialize the database
        teacherRepository.save(teacher).block();

        int databaseSizeBeforeDelete = teacherRepository.findAll().collectList().block().size();

        // Delete the teacher
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, teacher.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Teacher> teacherList = teacherRepository.findAll().collectList().block();
        assertThat(teacherList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

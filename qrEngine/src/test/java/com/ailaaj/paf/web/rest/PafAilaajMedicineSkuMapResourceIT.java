package com.ailaaj.paf.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ailaaj.paf.IntegrationTest;
import com.ailaaj.paf.domain.PafAilaajMedicineSkuMap;
import com.ailaaj.paf.repository.PafAilaajMedicineSkuMapRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PafAilaajMedicineSkuMapResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PafAilaajMedicineSkuMapResourceIT {

    private static final Long DEFAULT_AILAAJ_MEDICINE_ID = 1L;
    private static final Long UPDATED_AILAAJ_MEDICINE_ID = 2L;

    private static final Long DEFAULT_PAF_MEDICINE_ID = 1L;
    private static final Long UPDATED_PAF_MEDICINE_ID = 2L;

    private static final String DEFAULT_PAF_MEDICINE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PAF_MEDICINE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MEDICINE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MEDICINE_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_C_FACTORBRAND = 1L;
    private static final Long UPDATED_C_FACTORBRAND = 2L;

    private static final String DEFAULT_SUPPLIER = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/paf-ailaaj-medicine-sku-maps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PafAilaajMedicineSkuMapRepository pafAilaajMedicineSkuMapRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPafAilaajMedicineSkuMapMockMvc;

    private PafAilaajMedicineSkuMap pafAilaajMedicineSkuMap;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PafAilaajMedicineSkuMap createEntity(EntityManager em) {
        PafAilaajMedicineSkuMap pafAilaajMedicineSkuMap = new PafAilaajMedicineSkuMap()
            .ailaajMedicineId(DEFAULT_AILAAJ_MEDICINE_ID)
            .pafMedicineId(DEFAULT_PAF_MEDICINE_ID)
            .pafMedicineName(DEFAULT_PAF_MEDICINE_NAME)
            .medicineName(DEFAULT_MEDICINE_NAME)
            .cFactorbrand(DEFAULT_C_FACTORBRAND)
            .supplier(DEFAULT_SUPPLIER);
        return pafAilaajMedicineSkuMap;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PafAilaajMedicineSkuMap createUpdatedEntity(EntityManager em) {
        PafAilaajMedicineSkuMap pafAilaajMedicineSkuMap = new PafAilaajMedicineSkuMap()
            .ailaajMedicineId(UPDATED_AILAAJ_MEDICINE_ID)
            .pafMedicineId(UPDATED_PAF_MEDICINE_ID)
            .pafMedicineName(UPDATED_PAF_MEDICINE_NAME)
            .medicineName(UPDATED_MEDICINE_NAME)
            .cFactorbrand(UPDATED_C_FACTORBRAND)
            .supplier(UPDATED_SUPPLIER);
        return pafAilaajMedicineSkuMap;
    }

    @BeforeEach
    public void initTest() {
        pafAilaajMedicineSkuMap = createEntity(em);
    }

    @Test
    @Transactional
    void createPafAilaajMedicineSkuMap() throws Exception {
        int databaseSizeBeforeCreate = pafAilaajMedicineSkuMapRepository.findAll().size();
        // Create the PafAilaajMedicineSkuMap
        restPafAilaajMedicineSkuMapMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pafAilaajMedicineSkuMap))
            )
            .andExpect(status().isCreated());

        // Validate the PafAilaajMedicineSkuMap in the database
        List<PafAilaajMedicineSkuMap> pafAilaajMedicineSkuMapList = pafAilaajMedicineSkuMapRepository.findAll();
        assertThat(pafAilaajMedicineSkuMapList).hasSize(databaseSizeBeforeCreate + 1);
        PafAilaajMedicineSkuMap testPafAilaajMedicineSkuMap = pafAilaajMedicineSkuMapList.get(pafAilaajMedicineSkuMapList.size() - 1);
        assertThat(testPafAilaajMedicineSkuMap.getAilaajMedicineId()).isEqualTo(DEFAULT_AILAAJ_MEDICINE_ID);
        assertThat(testPafAilaajMedicineSkuMap.getPafMedicineId()).isEqualTo(DEFAULT_PAF_MEDICINE_ID);
        assertThat(testPafAilaajMedicineSkuMap.getPafMedicineName()).isEqualTo(DEFAULT_PAF_MEDICINE_NAME);
        assertThat(testPafAilaajMedicineSkuMap.getMedicineName()).isEqualTo(DEFAULT_MEDICINE_NAME);
        assertThat(testPafAilaajMedicineSkuMap.getcFactorbrand()).isEqualTo(DEFAULT_C_FACTORBRAND);
        assertThat(testPafAilaajMedicineSkuMap.getSupplier()).isEqualTo(DEFAULT_SUPPLIER);
    }

    @Test
    @Transactional
    void createPafAilaajMedicineSkuMapWithExistingId() throws Exception {
        // Create the PafAilaajMedicineSkuMap with an existing ID
        pafAilaajMedicineSkuMap.setId(1L);

        int databaseSizeBeforeCreate = pafAilaajMedicineSkuMapRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPafAilaajMedicineSkuMapMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pafAilaajMedicineSkuMap))
            )
            .andExpect(status().isBadRequest());

        // Validate the PafAilaajMedicineSkuMap in the database
        List<PafAilaajMedicineSkuMap> pafAilaajMedicineSkuMapList = pafAilaajMedicineSkuMapRepository.findAll();
        assertThat(pafAilaajMedicineSkuMapList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPafAilaajMedicineSkuMaps() throws Exception {
        // Initialize the database
        pafAilaajMedicineSkuMapRepository.saveAndFlush(pafAilaajMedicineSkuMap);

        // Get all the pafAilaajMedicineSkuMapList
        restPafAilaajMedicineSkuMapMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pafAilaajMedicineSkuMap.getId().intValue())))
            .andExpect(jsonPath("$.[*].ailaajMedicineId").value(hasItem(DEFAULT_AILAAJ_MEDICINE_ID.intValue())))
            .andExpect(jsonPath("$.[*].pafMedicineId").value(hasItem(DEFAULT_PAF_MEDICINE_ID.intValue())))
            .andExpect(jsonPath("$.[*].pafMedicineName").value(hasItem(DEFAULT_PAF_MEDICINE_NAME)))
            .andExpect(jsonPath("$.[*].medicineName").value(hasItem(DEFAULT_MEDICINE_NAME)))
            .andExpect(jsonPath("$.[*].cFactorbrand").value(hasItem(DEFAULT_C_FACTORBRAND.intValue())))
            .andExpect(jsonPath("$.[*].supplier").value(hasItem(DEFAULT_SUPPLIER)));
    }

    @Test
    @Transactional
    void getPafAilaajMedicineSkuMap() throws Exception {
        // Initialize the database
        pafAilaajMedicineSkuMapRepository.saveAndFlush(pafAilaajMedicineSkuMap);

        // Get the pafAilaajMedicineSkuMap
        restPafAilaajMedicineSkuMapMockMvc
            .perform(get(ENTITY_API_URL_ID, pafAilaajMedicineSkuMap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pafAilaajMedicineSkuMap.getId().intValue()))
            .andExpect(jsonPath("$.ailaajMedicineId").value(DEFAULT_AILAAJ_MEDICINE_ID.intValue()))
            .andExpect(jsonPath("$.pafMedicineId").value(DEFAULT_PAF_MEDICINE_ID.intValue()))
            .andExpect(jsonPath("$.pafMedicineName").value(DEFAULT_PAF_MEDICINE_NAME))
            .andExpect(jsonPath("$.medicineName").value(DEFAULT_MEDICINE_NAME))
            .andExpect(jsonPath("$.cFactorbrand").value(DEFAULT_C_FACTORBRAND.intValue()))
            .andExpect(jsonPath("$.supplier").value(DEFAULT_SUPPLIER));
    }

    @Test
    @Transactional
    void getNonExistingPafAilaajMedicineSkuMap() throws Exception {
        // Get the pafAilaajMedicineSkuMap
        restPafAilaajMedicineSkuMapMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPafAilaajMedicineSkuMap() throws Exception {
        // Initialize the database
        pafAilaajMedicineSkuMapRepository.saveAndFlush(pafAilaajMedicineSkuMap);

        int databaseSizeBeforeUpdate = pafAilaajMedicineSkuMapRepository.findAll().size();

        // Update the pafAilaajMedicineSkuMap
        PafAilaajMedicineSkuMap updatedPafAilaajMedicineSkuMap = pafAilaajMedicineSkuMapRepository
            .findById(pafAilaajMedicineSkuMap.getId())
            .get();
        // Disconnect from session so that the updates on updatedPafAilaajMedicineSkuMap are not directly saved in db
        em.detach(updatedPafAilaajMedicineSkuMap);
        updatedPafAilaajMedicineSkuMap
            .ailaajMedicineId(UPDATED_AILAAJ_MEDICINE_ID)
            .pafMedicineId(UPDATED_PAF_MEDICINE_ID)
            .pafMedicineName(UPDATED_PAF_MEDICINE_NAME)
            .medicineName(UPDATED_MEDICINE_NAME)
            .cFactorbrand(UPDATED_C_FACTORBRAND)
            .supplier(UPDATED_SUPPLIER);

        restPafAilaajMedicineSkuMapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPafAilaajMedicineSkuMap.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPafAilaajMedicineSkuMap))
            )
            .andExpect(status().isOk());

        // Validate the PafAilaajMedicineSkuMap in the database
        List<PafAilaajMedicineSkuMap> pafAilaajMedicineSkuMapList = pafAilaajMedicineSkuMapRepository.findAll();
        assertThat(pafAilaajMedicineSkuMapList).hasSize(databaseSizeBeforeUpdate);
        PafAilaajMedicineSkuMap testPafAilaajMedicineSkuMap = pafAilaajMedicineSkuMapList.get(pafAilaajMedicineSkuMapList.size() - 1);
        assertThat(testPafAilaajMedicineSkuMap.getAilaajMedicineId()).isEqualTo(UPDATED_AILAAJ_MEDICINE_ID);
        assertThat(testPafAilaajMedicineSkuMap.getPafMedicineId()).isEqualTo(UPDATED_PAF_MEDICINE_ID);
        assertThat(testPafAilaajMedicineSkuMap.getPafMedicineName()).isEqualTo(UPDATED_PAF_MEDICINE_NAME);
        assertThat(testPafAilaajMedicineSkuMap.getMedicineName()).isEqualTo(UPDATED_MEDICINE_NAME);
        assertThat(testPafAilaajMedicineSkuMap.getcFactorbrand()).isEqualTo(UPDATED_C_FACTORBRAND);
        assertThat(testPafAilaajMedicineSkuMap.getSupplier()).isEqualTo(UPDATED_SUPPLIER);
    }

    @Test
    @Transactional
    void putNonExistingPafAilaajMedicineSkuMap() throws Exception {
        int databaseSizeBeforeUpdate = pafAilaajMedicineSkuMapRepository.findAll().size();
        pafAilaajMedicineSkuMap.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPafAilaajMedicineSkuMapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pafAilaajMedicineSkuMap.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pafAilaajMedicineSkuMap))
            )
            .andExpect(status().isBadRequest());

        // Validate the PafAilaajMedicineSkuMap in the database
        List<PafAilaajMedicineSkuMap> pafAilaajMedicineSkuMapList = pafAilaajMedicineSkuMapRepository.findAll();
        assertThat(pafAilaajMedicineSkuMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPafAilaajMedicineSkuMap() throws Exception {
        int databaseSizeBeforeUpdate = pafAilaajMedicineSkuMapRepository.findAll().size();
        pafAilaajMedicineSkuMap.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPafAilaajMedicineSkuMapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pafAilaajMedicineSkuMap))
            )
            .andExpect(status().isBadRequest());

        // Validate the PafAilaajMedicineSkuMap in the database
        List<PafAilaajMedicineSkuMap> pafAilaajMedicineSkuMapList = pafAilaajMedicineSkuMapRepository.findAll();
        assertThat(pafAilaajMedicineSkuMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPafAilaajMedicineSkuMap() throws Exception {
        int databaseSizeBeforeUpdate = pafAilaajMedicineSkuMapRepository.findAll().size();
        pafAilaajMedicineSkuMap.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPafAilaajMedicineSkuMapMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pafAilaajMedicineSkuMap))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PafAilaajMedicineSkuMap in the database
        List<PafAilaajMedicineSkuMap> pafAilaajMedicineSkuMapList = pafAilaajMedicineSkuMapRepository.findAll();
        assertThat(pafAilaajMedicineSkuMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePafAilaajMedicineSkuMapWithPatch() throws Exception {
        // Initialize the database
        pafAilaajMedicineSkuMapRepository.saveAndFlush(pafAilaajMedicineSkuMap);

        int databaseSizeBeforeUpdate = pafAilaajMedicineSkuMapRepository.findAll().size();

        // Update the pafAilaajMedicineSkuMap using partial update
        PafAilaajMedicineSkuMap partialUpdatedPafAilaajMedicineSkuMap = new PafAilaajMedicineSkuMap();
        partialUpdatedPafAilaajMedicineSkuMap.setId(pafAilaajMedicineSkuMap.getId());

        partialUpdatedPafAilaajMedicineSkuMap
            .pafMedicineName(UPDATED_PAF_MEDICINE_NAME)
            .medicineName(UPDATED_MEDICINE_NAME)
            .supplier(UPDATED_SUPPLIER);

        restPafAilaajMedicineSkuMapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPafAilaajMedicineSkuMap.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPafAilaajMedicineSkuMap))
            )
            .andExpect(status().isOk());

        // Validate the PafAilaajMedicineSkuMap in the database
        List<PafAilaajMedicineSkuMap> pafAilaajMedicineSkuMapList = pafAilaajMedicineSkuMapRepository.findAll();
        assertThat(pafAilaajMedicineSkuMapList).hasSize(databaseSizeBeforeUpdate);
        PafAilaajMedicineSkuMap testPafAilaajMedicineSkuMap = pafAilaajMedicineSkuMapList.get(pafAilaajMedicineSkuMapList.size() - 1);
        assertThat(testPafAilaajMedicineSkuMap.getAilaajMedicineId()).isEqualTo(DEFAULT_AILAAJ_MEDICINE_ID);
        assertThat(testPafAilaajMedicineSkuMap.getPafMedicineId()).isEqualTo(DEFAULT_PAF_MEDICINE_ID);
        assertThat(testPafAilaajMedicineSkuMap.getPafMedicineName()).isEqualTo(UPDATED_PAF_MEDICINE_NAME);
        assertThat(testPafAilaajMedicineSkuMap.getMedicineName()).isEqualTo(UPDATED_MEDICINE_NAME);
        assertThat(testPafAilaajMedicineSkuMap.getcFactorbrand()).isEqualTo(DEFAULT_C_FACTORBRAND);
        assertThat(testPafAilaajMedicineSkuMap.getSupplier()).isEqualTo(UPDATED_SUPPLIER);
    }

    @Test
    @Transactional
    void fullUpdatePafAilaajMedicineSkuMapWithPatch() throws Exception {
        // Initialize the database
        pafAilaajMedicineSkuMapRepository.saveAndFlush(pafAilaajMedicineSkuMap);

        int databaseSizeBeforeUpdate = pafAilaajMedicineSkuMapRepository.findAll().size();

        // Update the pafAilaajMedicineSkuMap using partial update
        PafAilaajMedicineSkuMap partialUpdatedPafAilaajMedicineSkuMap = new PafAilaajMedicineSkuMap();
        partialUpdatedPafAilaajMedicineSkuMap.setId(pafAilaajMedicineSkuMap.getId());

        partialUpdatedPafAilaajMedicineSkuMap
            .ailaajMedicineId(UPDATED_AILAAJ_MEDICINE_ID)
            .pafMedicineId(UPDATED_PAF_MEDICINE_ID)
            .pafMedicineName(UPDATED_PAF_MEDICINE_NAME)
            .medicineName(UPDATED_MEDICINE_NAME)
            .cFactorbrand(UPDATED_C_FACTORBRAND)
            .supplier(UPDATED_SUPPLIER);

        restPafAilaajMedicineSkuMapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPafAilaajMedicineSkuMap.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPafAilaajMedicineSkuMap))
            )
            .andExpect(status().isOk());

        // Validate the PafAilaajMedicineSkuMap in the database
        List<PafAilaajMedicineSkuMap> pafAilaajMedicineSkuMapList = pafAilaajMedicineSkuMapRepository.findAll();
        assertThat(pafAilaajMedicineSkuMapList).hasSize(databaseSizeBeforeUpdate);
        PafAilaajMedicineSkuMap testPafAilaajMedicineSkuMap = pafAilaajMedicineSkuMapList.get(pafAilaajMedicineSkuMapList.size() - 1);
        assertThat(testPafAilaajMedicineSkuMap.getAilaajMedicineId()).isEqualTo(UPDATED_AILAAJ_MEDICINE_ID);
        assertThat(testPafAilaajMedicineSkuMap.getPafMedicineId()).isEqualTo(UPDATED_PAF_MEDICINE_ID);
        assertThat(testPafAilaajMedicineSkuMap.getPafMedicineName()).isEqualTo(UPDATED_PAF_MEDICINE_NAME);
        assertThat(testPafAilaajMedicineSkuMap.getMedicineName()).isEqualTo(UPDATED_MEDICINE_NAME);
        assertThat(testPafAilaajMedicineSkuMap.getcFactorbrand()).isEqualTo(UPDATED_C_FACTORBRAND);
        assertThat(testPafAilaajMedicineSkuMap.getSupplier()).isEqualTo(UPDATED_SUPPLIER);
    }

    @Test
    @Transactional
    void patchNonExistingPafAilaajMedicineSkuMap() throws Exception {
        int databaseSizeBeforeUpdate = pafAilaajMedicineSkuMapRepository.findAll().size();
        pafAilaajMedicineSkuMap.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPafAilaajMedicineSkuMapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pafAilaajMedicineSkuMap.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pafAilaajMedicineSkuMap))
            )
            .andExpect(status().isBadRequest());

        // Validate the PafAilaajMedicineSkuMap in the database
        List<PafAilaajMedicineSkuMap> pafAilaajMedicineSkuMapList = pafAilaajMedicineSkuMapRepository.findAll();
        assertThat(pafAilaajMedicineSkuMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPafAilaajMedicineSkuMap() throws Exception {
        int databaseSizeBeforeUpdate = pafAilaajMedicineSkuMapRepository.findAll().size();
        pafAilaajMedicineSkuMap.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPafAilaajMedicineSkuMapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pafAilaajMedicineSkuMap))
            )
            .andExpect(status().isBadRequest());

        // Validate the PafAilaajMedicineSkuMap in the database
        List<PafAilaajMedicineSkuMap> pafAilaajMedicineSkuMapList = pafAilaajMedicineSkuMapRepository.findAll();
        assertThat(pafAilaajMedicineSkuMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPafAilaajMedicineSkuMap() throws Exception {
        int databaseSizeBeforeUpdate = pafAilaajMedicineSkuMapRepository.findAll().size();
        pafAilaajMedicineSkuMap.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPafAilaajMedicineSkuMapMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pafAilaajMedicineSkuMap))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PafAilaajMedicineSkuMap in the database
        List<PafAilaajMedicineSkuMap> pafAilaajMedicineSkuMapList = pafAilaajMedicineSkuMapRepository.findAll();
        assertThat(pafAilaajMedicineSkuMapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePafAilaajMedicineSkuMap() throws Exception {
        // Initialize the database
        pafAilaajMedicineSkuMapRepository.saveAndFlush(pafAilaajMedicineSkuMap);

        int databaseSizeBeforeDelete = pafAilaajMedicineSkuMapRepository.findAll().size();

        // Delete the pafAilaajMedicineSkuMap
        restPafAilaajMedicineSkuMapMockMvc
            .perform(delete(ENTITY_API_URL_ID, pafAilaajMedicineSkuMap.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PafAilaajMedicineSkuMap> pafAilaajMedicineSkuMapList = pafAilaajMedicineSkuMapRepository.findAll();
        assertThat(pafAilaajMedicineSkuMapList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

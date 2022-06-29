package com.ailaaj.paf.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ailaaj.paf.IntegrationTest;
import com.ailaaj.paf.domain.DemanInfo;
import com.ailaaj.paf.repository.DemanInfoRepository;
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
 * Integration tests for the {@link DemanInfoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DemanInfoResourceIT {

    private static final String DEFAULT_DEMAND_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEMAND_ID = "BBBBBBBBBB";

    private static final String DEFAULT_DEMAND_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_DEMAND_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_STATUS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/deman-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemanInfoRepository demanInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemanInfoMockMvc;

    private DemanInfo demanInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemanInfo createEntity(EntityManager em) {
        DemanInfo demanInfo = new DemanInfo()
            .demandId(DEFAULT_DEMAND_ID)
            .demandStatus(DEFAULT_DEMAND_STATUS)
            .invoiceStatus(DEFAULT_INVOICE_STATUS);
        return demanInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemanInfo createUpdatedEntity(EntityManager em) {
        DemanInfo demanInfo = new DemanInfo()
            .demandId(UPDATED_DEMAND_ID)
            .demandStatus(UPDATED_DEMAND_STATUS)
            .invoiceStatus(UPDATED_INVOICE_STATUS);
        return demanInfo;
    }

    @BeforeEach
    public void initTest() {
        demanInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createDemanInfo() throws Exception {
        int databaseSizeBeforeCreate = demanInfoRepository.findAll().size();
        // Create the DemanInfo
        restDemanInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demanInfo)))
            .andExpect(status().isCreated());

        // Validate the DemanInfo in the database
        List<DemanInfo> demanInfoList = demanInfoRepository.findAll();
        assertThat(demanInfoList).hasSize(databaseSizeBeforeCreate + 1);
        DemanInfo testDemanInfo = demanInfoList.get(demanInfoList.size() - 1);
        assertThat(testDemanInfo.getDemandId()).isEqualTo(DEFAULT_DEMAND_ID);
        assertThat(testDemanInfo.getDemandStatus()).isEqualTo(DEFAULT_DEMAND_STATUS);
        assertThat(testDemanInfo.getInvoiceStatus()).isEqualTo(DEFAULT_INVOICE_STATUS);
    }

    @Test
    @Transactional
    void createDemanInfoWithExistingId() throws Exception {
        // Create the DemanInfo with an existing ID
        demanInfo.setId(1L);

        int databaseSizeBeforeCreate = demanInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemanInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demanInfo)))
            .andExpect(status().isBadRequest());

        // Validate the DemanInfo in the database
        List<DemanInfo> demanInfoList = demanInfoRepository.findAll();
        assertThat(demanInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDemanInfos() throws Exception {
        // Initialize the database
        demanInfoRepository.saveAndFlush(demanInfo);

        // Get all the demanInfoList
        restDemanInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demanInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].demandId").value(hasItem(DEFAULT_DEMAND_ID)))
            .andExpect(jsonPath("$.[*].demandStatus").value(hasItem(DEFAULT_DEMAND_STATUS)))
            .andExpect(jsonPath("$.[*].invoiceStatus").value(hasItem(DEFAULT_INVOICE_STATUS)));
    }

    @Test
    @Transactional
    void getDemanInfo() throws Exception {
        // Initialize the database
        demanInfoRepository.saveAndFlush(demanInfo);

        // Get the demanInfo
        restDemanInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, demanInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demanInfo.getId().intValue()))
            .andExpect(jsonPath("$.demandId").value(DEFAULT_DEMAND_ID))
            .andExpect(jsonPath("$.demandStatus").value(DEFAULT_DEMAND_STATUS))
            .andExpect(jsonPath("$.invoiceStatus").value(DEFAULT_INVOICE_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingDemanInfo() throws Exception {
        // Get the demanInfo
        restDemanInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDemanInfo() throws Exception {
        // Initialize the database
        demanInfoRepository.saveAndFlush(demanInfo);

        int databaseSizeBeforeUpdate = demanInfoRepository.findAll().size();

        // Update the demanInfo
        DemanInfo updatedDemanInfo = demanInfoRepository.findById(demanInfo.getId()).get();
        // Disconnect from session so that the updates on updatedDemanInfo are not directly saved in db
        em.detach(updatedDemanInfo);
        updatedDemanInfo.demandId(UPDATED_DEMAND_ID).demandStatus(UPDATED_DEMAND_STATUS).invoiceStatus(UPDATED_INVOICE_STATUS);

        restDemanInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDemanInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDemanInfo))
            )
            .andExpect(status().isOk());

        // Validate the DemanInfo in the database
        List<DemanInfo> demanInfoList = demanInfoRepository.findAll();
        assertThat(demanInfoList).hasSize(databaseSizeBeforeUpdate);
        DemanInfo testDemanInfo = demanInfoList.get(demanInfoList.size() - 1);
        assertThat(testDemanInfo.getDemandId()).isEqualTo(UPDATED_DEMAND_ID);
        assertThat(testDemanInfo.getDemandStatus()).isEqualTo(UPDATED_DEMAND_STATUS);
        assertThat(testDemanInfo.getInvoiceStatus()).isEqualTo(UPDATED_INVOICE_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingDemanInfo() throws Exception {
        int databaseSizeBeforeUpdate = demanInfoRepository.findAll().size();
        demanInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemanInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demanInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demanInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemanInfo in the database
        List<DemanInfo> demanInfoList = demanInfoRepository.findAll();
        assertThat(demanInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemanInfo() throws Exception {
        int databaseSizeBeforeUpdate = demanInfoRepository.findAll().size();
        demanInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemanInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demanInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemanInfo in the database
        List<DemanInfo> demanInfoList = demanInfoRepository.findAll();
        assertThat(demanInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemanInfo() throws Exception {
        int databaseSizeBeforeUpdate = demanInfoRepository.findAll().size();
        demanInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemanInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demanInfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemanInfo in the database
        List<DemanInfo> demanInfoList = demanInfoRepository.findAll();
        assertThat(demanInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemanInfoWithPatch() throws Exception {
        // Initialize the database
        demanInfoRepository.saveAndFlush(demanInfo);

        int databaseSizeBeforeUpdate = demanInfoRepository.findAll().size();

        // Update the demanInfo using partial update
        DemanInfo partialUpdatedDemanInfo = new DemanInfo();
        partialUpdatedDemanInfo.setId(demanInfo.getId());

        partialUpdatedDemanInfo.invoiceStatus(UPDATED_INVOICE_STATUS);

        restDemanInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemanInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemanInfo))
            )
            .andExpect(status().isOk());

        // Validate the DemanInfo in the database
        List<DemanInfo> demanInfoList = demanInfoRepository.findAll();
        assertThat(demanInfoList).hasSize(databaseSizeBeforeUpdate);
        DemanInfo testDemanInfo = demanInfoList.get(demanInfoList.size() - 1);
        assertThat(testDemanInfo.getDemandId()).isEqualTo(DEFAULT_DEMAND_ID);
        assertThat(testDemanInfo.getDemandStatus()).isEqualTo(DEFAULT_DEMAND_STATUS);
        assertThat(testDemanInfo.getInvoiceStatus()).isEqualTo(UPDATED_INVOICE_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateDemanInfoWithPatch() throws Exception {
        // Initialize the database
        demanInfoRepository.saveAndFlush(demanInfo);

        int databaseSizeBeforeUpdate = demanInfoRepository.findAll().size();

        // Update the demanInfo using partial update
        DemanInfo partialUpdatedDemanInfo = new DemanInfo();
        partialUpdatedDemanInfo.setId(demanInfo.getId());

        partialUpdatedDemanInfo.demandId(UPDATED_DEMAND_ID).demandStatus(UPDATED_DEMAND_STATUS).invoiceStatus(UPDATED_INVOICE_STATUS);

        restDemanInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemanInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemanInfo))
            )
            .andExpect(status().isOk());

        // Validate the DemanInfo in the database
        List<DemanInfo> demanInfoList = demanInfoRepository.findAll();
        assertThat(demanInfoList).hasSize(databaseSizeBeforeUpdate);
        DemanInfo testDemanInfo = demanInfoList.get(demanInfoList.size() - 1);
        assertThat(testDemanInfo.getDemandId()).isEqualTo(UPDATED_DEMAND_ID);
        assertThat(testDemanInfo.getDemandStatus()).isEqualTo(UPDATED_DEMAND_STATUS);
        assertThat(testDemanInfo.getInvoiceStatus()).isEqualTo(UPDATED_INVOICE_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingDemanInfo() throws Exception {
        int databaseSizeBeforeUpdate = demanInfoRepository.findAll().size();
        demanInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemanInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demanInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demanInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemanInfo in the database
        List<DemanInfo> demanInfoList = demanInfoRepository.findAll();
        assertThat(demanInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemanInfo() throws Exception {
        int databaseSizeBeforeUpdate = demanInfoRepository.findAll().size();
        demanInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemanInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demanInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemanInfo in the database
        List<DemanInfo> demanInfoList = demanInfoRepository.findAll();
        assertThat(demanInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemanInfo() throws Exception {
        int databaseSizeBeforeUpdate = demanInfoRepository.findAll().size();
        demanInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemanInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(demanInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemanInfo in the database
        List<DemanInfo> demanInfoList = demanInfoRepository.findAll();
        assertThat(demanInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemanInfo() throws Exception {
        // Initialize the database
        demanInfoRepository.saveAndFlush(demanInfo);

        int databaseSizeBeforeDelete = demanInfoRepository.findAll().size();

        // Delete the demanInfo
        restDemanInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, demanInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DemanInfo> demanInfoList = demanInfoRepository.findAll();
        assertThat(demanInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

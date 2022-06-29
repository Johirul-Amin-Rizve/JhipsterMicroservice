package com.ailaaj.paf.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ailaaj.paf.IntegrationTest;
import com.ailaaj.paf.domain.Demand;
import com.ailaaj.paf.repository.DemandRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link DemandResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DemandResourceIT {

    private static final String DEFAULT_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_HOSPITAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HOSPITAL_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/demands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemandRepository demandRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandMockMvc;

    private Demand demand;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Demand createEntity(EntityManager em) {
        Demand demand = new Demand()
            .orderId(DEFAULT_ORDER_ID)
            .orderStatus(DEFAULT_ORDER_STATUS)
            .invoiceStatus(DEFAULT_INVOICE_STATUS)
            .hospitalName(DEFAULT_HOSPITAL_NAME)
            .createdDate(DEFAULT_CREATED_DATE);
        return demand;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Demand createUpdatedEntity(EntityManager em) {
        Demand demand = new Demand()
            .orderId(UPDATED_ORDER_ID)
            .orderStatus(UPDATED_ORDER_STATUS)
            .invoiceStatus(UPDATED_INVOICE_STATUS)
            .hospitalName(UPDATED_HOSPITAL_NAME)
            .createdDate(UPDATED_CREATED_DATE);
        return demand;
    }

    @BeforeEach
    public void initTest() {
        demand = createEntity(em);
    }

    @Test
    @Transactional
    void createDemand() throws Exception {
        int databaseSizeBeforeCreate = demandRepository.findAll().size();
        // Create the Demand
        restDemandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demand)))
            .andExpect(status().isCreated());

        // Validate the Demand in the database
        List<Demand> demandList = demandRepository.findAll();
        assertThat(demandList).hasSize(databaseSizeBeforeCreate + 1);
        Demand testDemand = demandList.get(demandList.size() - 1);
        assertThat(testDemand.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testDemand.getOrderStatus()).isEqualTo(DEFAULT_ORDER_STATUS);
        assertThat(testDemand.getInvoiceStatus()).isEqualTo(DEFAULT_INVOICE_STATUS);
        assertThat(testDemand.getHospitalName()).isEqualTo(DEFAULT_HOSPITAL_NAME);
        assertThat(testDemand.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createDemandWithExistingId() throws Exception {
        // Create the Demand with an existing ID
        demand.setId(1L);

        int databaseSizeBeforeCreate = demandRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demand)))
            .andExpect(status().isBadRequest());

        // Validate the Demand in the database
        List<Demand> demandList = demandRepository.findAll();
        assertThat(demandList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDemands() throws Exception {
        // Initialize the database
        demandRepository.saveAndFlush(demand);

        // Get all the demandList
        restDemandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demand.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS)))
            .andExpect(jsonPath("$.[*].invoiceStatus").value(hasItem(DEFAULT_INVOICE_STATUS)))
            .andExpect(jsonPath("$.[*].hospitalName").value(hasItem(DEFAULT_HOSPITAL_NAME)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getDemand() throws Exception {
        // Initialize the database
        demandRepository.saveAndFlush(demand);

        // Get the demand
        restDemandMockMvc
            .perform(get(ENTITY_API_URL_ID, demand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demand.getId().intValue()))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID))
            .andExpect(jsonPath("$.orderStatus").value(DEFAULT_ORDER_STATUS))
            .andExpect(jsonPath("$.invoiceStatus").value(DEFAULT_INVOICE_STATUS))
            .andExpect(jsonPath("$.hospitalName").value(DEFAULT_HOSPITAL_NAME))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDemand() throws Exception {
        // Get the demand
        restDemandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDemand() throws Exception {
        // Initialize the database
        demandRepository.saveAndFlush(demand);

        int databaseSizeBeforeUpdate = demandRepository.findAll().size();

        // Update the demand
        Demand updatedDemand = demandRepository.findById(demand.getId()).get();
        // Disconnect from session so that the updates on updatedDemand are not directly saved in db
        em.detach(updatedDemand);
        updatedDemand
            .orderId(UPDATED_ORDER_ID)
            .orderStatus(UPDATED_ORDER_STATUS)
            .invoiceStatus(UPDATED_INVOICE_STATUS)
            .hospitalName(UPDATED_HOSPITAL_NAME)
            .createdDate(UPDATED_CREATED_DATE);

        restDemandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDemand.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDemand))
            )
            .andExpect(status().isOk());

        // Validate the Demand in the database
        List<Demand> demandList = demandRepository.findAll();
        assertThat(demandList).hasSize(databaseSizeBeforeUpdate);
        Demand testDemand = demandList.get(demandList.size() - 1);
        assertThat(testDemand.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testDemand.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testDemand.getInvoiceStatus()).isEqualTo(UPDATED_INVOICE_STATUS);
        assertThat(testDemand.getHospitalName()).isEqualTo(UPDATED_HOSPITAL_NAME);
        assertThat(testDemand.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingDemand() throws Exception {
        int databaseSizeBeforeUpdate = demandRepository.findAll().size();
        demand.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demand.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demand in the database
        List<Demand> demandList = demandRepository.findAll();
        assertThat(demandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemand() throws Exception {
        int databaseSizeBeforeUpdate = demandRepository.findAll().size();
        demand.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demand in the database
        List<Demand> demandList = demandRepository.findAll();
        assertThat(demandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemand() throws Exception {
        int databaseSizeBeforeUpdate = demandRepository.findAll().size();
        demand.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demand)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Demand in the database
        List<Demand> demandList = demandRepository.findAll();
        assertThat(demandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandWithPatch() throws Exception {
        // Initialize the database
        demandRepository.saveAndFlush(demand);

        int databaseSizeBeforeUpdate = demandRepository.findAll().size();

        // Update the demand using partial update
        Demand partialUpdatedDemand = new Demand();
        partialUpdatedDemand.setId(demand.getId());

        partialUpdatedDemand.invoiceStatus(UPDATED_INVOICE_STATUS);

        restDemandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemand))
            )
            .andExpect(status().isOk());

        // Validate the Demand in the database
        List<Demand> demandList = demandRepository.findAll();
        assertThat(demandList).hasSize(databaseSizeBeforeUpdate);
        Demand testDemand = demandList.get(demandList.size() - 1);
        assertThat(testDemand.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testDemand.getOrderStatus()).isEqualTo(DEFAULT_ORDER_STATUS);
        assertThat(testDemand.getInvoiceStatus()).isEqualTo(UPDATED_INVOICE_STATUS);
        assertThat(testDemand.getHospitalName()).isEqualTo(DEFAULT_HOSPITAL_NAME);
        assertThat(testDemand.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateDemandWithPatch() throws Exception {
        // Initialize the database
        demandRepository.saveAndFlush(demand);

        int databaseSizeBeforeUpdate = demandRepository.findAll().size();

        // Update the demand using partial update
        Demand partialUpdatedDemand = new Demand();
        partialUpdatedDemand.setId(demand.getId());

        partialUpdatedDemand
            .orderId(UPDATED_ORDER_ID)
            .orderStatus(UPDATED_ORDER_STATUS)
            .invoiceStatus(UPDATED_INVOICE_STATUS)
            .hospitalName(UPDATED_HOSPITAL_NAME)
            .createdDate(UPDATED_CREATED_DATE);

        restDemandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemand))
            )
            .andExpect(status().isOk());

        // Validate the Demand in the database
        List<Demand> demandList = demandRepository.findAll();
        assertThat(demandList).hasSize(databaseSizeBeforeUpdate);
        Demand testDemand = demandList.get(demandList.size() - 1);
        assertThat(testDemand.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testDemand.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testDemand.getInvoiceStatus()).isEqualTo(UPDATED_INVOICE_STATUS);
        assertThat(testDemand.getHospitalName()).isEqualTo(UPDATED_HOSPITAL_NAME);
        assertThat(testDemand.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingDemand() throws Exception {
        int databaseSizeBeforeUpdate = demandRepository.findAll().size();
        demand.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demand in the database
        List<Demand> demandList = demandRepository.findAll();
        assertThat(demandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemand() throws Exception {
        int databaseSizeBeforeUpdate = demandRepository.findAll().size();
        demand.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demand))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demand in the database
        List<Demand> demandList = demandRepository.findAll();
        assertThat(demandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemand() throws Exception {
        int databaseSizeBeforeUpdate = demandRepository.findAll().size();
        demand.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(demand)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Demand in the database
        List<Demand> demandList = demandRepository.findAll();
        assertThat(demandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemand() throws Exception {
        // Initialize the database
        demandRepository.saveAndFlush(demand);

        int databaseSizeBeforeDelete = demandRepository.findAll().size();

        // Delete the demand
        restDemandMockMvc
            .perform(delete(ENTITY_API_URL_ID, demand.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Demand> demandList = demandRepository.findAll();
        assertThat(demandList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

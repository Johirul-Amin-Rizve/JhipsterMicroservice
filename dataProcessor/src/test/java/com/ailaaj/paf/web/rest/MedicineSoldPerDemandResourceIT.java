package com.ailaaj.paf.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ailaaj.paf.IntegrationTest;
import com.ailaaj.paf.domain.MedicineSoldPerDemand;
import com.ailaaj.paf.repository.MedicineSoldPerDemandRepository;
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
 * Integration tests for the {@link MedicineSoldPerDemandResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MedicineSoldPerDemandResourceIT {

    private static final Long DEFAULT_MEDICINE_ID = 1L;
    private static final Long UPDATED_MEDICINE_ID = 2L;

    private static final String DEFAULT_MEDICINE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MEDICINE_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_MEDICINE_ORDER_QUANTITY = 1D;
    private static final Double UPDATED_MEDICINE_ORDER_QUANTITY = 2D;

    private static final Double DEFAULT_UNIT_PRICE = 1D;
    private static final Double UPDATED_UNIT_PRICE = 2D;

    private static final Double DEFAULT_DISCOUNTED_PRICE = 1D;
    private static final Double UPDATED_DISCOUNTED_PRICE = 2D;

    private static final Integer DEFAULT_ISSUED_QUANTITY = 1;
    private static final Integer UPDATED_ISSUED_QUANTITY = 2;

    private static final Integer DEFAULT_RETURN_QUANTITY = 1;
    private static final Integer UPDATED_RETURN_QUANTITY = 2;

    private static final Instant DEFAULT_SELLING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SELLING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/medicine-sold-per-demands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MedicineSoldPerDemandRepository medicineSoldPerDemandRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicineSoldPerDemandMockMvc;

    private MedicineSoldPerDemand medicineSoldPerDemand;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicineSoldPerDemand createEntity(EntityManager em) {
        MedicineSoldPerDemand medicineSoldPerDemand = new MedicineSoldPerDemand()
            .medicineId(DEFAULT_MEDICINE_ID)
            .medicineName(DEFAULT_MEDICINE_NAME)
            .medicineOrderQuantity(DEFAULT_MEDICINE_ORDER_QUANTITY)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .discountedPrice(DEFAULT_DISCOUNTED_PRICE)
            .issuedQuantity(DEFAULT_ISSUED_QUANTITY)
            .returnQuantity(DEFAULT_RETURN_QUANTITY)
            .sellingDate(DEFAULT_SELLING_DATE);
        return medicineSoldPerDemand;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicineSoldPerDemand createUpdatedEntity(EntityManager em) {
        MedicineSoldPerDemand medicineSoldPerDemand = new MedicineSoldPerDemand()
            .medicineId(UPDATED_MEDICINE_ID)
            .medicineName(UPDATED_MEDICINE_NAME)
            .medicineOrderQuantity(UPDATED_MEDICINE_ORDER_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .discountedPrice(UPDATED_DISCOUNTED_PRICE)
            .issuedQuantity(UPDATED_ISSUED_QUANTITY)
            .returnQuantity(UPDATED_RETURN_QUANTITY)
            .sellingDate(UPDATED_SELLING_DATE);
        return medicineSoldPerDemand;
    }

    @BeforeEach
    public void initTest() {
        medicineSoldPerDemand = createEntity(em);
    }

    @Test
    @Transactional
    void createMedicineSoldPerDemand() throws Exception {
        int databaseSizeBeforeCreate = medicineSoldPerDemandRepository.findAll().size();
        // Create the MedicineSoldPerDemand
        restMedicineSoldPerDemandMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicineSoldPerDemand))
            )
            .andExpect(status().isCreated());

        // Validate the MedicineSoldPerDemand in the database
        List<MedicineSoldPerDemand> medicineSoldPerDemandList = medicineSoldPerDemandRepository.findAll();
        assertThat(medicineSoldPerDemandList).hasSize(databaseSizeBeforeCreate + 1);
        MedicineSoldPerDemand testMedicineSoldPerDemand = medicineSoldPerDemandList.get(medicineSoldPerDemandList.size() - 1);
        assertThat(testMedicineSoldPerDemand.getMedicineId()).isEqualTo(DEFAULT_MEDICINE_ID);
        assertThat(testMedicineSoldPerDemand.getMedicineName()).isEqualTo(DEFAULT_MEDICINE_NAME);
        assertThat(testMedicineSoldPerDemand.getMedicineOrderQuantity()).isEqualTo(DEFAULT_MEDICINE_ORDER_QUANTITY);
        assertThat(testMedicineSoldPerDemand.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testMedicineSoldPerDemand.getDiscountedPrice()).isEqualTo(DEFAULT_DISCOUNTED_PRICE);
        assertThat(testMedicineSoldPerDemand.getIssuedQuantity()).isEqualTo(DEFAULT_ISSUED_QUANTITY);
        assertThat(testMedicineSoldPerDemand.getReturnQuantity()).isEqualTo(DEFAULT_RETURN_QUANTITY);
        assertThat(testMedicineSoldPerDemand.getSellingDate()).isEqualTo(DEFAULT_SELLING_DATE);
    }

    @Test
    @Transactional
    void createMedicineSoldPerDemandWithExistingId() throws Exception {
        // Create the MedicineSoldPerDemand with an existing ID
        medicineSoldPerDemand.setId(1L);

        int databaseSizeBeforeCreate = medicineSoldPerDemandRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicineSoldPerDemandMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicineSoldPerDemand))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicineSoldPerDemand in the database
        List<MedicineSoldPerDemand> medicineSoldPerDemandList = medicineSoldPerDemandRepository.findAll();
        assertThat(medicineSoldPerDemandList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMedicineSoldPerDemands() throws Exception {
        // Initialize the database
        medicineSoldPerDemandRepository.saveAndFlush(medicineSoldPerDemand);

        // Get all the medicineSoldPerDemandList
        restMedicineSoldPerDemandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicineSoldPerDemand.getId().intValue())))
            .andExpect(jsonPath("$.[*].medicineId").value(hasItem(DEFAULT_MEDICINE_ID.intValue())))
            .andExpect(jsonPath("$.[*].medicineName").value(hasItem(DEFAULT_MEDICINE_NAME)))
            .andExpect(jsonPath("$.[*].medicineOrderQuantity").value(hasItem(DEFAULT_MEDICINE_ORDER_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].discountedPrice").value(hasItem(DEFAULT_DISCOUNTED_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].issuedQuantity").value(hasItem(DEFAULT_ISSUED_QUANTITY)))
            .andExpect(jsonPath("$.[*].returnQuantity").value(hasItem(DEFAULT_RETURN_QUANTITY)))
            .andExpect(jsonPath("$.[*].sellingDate").value(hasItem(DEFAULT_SELLING_DATE.toString())));
    }

    @Test
    @Transactional
    void getMedicineSoldPerDemand() throws Exception {
        // Initialize the database
        medicineSoldPerDemandRepository.saveAndFlush(medicineSoldPerDemand);

        // Get the medicineSoldPerDemand
        restMedicineSoldPerDemandMockMvc
            .perform(get(ENTITY_API_URL_ID, medicineSoldPerDemand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medicineSoldPerDemand.getId().intValue()))
            .andExpect(jsonPath("$.medicineId").value(DEFAULT_MEDICINE_ID.intValue()))
            .andExpect(jsonPath("$.medicineName").value(DEFAULT_MEDICINE_NAME))
            .andExpect(jsonPath("$.medicineOrderQuantity").value(DEFAULT_MEDICINE_ORDER_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.discountedPrice").value(DEFAULT_DISCOUNTED_PRICE.doubleValue()))
            .andExpect(jsonPath("$.issuedQuantity").value(DEFAULT_ISSUED_QUANTITY))
            .andExpect(jsonPath("$.returnQuantity").value(DEFAULT_RETURN_QUANTITY))
            .andExpect(jsonPath("$.sellingDate").value(DEFAULT_SELLING_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMedicineSoldPerDemand() throws Exception {
        // Get the medicineSoldPerDemand
        restMedicineSoldPerDemandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMedicineSoldPerDemand() throws Exception {
        // Initialize the database
        medicineSoldPerDemandRepository.saveAndFlush(medicineSoldPerDemand);

        int databaseSizeBeforeUpdate = medicineSoldPerDemandRepository.findAll().size();

        // Update the medicineSoldPerDemand
        MedicineSoldPerDemand updatedMedicineSoldPerDemand = medicineSoldPerDemandRepository.findById(medicineSoldPerDemand.getId()).get();
        // Disconnect from session so that the updates on updatedMedicineSoldPerDemand are not directly saved in db
        em.detach(updatedMedicineSoldPerDemand);
        updatedMedicineSoldPerDemand
            .medicineId(UPDATED_MEDICINE_ID)
            .medicineName(UPDATED_MEDICINE_NAME)
            .medicineOrderQuantity(UPDATED_MEDICINE_ORDER_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .discountedPrice(UPDATED_DISCOUNTED_PRICE)
            .issuedQuantity(UPDATED_ISSUED_QUANTITY)
            .returnQuantity(UPDATED_RETURN_QUANTITY)
            .sellingDate(UPDATED_SELLING_DATE);

        restMedicineSoldPerDemandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMedicineSoldPerDemand.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMedicineSoldPerDemand))
            )
            .andExpect(status().isOk());

        // Validate the MedicineSoldPerDemand in the database
        List<MedicineSoldPerDemand> medicineSoldPerDemandList = medicineSoldPerDemandRepository.findAll();
        assertThat(medicineSoldPerDemandList).hasSize(databaseSizeBeforeUpdate);
        MedicineSoldPerDemand testMedicineSoldPerDemand = medicineSoldPerDemandList.get(medicineSoldPerDemandList.size() - 1);
        assertThat(testMedicineSoldPerDemand.getMedicineId()).isEqualTo(UPDATED_MEDICINE_ID);
        assertThat(testMedicineSoldPerDemand.getMedicineName()).isEqualTo(UPDATED_MEDICINE_NAME);
        assertThat(testMedicineSoldPerDemand.getMedicineOrderQuantity()).isEqualTo(UPDATED_MEDICINE_ORDER_QUANTITY);
        assertThat(testMedicineSoldPerDemand.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testMedicineSoldPerDemand.getDiscountedPrice()).isEqualTo(UPDATED_DISCOUNTED_PRICE);
        assertThat(testMedicineSoldPerDemand.getIssuedQuantity()).isEqualTo(UPDATED_ISSUED_QUANTITY);
        assertThat(testMedicineSoldPerDemand.getReturnQuantity()).isEqualTo(UPDATED_RETURN_QUANTITY);
        assertThat(testMedicineSoldPerDemand.getSellingDate()).isEqualTo(UPDATED_SELLING_DATE);
    }

    @Test
    @Transactional
    void putNonExistingMedicineSoldPerDemand() throws Exception {
        int databaseSizeBeforeUpdate = medicineSoldPerDemandRepository.findAll().size();
        medicineSoldPerDemand.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicineSoldPerDemandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medicineSoldPerDemand.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicineSoldPerDemand))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicineSoldPerDemand in the database
        List<MedicineSoldPerDemand> medicineSoldPerDemandList = medicineSoldPerDemandRepository.findAll();
        assertThat(medicineSoldPerDemandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMedicineSoldPerDemand() throws Exception {
        int databaseSizeBeforeUpdate = medicineSoldPerDemandRepository.findAll().size();
        medicineSoldPerDemand.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicineSoldPerDemandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicineSoldPerDemand))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicineSoldPerDemand in the database
        List<MedicineSoldPerDemand> medicineSoldPerDemandList = medicineSoldPerDemandRepository.findAll();
        assertThat(medicineSoldPerDemandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMedicineSoldPerDemand() throws Exception {
        int databaseSizeBeforeUpdate = medicineSoldPerDemandRepository.findAll().size();
        medicineSoldPerDemand.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicineSoldPerDemandMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicineSoldPerDemand))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MedicineSoldPerDemand in the database
        List<MedicineSoldPerDemand> medicineSoldPerDemandList = medicineSoldPerDemandRepository.findAll();
        assertThat(medicineSoldPerDemandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMedicineSoldPerDemandWithPatch() throws Exception {
        // Initialize the database
        medicineSoldPerDemandRepository.saveAndFlush(medicineSoldPerDemand);

        int databaseSizeBeforeUpdate = medicineSoldPerDemandRepository.findAll().size();

        // Update the medicineSoldPerDemand using partial update
        MedicineSoldPerDemand partialUpdatedMedicineSoldPerDemand = new MedicineSoldPerDemand();
        partialUpdatedMedicineSoldPerDemand.setId(medicineSoldPerDemand.getId());

        partialUpdatedMedicineSoldPerDemand
            .medicineId(UPDATED_MEDICINE_ID)
            .medicineName(UPDATED_MEDICINE_NAME)
            .discountedPrice(UPDATED_DISCOUNTED_PRICE)
            .issuedQuantity(UPDATED_ISSUED_QUANTITY)
            .sellingDate(UPDATED_SELLING_DATE);

        restMedicineSoldPerDemandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedicineSoldPerDemand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedicineSoldPerDemand))
            )
            .andExpect(status().isOk());

        // Validate the MedicineSoldPerDemand in the database
        List<MedicineSoldPerDemand> medicineSoldPerDemandList = medicineSoldPerDemandRepository.findAll();
        assertThat(medicineSoldPerDemandList).hasSize(databaseSizeBeforeUpdate);
        MedicineSoldPerDemand testMedicineSoldPerDemand = medicineSoldPerDemandList.get(medicineSoldPerDemandList.size() - 1);
        assertThat(testMedicineSoldPerDemand.getMedicineId()).isEqualTo(UPDATED_MEDICINE_ID);
        assertThat(testMedicineSoldPerDemand.getMedicineName()).isEqualTo(UPDATED_MEDICINE_NAME);
        assertThat(testMedicineSoldPerDemand.getMedicineOrderQuantity()).isEqualTo(DEFAULT_MEDICINE_ORDER_QUANTITY);
        assertThat(testMedicineSoldPerDemand.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testMedicineSoldPerDemand.getDiscountedPrice()).isEqualTo(UPDATED_DISCOUNTED_PRICE);
        assertThat(testMedicineSoldPerDemand.getIssuedQuantity()).isEqualTo(UPDATED_ISSUED_QUANTITY);
        assertThat(testMedicineSoldPerDemand.getReturnQuantity()).isEqualTo(DEFAULT_RETURN_QUANTITY);
        assertThat(testMedicineSoldPerDemand.getSellingDate()).isEqualTo(UPDATED_SELLING_DATE);
    }

    @Test
    @Transactional
    void fullUpdateMedicineSoldPerDemandWithPatch() throws Exception {
        // Initialize the database
        medicineSoldPerDemandRepository.saveAndFlush(medicineSoldPerDemand);

        int databaseSizeBeforeUpdate = medicineSoldPerDemandRepository.findAll().size();

        // Update the medicineSoldPerDemand using partial update
        MedicineSoldPerDemand partialUpdatedMedicineSoldPerDemand = new MedicineSoldPerDemand();
        partialUpdatedMedicineSoldPerDemand.setId(medicineSoldPerDemand.getId());

        partialUpdatedMedicineSoldPerDemand
            .medicineId(UPDATED_MEDICINE_ID)
            .medicineName(UPDATED_MEDICINE_NAME)
            .medicineOrderQuantity(UPDATED_MEDICINE_ORDER_QUANTITY)
            .unitPrice(UPDATED_UNIT_PRICE)
            .discountedPrice(UPDATED_DISCOUNTED_PRICE)
            .issuedQuantity(UPDATED_ISSUED_QUANTITY)
            .returnQuantity(UPDATED_RETURN_QUANTITY)
            .sellingDate(UPDATED_SELLING_DATE);

        restMedicineSoldPerDemandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedicineSoldPerDemand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedicineSoldPerDemand))
            )
            .andExpect(status().isOk());

        // Validate the MedicineSoldPerDemand in the database
        List<MedicineSoldPerDemand> medicineSoldPerDemandList = medicineSoldPerDemandRepository.findAll();
        assertThat(medicineSoldPerDemandList).hasSize(databaseSizeBeforeUpdate);
        MedicineSoldPerDemand testMedicineSoldPerDemand = medicineSoldPerDemandList.get(medicineSoldPerDemandList.size() - 1);
        assertThat(testMedicineSoldPerDemand.getMedicineId()).isEqualTo(UPDATED_MEDICINE_ID);
        assertThat(testMedicineSoldPerDemand.getMedicineName()).isEqualTo(UPDATED_MEDICINE_NAME);
        assertThat(testMedicineSoldPerDemand.getMedicineOrderQuantity()).isEqualTo(UPDATED_MEDICINE_ORDER_QUANTITY);
        assertThat(testMedicineSoldPerDemand.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testMedicineSoldPerDemand.getDiscountedPrice()).isEqualTo(UPDATED_DISCOUNTED_PRICE);
        assertThat(testMedicineSoldPerDemand.getIssuedQuantity()).isEqualTo(UPDATED_ISSUED_QUANTITY);
        assertThat(testMedicineSoldPerDemand.getReturnQuantity()).isEqualTo(UPDATED_RETURN_QUANTITY);
        assertThat(testMedicineSoldPerDemand.getSellingDate()).isEqualTo(UPDATED_SELLING_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingMedicineSoldPerDemand() throws Exception {
        int databaseSizeBeforeUpdate = medicineSoldPerDemandRepository.findAll().size();
        medicineSoldPerDemand.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicineSoldPerDemandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, medicineSoldPerDemand.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medicineSoldPerDemand))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicineSoldPerDemand in the database
        List<MedicineSoldPerDemand> medicineSoldPerDemandList = medicineSoldPerDemandRepository.findAll();
        assertThat(medicineSoldPerDemandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMedicineSoldPerDemand() throws Exception {
        int databaseSizeBeforeUpdate = medicineSoldPerDemandRepository.findAll().size();
        medicineSoldPerDemand.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicineSoldPerDemandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medicineSoldPerDemand))
            )
            .andExpect(status().isBadRequest());

        // Validate the MedicineSoldPerDemand in the database
        List<MedicineSoldPerDemand> medicineSoldPerDemandList = medicineSoldPerDemandRepository.findAll();
        assertThat(medicineSoldPerDemandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMedicineSoldPerDemand() throws Exception {
        int databaseSizeBeforeUpdate = medicineSoldPerDemandRepository.findAll().size();
        medicineSoldPerDemand.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicineSoldPerDemandMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medicineSoldPerDemand))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MedicineSoldPerDemand in the database
        List<MedicineSoldPerDemand> medicineSoldPerDemandList = medicineSoldPerDemandRepository.findAll();
        assertThat(medicineSoldPerDemandList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMedicineSoldPerDemand() throws Exception {
        // Initialize the database
        medicineSoldPerDemandRepository.saveAndFlush(medicineSoldPerDemand);

        int databaseSizeBeforeDelete = medicineSoldPerDemandRepository.findAll().size();

        // Delete the medicineSoldPerDemand
        restMedicineSoldPerDemandMockMvc
            .perform(delete(ENTITY_API_URL_ID, medicineSoldPerDemand.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MedicineSoldPerDemand> medicineSoldPerDemandList = medicineSoldPerDemandRepository.findAll();
        assertThat(medicineSoldPerDemandList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

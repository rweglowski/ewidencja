package com.home.web.rest;

import com.home.EwidencjaApp;

import com.home.domain.Ot;
import com.home.repository.OtRepository;
import com.home.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OtResource REST controller.
 *
 * @see OtResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EwidencjaApp.class)
public class OtResourceIntTest {

    private static final String DEFAULT_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_PLACE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    @Autowired
    private OtRepository otRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOtMockMvc;

    private Ot ot;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            OtResource otResource = new OtResource(otRepository);
        this.restOtMockMvc = MockMvcBuilders.standaloneSetup(otResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ot createEntity(EntityManager em) {
        Ot ot = new Ot()
                .place(DEFAULT_PLACE)
                .date(DEFAULT_DATE)
                .name(DEFAULT_NAME)
                .path(DEFAULT_PATH);
        return ot;
    }

    @Before
    public void initTest() {
        ot = createEntity(em);
    }

    @Test
    @Transactional
    public void createOt() throws Exception {
        int databaseSizeBeforeCreate = otRepository.findAll().size();

        // Create the Ot

        restOtMockMvc.perform(post("/api/ots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ot)))
            .andExpect(status().isCreated());

        // Validate the Ot in the database
        List<Ot> otList = otRepository.findAll();
        assertThat(otList).hasSize(databaseSizeBeforeCreate + 1);
        Ot testOt = otList.get(otList.size() - 1);
        assertThat(testOt.getPlace()).isEqualTo(DEFAULT_PLACE);
        assertThat(testOt.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testOt.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOt.getPath()).isEqualTo(DEFAULT_PATH);
    }

    @Test
    @Transactional
    public void createOtWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = otRepository.findAll().size();

        // Create the Ot with an existing ID
        Ot existingOt = new Ot();
        existingOt.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOtMockMvc.perform(post("/api/ots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingOt)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Ot> otList = otRepository.findAll();
        assertThat(otList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOts() throws Exception {
        // Initialize the database
        otRepository.saveAndFlush(ot);

        // Get all the otList
        restOtMockMvc.perform(get("/api/ots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ot.getId().intValue())))
            .andExpect(jsonPath("$.[*].place").value(hasItem(DEFAULT_PLACE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())));
    }

    @Test
    @Transactional
    public void getOt() throws Exception {
        // Initialize the database
        otRepository.saveAndFlush(ot);

        // Get the ot
        restOtMockMvc.perform(get("/api/ots/{id}", ot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ot.getId().intValue()))
            .andExpect(jsonPath("$.place").value(DEFAULT_PLACE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOt() throws Exception {
        // Get the ot
        restOtMockMvc.perform(get("/api/ots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOt() throws Exception {
        // Initialize the database
        otRepository.saveAndFlush(ot);
        int databaseSizeBeforeUpdate = otRepository.findAll().size();

        // Update the ot
        Ot updatedOt = otRepository.findOne(ot.getId());
        updatedOt
                .place(UPDATED_PLACE)
                .date(UPDATED_DATE)
                .name(UPDATED_NAME)
                .path(UPDATED_PATH);

        restOtMockMvc.perform(put("/api/ots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOt)))
            .andExpect(status().isOk());

        // Validate the Ot in the database
        List<Ot> otList = otRepository.findAll();
        assertThat(otList).hasSize(databaseSizeBeforeUpdate);
        Ot testOt = otList.get(otList.size() - 1);
        assertThat(testOt.getPlace()).isEqualTo(UPDATED_PLACE);
        assertThat(testOt.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testOt.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOt.getPath()).isEqualTo(UPDATED_PATH);
    }

    @Test
    @Transactional
    public void updateNonExistingOt() throws Exception {
        int databaseSizeBeforeUpdate = otRepository.findAll().size();

        // Create the Ot

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOtMockMvc.perform(put("/api/ots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ot)))
            .andExpect(status().isCreated());

        // Validate the Ot in the database
        List<Ot> otList = otRepository.findAll();
        assertThat(otList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOt() throws Exception {
        // Initialize the database
        otRepository.saveAndFlush(ot);
        int databaseSizeBeforeDelete = otRepository.findAll().size();

        // Get the ot
        restOtMockMvc.perform(delete("/api/ots/{id}", ot.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ot> otList = otRepository.findAll();
        assertThat(otList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ot.class);
    }
}

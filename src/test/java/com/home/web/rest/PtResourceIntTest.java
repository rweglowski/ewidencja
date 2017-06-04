package com.home.web.rest;

import com.home.EwidencjaApp;

import com.home.domain.Pt;
import com.home.repository.PtRepository;
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
 * Test class for the PtResource REST controller.
 *
 * @see PtResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EwidencjaApp.class)
public class PtResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private PtRepository ptRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPtMockMvc;

    private Pt pt;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            PtResource ptResource = new PtResource(ptRepository);
        this.restPtMockMvc = MockMvcBuilders.standaloneSetup(ptResource)
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
    public static Pt createEntity(EntityManager em) {
        Pt pt = new Pt()
                .date(DEFAULT_DATE)
                .name(DEFAULT_NAME)
                .note(DEFAULT_NOTE);
        return pt;
    }

    @Before
    public void initTest() {
        pt = createEntity(em);
    }

    @Test
    @Transactional
    public void createPt() throws Exception {
        int databaseSizeBeforeCreate = ptRepository.findAll().size();

        // Create the Pt

        restPtMockMvc.perform(post("/api/pts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pt)))
            .andExpect(status().isCreated());

        // Validate the Pt in the database
        List<Pt> ptList = ptRepository.findAll();
        assertThat(ptList).hasSize(databaseSizeBeforeCreate + 1);
        Pt testPt = ptList.get(ptList.size() - 1);
        assertThat(testPt.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPt.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPt.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createPtWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ptRepository.findAll().size();

        // Create the Pt with an existing ID
        Pt existingPt = new Pt();
        existingPt.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPtMockMvc.perform(post("/api/pts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPt)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Pt> ptList = ptRepository.findAll();
        assertThat(ptList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ptRepository.findAll().size();
        // set the field null
        pt.setName(null);

        // Create the Pt, which fails.

        restPtMockMvc.perform(post("/api/pts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pt)))
            .andExpect(status().isBadRequest());

        List<Pt> ptList = ptRepository.findAll();
        assertThat(ptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPts() throws Exception {
        // Initialize the database
        ptRepository.saveAndFlush(pt);

        // Get all the ptList
        restPtMockMvc.perform(get("/api/pts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pt.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }

    @Test
    @Transactional
    public void getPt() throws Exception {
        // Initialize the database
        ptRepository.saveAndFlush(pt);

        // Get the pt
        restPtMockMvc.perform(get("/api/pts/{id}", pt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pt.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPt() throws Exception {
        // Get the pt
        restPtMockMvc.perform(get("/api/pts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePt() throws Exception {
        // Initialize the database
        ptRepository.saveAndFlush(pt);
        int databaseSizeBeforeUpdate = ptRepository.findAll().size();

        // Update the pt
        Pt updatedPt = ptRepository.findOne(pt.getId());
        updatedPt
                .date(UPDATED_DATE)
                .name(UPDATED_NAME)
                .note(UPDATED_NOTE);

        restPtMockMvc.perform(put("/api/pts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPt)))
            .andExpect(status().isOk());

        // Validate the Pt in the database
        List<Pt> ptList = ptRepository.findAll();
        assertThat(ptList).hasSize(databaseSizeBeforeUpdate);
        Pt testPt = ptList.get(ptList.size() - 1);
        assertThat(testPt.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPt.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPt.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingPt() throws Exception {
        int databaseSizeBeforeUpdate = ptRepository.findAll().size();

        // Create the Pt

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPtMockMvc.perform(put("/api/pts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pt)))
            .andExpect(status().isCreated());

        // Validate the Pt in the database
        List<Pt> ptList = ptRepository.findAll();
        assertThat(ptList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePt() throws Exception {
        // Initialize the database
        ptRepository.saveAndFlush(pt);
        int databaseSizeBeforeDelete = ptRepository.findAll().size();

        // Get the pt
        restPtMockMvc.perform(delete("/api/pts/{id}", pt.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pt> ptList = ptRepository.findAll();
        assertThat(ptList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pt.class);
    }
}

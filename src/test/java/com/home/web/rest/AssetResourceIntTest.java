package com.home.web.rest;

import com.home.EwidencjaApp;

import com.home.domain.Asset;
import com.home.repository.AssetRepository;
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

import com.home.domain.enumeration.AssetStatus;
/**
 * Test class for the AssetResource REST controller.
 *
 * @see AssetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EwidencjaApp.class)
public class AssetResourceIntTest {

    private static final String DEFAULT_REGISTRATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BARCODE = "AAAAAAAAAA";
    private static final String UPDATED_BARCODE = "BBBBBBBBBB";

    private static final String DEFAULT_SYMBOL = "AAAAAAAAAA";
    private static final String UPDATED_SYMBOL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PURCHASE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PURCHASE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE_OF_USE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE_OF_USE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LIQUIDATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LIQUIDATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_START_DATE_OF_USE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE_OF_USE = LocalDate.now(ZoneId.systemDefault());

    private static final AssetStatus DEFAULT_STATUS = AssetStatus.IN_USE;
    private static final AssetStatus UPDATED_STATUS = AssetStatus.DELETED;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_VALUE = 0L;
    private static final Long UPDATED_VALUE = 1L;

    private static final String DEFAULT_ASSET_GROUP = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_GROUP = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INVENTORY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_INVENTORY_CODE = "BBBBBBBBBB";

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssetMockMvc;

    private Asset asset;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            AssetResource assetResource = new AssetResource(assetRepository);
        this.restAssetMockMvc = MockMvcBuilders.standaloneSetup(assetResource)
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
    public static Asset createEntity(EntityManager em) {
        Asset asset = new Asset()
                .registrationCode(DEFAULT_REGISTRATION_CODE)
                .barcode(DEFAULT_BARCODE)
                .symbol(DEFAULT_SYMBOL)
                .purchaseDate(DEFAULT_PURCHASE_DATE)
                .endDateOfUse(DEFAULT_END_DATE_OF_USE)
                .liquidationDate(DEFAULT_LIQUIDATION_DATE)
                .startDateOfUse(DEFAULT_START_DATE_OF_USE)
                .status(DEFAULT_STATUS)
                .description(DEFAULT_DESCRIPTION)
                .value(DEFAULT_VALUE)
                .assetGroup(DEFAULT_ASSET_GROUP)
                .name(DEFAULT_NAME)
                .inventoryCode(DEFAULT_INVENTORY_CODE);
        return asset;
    }

    @Before
    public void initTest() {
        asset = createEntity(em);
    }

    @Test
    @Transactional
    public void createAsset() throws Exception {
        int databaseSizeBeforeCreate = assetRepository.findAll().size();

        // Create the Asset

        restAssetMockMvc.perform(post("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asset)))
            .andExpect(status().isCreated());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeCreate + 1);
        Asset testAsset = assetList.get(assetList.size() - 1);
        assertThat(testAsset.getRegistrationCode()).isEqualTo(DEFAULT_REGISTRATION_CODE);
        assertThat(testAsset.getBarcode()).isEqualTo(DEFAULT_BARCODE);
        assertThat(testAsset.getSymbol()).isEqualTo(DEFAULT_SYMBOL);
        assertThat(testAsset.getPurchaseDate()).isEqualTo(DEFAULT_PURCHASE_DATE);
        assertThat(testAsset.getEndDateOfUse()).isEqualTo(DEFAULT_END_DATE_OF_USE);
        assertThat(testAsset.getLiquidationDate()).isEqualTo(DEFAULT_LIQUIDATION_DATE);
        assertThat(testAsset.getStartDateOfUse()).isEqualTo(DEFAULT_START_DATE_OF_USE);
        assertThat(testAsset.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAsset.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAsset.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAsset.getAssetGroup()).isEqualTo(DEFAULT_ASSET_GROUP);
        assertThat(testAsset.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAsset.getInventoryCode()).isEqualTo(DEFAULT_INVENTORY_CODE);
    }

    @Test
    @Transactional
    public void createAssetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assetRepository.findAll().size();

        // Create the Asset with an existing ID
        Asset existingAsset = new Asset();
        existingAsset.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetMockMvc.perform(post("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAsset)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPurchaseDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetRepository.findAll().size();
        // set the field null
        asset.setPurchaseDate(null);

        // Create the Asset, which fails.

        restAssetMockMvc.perform(post("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asset)))
            .andExpect(status().isBadRequest());

        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetRepository.findAll().size();
        // set the field null
        asset.setValue(null);

        // Create the Asset, which fails.

        restAssetMockMvc.perform(post("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asset)))
            .andExpect(status().isBadRequest());

        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetRepository.findAll().size();
        // set the field null
        asset.setName(null);

        // Create the Asset, which fails.

        restAssetMockMvc.perform(post("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asset)))
            .andExpect(status().isBadRequest());

        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssets() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList
        restAssetMockMvc.perform(get("/api/assets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asset.getId().intValue())))
            .andExpect(jsonPath("$.[*].registrationCode").value(hasItem(DEFAULT_REGISTRATION_CODE.toString())))
            .andExpect(jsonPath("$.[*].barcode").value(hasItem(DEFAULT_BARCODE.toString())))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL.toString())))
            .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDateOfUse").value(hasItem(DEFAULT_END_DATE_OF_USE.toString())))
            .andExpect(jsonPath("$.[*].liquidationDate").value(hasItem(DEFAULT_LIQUIDATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].startDateOfUse").value(hasItem(DEFAULT_START_DATE_OF_USE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].assetGroup").value(hasItem(DEFAULT_ASSET_GROUP.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].inventoryCode").value(hasItem(DEFAULT_INVENTORY_CODE.toString())));
    }

    @Test
    @Transactional
    public void getAsset() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get the asset
        restAssetMockMvc.perform(get("/api/assets/{id}", asset.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(asset.getId().intValue()))
            .andExpect(jsonPath("$.registrationCode").value(DEFAULT_REGISTRATION_CODE.toString()))
            .andExpect(jsonPath("$.barcode").value(DEFAULT_BARCODE.toString()))
            .andExpect(jsonPath("$.symbol").value(DEFAULT_SYMBOL.toString()))
            .andExpect(jsonPath("$.purchaseDate").value(DEFAULT_PURCHASE_DATE.toString()))
            .andExpect(jsonPath("$.endDateOfUse").value(DEFAULT_END_DATE_OF_USE.toString()))
            .andExpect(jsonPath("$.liquidationDate").value(DEFAULT_LIQUIDATION_DATE.toString()))
            .andExpect(jsonPath("$.startDateOfUse").value(DEFAULT_START_DATE_OF_USE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()))
            .andExpect(jsonPath("$.assetGroup").value(DEFAULT_ASSET_GROUP.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.inventoryCode").value(DEFAULT_INVENTORY_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAsset() throws Exception {
        // Get the asset
        restAssetMockMvc.perform(get("/api/assets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAsset() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);
        int databaseSizeBeforeUpdate = assetRepository.findAll().size();

        // Update the asset
        Asset updatedAsset = assetRepository.findOne(asset.getId());
        updatedAsset
                .registrationCode(UPDATED_REGISTRATION_CODE)
                .barcode(UPDATED_BARCODE)
                .symbol(UPDATED_SYMBOL)
                .purchaseDate(UPDATED_PURCHASE_DATE)
                .endDateOfUse(UPDATED_END_DATE_OF_USE)
                .liquidationDate(UPDATED_LIQUIDATION_DATE)
                .startDateOfUse(UPDATED_START_DATE_OF_USE)
                .status(UPDATED_STATUS)
                .description(UPDATED_DESCRIPTION)
                .value(UPDATED_VALUE)
                .assetGroup(UPDATED_ASSET_GROUP)
                .name(UPDATED_NAME)
                .inventoryCode(UPDATED_INVENTORY_CODE);

        restAssetMockMvc.perform(put("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAsset)))
            .andExpect(status().isOk());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeUpdate);
        Asset testAsset = assetList.get(assetList.size() - 1);
        assertThat(testAsset.getRegistrationCode()).isEqualTo(UPDATED_REGISTRATION_CODE);
        assertThat(testAsset.getBarcode()).isEqualTo(UPDATED_BARCODE);
        assertThat(testAsset.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testAsset.getPurchaseDate()).isEqualTo(UPDATED_PURCHASE_DATE);
        assertThat(testAsset.getEndDateOfUse()).isEqualTo(UPDATED_END_DATE_OF_USE);
        assertThat(testAsset.getLiquidationDate()).isEqualTo(UPDATED_LIQUIDATION_DATE);
        assertThat(testAsset.getStartDateOfUse()).isEqualTo(UPDATED_START_DATE_OF_USE);
        assertThat(testAsset.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAsset.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAsset.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAsset.getAssetGroup()).isEqualTo(UPDATED_ASSET_GROUP);
        assertThat(testAsset.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAsset.getInventoryCode()).isEqualTo(UPDATED_INVENTORY_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingAsset() throws Exception {
        int databaseSizeBeforeUpdate = assetRepository.findAll().size();

        // Create the Asset

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssetMockMvc.perform(put("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asset)))
            .andExpect(status().isCreated());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAsset() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);
        int databaseSizeBeforeDelete = assetRepository.findAll().size();

        // Get the asset
        restAssetMockMvc.perform(delete("/api/assets/{id}", asset.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Asset.class);
    }
}

package com.home.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.home.domain.Ot;

import com.home.domain.OtCommandHandler;
import com.home.repository.OtRepository;
import com.home.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Ot.
 */
@RestController
@RequestMapping("/api")
public class OtResource {

    private final Logger log = LoggerFactory.getLogger(OtResource.class);

    private static final String ENTITY_NAME = "ot";

    private final OtRepository otRepository;

    @Autowired
    private OtCommandHandler handler;

    public OtResource(OtRepository otRepository) {
        this.otRepository = otRepository;
    }

    /**
     * POST  /ots : Create a new ot.
     *
     * @param ot the ot to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ot, or with status 400 (Bad Request) if the ot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ots")
    @Timed
    public ResponseEntity<Ot> createOt(@Valid @RequestBody Ot ot) throws URISyntaxException {
        log.debug("REST request to save Ot : {}", ot);
        if (ot.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ot cannot already have an ID")).body(null);
        }

        handler.handle(ot);
        Ot result = otRepository.save(ot);
        return ResponseEntity.created(new URI("/api/ots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ots : Updates an existing ot.
     *
     * @param ot the ot to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ot,
     * or with status 400 (Bad Request) if the ot is not valid,
     * or with status 500 (Internal Server Error) if the ot couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ots")
    @Timed
    public ResponseEntity<Ot> updateOt(@Valid @RequestBody Ot ot) throws URISyntaxException {
        log.debug("REST request to update Ot : {}", ot);
        if (ot.getId() == null) {
            return createOt(ot);
        }
        Ot result = otRepository.save(ot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ot.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ots : get all the ots.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ots in body
     */
    @GetMapping("/ots")
    @Timed
    public List<Ot> getAllOts() {
        log.debug("REST request to get all Ots");
        List<Ot> ots = otRepository.findAllWithEagerRelationships();
        return ots;
    }

    /**
     * GET  /ots/:id : get the "id" ot.
     *
     * @param id the id of the ot to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ot, or with status 404 (Not Found)
     */
    @GetMapping("/ots/{id}")
    @Timed
    public ResponseEntity<Ot> getOt(@PathVariable Long id) {
        log.debug("REST request to get Ot : {}", id);
        Ot ot = otRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ot));
    }

    /**
     * DELETE  /ots/:id : delete the "id" ot.
     *
     * @param id the id of the ot to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ots/{id}")
    @Timed
    public ResponseEntity<Void> deleteOt(@PathVariable Long id) {
        log.debug("REST request to delete Ot : {}", id);
        otRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}

package com.home.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.home.domain.Pt;

import com.home.domain.PtCommandHandler;
import com.home.repository.PtRepository;
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
 * REST controller for managing Pt.
 */
@RestController
@RequestMapping("/api")
public class PtResource {

    private final Logger log = LoggerFactory.getLogger(PtResource.class);

    private static final String ENTITY_NAME = "pt";

    private final PtRepository ptRepository;

    @Autowired
    private PtCommandHandler handler;

    public PtResource(PtRepository ptRepository) {
        this.ptRepository = ptRepository;
    }

    /**
     * POST  /pts : Create a new pt.
     *
     * @param pt the pt to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pt, or with status 400 (Bad Request) if the pt has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pts")
    @Timed
    public ResponseEntity<Pt> createPt(@Valid @RequestBody Pt pt) throws URISyntaxException {
        log.debug("REST request to save Pt : {}", pt);
        if (pt.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pt cannot already have an ID")).body(null);
        }
        handler.handle(pt);
        Pt result = ptRepository.save(pt);
        return ResponseEntity.created(new URI("/api/pts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pts : Updates an existing pt.
     *
     * @param pt the pt to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pt,
     * or with status 400 (Bad Request) if the pt is not valid,
     * or with status 500 (Internal Server Error) if the pt couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pts")
    @Timed
    public ResponseEntity<Pt> updatePt(@Valid @RequestBody Pt pt) throws URISyntaxException {
        log.debug("REST request to update Pt : {}", pt);
        if (pt.getId() == null) {
            return createPt(pt);
        }
        Pt result = ptRepository.save(pt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pt.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pts : get all the pts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pts in body
     */
    @GetMapping("/pts")
    @Timed
    public List<Pt> getAllPts() {
        log.debug("REST request to get all Pts");
        List<Pt> pts = ptRepository.findAllWithEagerRelationships();
        return pts;
    }

    /**
     * GET  /pts/:id : get the "id" pt.
     *
     * @param id the id of the pt to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pt, or with status 404 (Not Found)
     */
    @GetMapping("/pts/{id}")
    @Timed
    public ResponseEntity<Pt> getPt(@PathVariable Long id) {
        log.debug("REST request to get Pt : {}", id);
        Pt pt = ptRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pt));
    }

    /**
     * DELETE  /pts/:id : delete the "id" pt.
     *
     * @param id the id of the pt to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pts/{id}")
    @Timed
    public ResponseEntity<Void> deletePt(@PathVariable Long id) {
        log.debug("REST request to delete Pt : {}", id);
        ptRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}

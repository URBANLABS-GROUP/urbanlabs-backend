package org.example.controller;

import com.sun.istack.NotNull;
import org.example.model.businesscenter.BusinessCenter;
import org.example.repository.BusinessCenterRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/business-center")
public class BusinessCenterController {

    private final BusinessCenterRepository businessCenterRepository;

    public BusinessCenterController(final BusinessCenterRepository businessCenterRepository) {
        this.businessCenterRepository = businessCenterRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BusinessCenter>> getAll() {
        return ResponseEntity.ok(businessCenterRepository.findAll());
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BusinessCenter> create(@RequestBody @NotNull @Validated final BusinessCenter entity) {
        return ResponseEntity.ok(businessCenterRepository.save(entity));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BusinessCenter> update(
            @PathVariable("id") final int id,
            @RequestBody @NotNull @Validated final BusinessCenter entity) {

        Optional<BusinessCenter> businessCenter = businessCenterRepository.findById(id);
        if (businessCenter.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(businessCenterRepository.save(entity));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("id") final int id) {
        Optional<BusinessCenter> businessCenter = businessCenterRepository.findById(id);
        if (businessCenter.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        businessCenterRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

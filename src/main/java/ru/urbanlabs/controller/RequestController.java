package ru.urbanlabs.controller;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.urbanlabs.model.request.Request;
import ru.urbanlabs.repository.RequestRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/request")
public class RequestController {

    private final RequestRepository requestRepository;

    @Autowired
    public RequestController(final RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Request>> getAll() {
        return ResponseEntity.ok(requestRepository.findAll());
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Request> create(@RequestBody @NotNull @Validated final Request entity) {
        return ResponseEntity.ok(requestRepository.save(entity));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Request> update(
        @PathVariable("id") final int id,
        @RequestBody @NotNull @Validated final Request entity) {

        Optional<Request> request = requestRepository.findById(id);
        if (request.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(requestRepository.save(entity));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("id") final int id) {
        Optional<Request> request = requestRepository.findById(id);
        if (request.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        requestRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

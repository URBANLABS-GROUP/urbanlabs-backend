package org.example.controller;

import com.sun.istack.NotNull;
import org.example.model.businesscenter.Room;
import org.example.repository.RoomRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomRepository roomRepository;

    public RoomController(final RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Room>> getAll() {
        return ResponseEntity.ok(roomRepository.findAll());
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Room> create(@RequestBody @NotNull @Validated final Room entity) {
        return ResponseEntity.ok(roomRepository.save(entity));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Room> update(
            @PathVariable("id") final int id,
            @RequestBody @NotNull @Validated final Room entity) {

        Optional<Room> businessCenter = roomRepository.findById(id);
        if (businessCenter.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roomRepository.save(entity));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("id") final int id) {
        Optional<Room> businessCenter = roomRepository.findById(id);
        if (businessCenter.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        roomRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

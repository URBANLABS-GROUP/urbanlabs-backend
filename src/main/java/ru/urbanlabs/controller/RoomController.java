package ru.urbanlabs.controller;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.urbanlabs.dto.RoomTelemetryInfo;
import ru.urbanlabs.model.businesscenter.Room;
import ru.urbanlabs.repository.RoomRepository;
import ru.urbanlabs.service.RoomTelemetryService;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomRepository roomRepository;
    private final RoomTelemetryService roomTelemetryService;

    @Autowired
    public RoomController(final RoomRepository roomRepository,
                          final RoomTelemetryService roomTelemetryService) {
        this.roomRepository = roomRepository;
        this.roomTelemetryService = roomTelemetryService;
    }

    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomTelemetryInfo> getRoomTelemetryData(@PathVariable Integer id) {
        final Optional<Room> room = roomRepository.findById(id);
        if (room.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        RoomTelemetryInfo telemetryInfo = roomTelemetryService.buildRoomTelemetryInfo(room.get());
        roomTelemetryService.randomizeRoomTelemetryInfo(telemetryInfo);
        return ResponseEntity.ok(telemetryInfo);
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

        Optional<Room> room = roomRepository.findById(id);
        if (room.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roomRepository.save(entity));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("id") final int id) {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        roomRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

package org.example.controller;

import com.sun.istack.NotNull;
import org.example.dto.BusinessCenterStoreyTelemetryInfo;
import org.example.dto.RoomTelemetryInfo;
import org.example.model.businesscenter.BusinessCenterStorey;
import org.example.model.businesscenter.Room;
import org.example.repository.BusinessCenterStoreyRepository;
import org.example.repository.RoomRepository;
import org.example.service.RoomTelemetryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/business-center-storey")
public class BusinessCenterStoreyController {

    private final BusinessCenterStoreyRepository businessCenterStoreyRepository;
    private final RoomRepository roomRepository;
    private final RoomTelemetryService roomTelemetryService;

    public BusinessCenterStoreyController(final BusinessCenterStoreyRepository businessCenterStoreyRepository,
                                          final RoomRepository roomRepository,
                                          final RoomTelemetryService roomTelemetryService) {
        this.businessCenterStoreyRepository = businessCenterStoreyRepository;
        this.roomRepository = roomRepository;
        this.roomTelemetryService = roomTelemetryService;
    }

    @RequestMapping(value = "/info/{id}", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BusinessCenterStoreyTelemetryInfo> getRoomTelemetryData(@PathVariable Integer id) {
        final Optional<BusinessCenterStorey> storey = businessCenterStoreyRepository.findById(id);
        if (storey.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Room> rooms = storey.get().getRooms();
        List<RoomTelemetryInfo> roomTelemetryInfos = new ArrayList<>();
        for (final Room room : rooms) {
            RoomTelemetryInfo telemetryInfo = roomTelemetryService.buildRoomTelemetryInfo(room);
            roomTelemetryService.randomizeRoomTelemetryInfo(telemetryInfo);

            roomTelemetryInfos.add(telemetryInfo);
        }

        return ResponseEntity.ok(BusinessCenterStoreyTelemetryInfo.of(roomTelemetryInfos));
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BusinessCenterStorey>> getAll() {
        return ResponseEntity.ok(businessCenterStoreyRepository.findAll());
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BusinessCenterStorey> create(@RequestBody @NotNull @Validated final BusinessCenterStorey entity) {
        return ResponseEntity.ok(businessCenterStoreyRepository.save(entity));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<BusinessCenterStorey> update(
            @PathVariable("id") final int id,
            @RequestBody @NotNull @Validated final BusinessCenterStorey entity) {

        Optional<BusinessCenterStorey> businessCenterStorey = businessCenterStoreyRepository.findById(id);
        if (businessCenterStorey.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(businessCenterStoreyRepository.save(entity));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable("id") final int id) {
        Optional<BusinessCenterStorey> businessCenter = businessCenterStoreyRepository.findById(id);
        if (businessCenter.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        businessCenterStoreyRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

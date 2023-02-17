package org.example.controller;


import org.example.model.businesscenter.BusinessCenter;
import org.example.model.businesscenter.BusinessCenterStorey;
import org.example.model.businesscenter.Room;
import org.example.repository.BusinessCenterRepository;
import org.example.repository.BusinessCenterStoreyRepository;
import org.example.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TestDataController {

    @Autowired
    BusinessCenterRepository businessCenterRepository;
    @Autowired
    BusinessCenterStoreyRepository businessCenterStoreyRepository;
    @Autowired
    RoomRepository roomRepository;

    private static AtomicInteger roomCounter = new AtomicInteger(0);

    @RequestMapping(value = "/fill-test-data", method = RequestMethod.GET)
    public ResponseEntity<String> testData() {
        final BusinessCenter businessCenterFirst = buildBusinessCenter("Тестовые бизнец центр 1");
        businessCenterRepository.save(businessCenterFirst);

        final BusinessCenter businessCenterSecond = buildBusinessCenter("Тестовые бизнец центр 2");
        businessCenterRepository.save(businessCenterSecond);

        final BusinessCenterStorey businessCenterStorey1Floor = buildBusinessCenterStorey(businessCenterFirst, "1 этаж");
        final BusinessCenterStorey businessCenterStorey2Floor = buildBusinessCenterStorey(businessCenterFirst, "2 этаж");
        final BusinessCenterStorey businessCenterStorey3Floor = buildBusinessCenterStorey(businessCenterFirst, "3 этаж");
        final List<BusinessCenterStorey> firstBusinessCenterStoreys =
                businessCenterStoreyRepository.saveAll(List.of(
                        businessCenterStorey1Floor,
                        businessCenterStorey2Floor,
                        businessCenterStorey3Floor
                ));

        final BusinessCenterStorey businessCenterSecondStorey0Floor = buildBusinessCenterStorey(businessCenterSecond, "подвал");
        final BusinessCenterStorey businessCenterSecondStorey1Floor = buildBusinessCenterStorey(businessCenterSecond, "1 этаж");
        final BusinessCenterStorey businessCenterSecondStorey2Floor = buildBusinessCenterStorey(businessCenterSecond, "2 этаж");
        final BusinessCenterStorey businessCenterSecondStorey3Floor = buildBusinessCenterStorey(businessCenterSecond, "3 этаж");
        final BusinessCenterStorey businessCenterSecondStorey4Floor = buildBusinessCenterStorey(businessCenterSecond, "4 этаж");
        final List<BusinessCenterStorey> secondBusinessCenterStoreys = businessCenterStoreyRepository.saveAll(List.of(
                businessCenterSecondStorey0Floor,
                businessCenterSecondStorey1Floor,
                businessCenterSecondStorey2Floor,
                businessCenterSecondStorey3Floor,
                businessCenterSecondStorey4Floor
        ));

        List<Room> rooms = new ArrayList<>();
        for (BusinessCenterStorey centerStorey : firstBusinessCenterStoreys) {
            rooms.add(buildRoom(centerStorey));
        }
        for (BusinessCenterStorey centerStorey : secondBusinessCenterStoreys) {
            rooms.add(buildRoom(centerStorey));
        }

        rooms = roomRepository.saveAll(rooms);

        return ResponseEntity.ok("pong");
    }

    private static Room buildRoom(final BusinessCenterStorey businessCenterStorey) {
        final Room room = new Room();
        room.setName("Комната " + roomCounter.incrementAndGet());
        room.setBusinessCenterStoreyId(businessCenterStorey.getId());
        return room;
    }

    private static BusinessCenter buildBusinessCenter(String name) {
        final BusinessCenter businessCenterFirst = new BusinessCenter();
        businessCenterFirst.setName(name);
        businessCenterFirst.setStoreys(List.of());
        return businessCenterFirst;
    }

    private static BusinessCenterStorey buildBusinessCenterStorey(BusinessCenter businessCenterFirst, String name) {
        final BusinessCenterStorey businessCenterStorey = new BusinessCenterStorey();
        businessCenterStorey.setBusinessCenterId(businessCenterFirst.getId());
        businessCenterStorey.setMap(null);
        businessCenterStorey.setRooms(List.of());
        businessCenterStorey.setName(name);
        return businessCenterStorey;
    }
}

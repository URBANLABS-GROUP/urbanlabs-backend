package org.example.controller;


import org.example.model.businesscenter.BusinessCenter;
import org.example.model.businesscenter.BusinessCenterStorey;
import org.example.model.businesscenter.Room;
import org.example.model.customer.Lessee;
import org.example.model.customer.Lessor;
import org.example.model.document.LeaseContract;
import org.example.model.event.LeaseEvent;
import org.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
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
    @Autowired
    LessorRepository lessorRepository;
    @Autowired
    LesseeRepository lesseeRepository;
    @Autowired
    LeaseEventRepository leaseEventRepository;
    @Autowired
    LeaseContractRepository leaseContractRepository;

    private static AtomicInteger roomCounter = new AtomicInteger(0);

    @RequestMapping(value = "/fill-test-data", method = RequestMethod.GET)
    public ResponseEntity<String> testData() {

        Lessor lessor = buildLessor("Муратов Иван Игоревич");
        lessor = lessorRepository.save(lessor);

        Lessee lessee = buildLessee("Ваганес Артурович Джан");
        lessee = lesseeRepository.save(lessee);

        final BusinessCenter businessCenterFirst = buildBusinessCenter("Тестовые бизнец центр 1", lessor);
        businessCenterRepository.save(businessCenterFirst);

        final BusinessCenter businessCenterSecond = buildBusinessCenter("Тестовые бизнец центр 2", lessor);
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

        final List<LeaseContract> leaseContracts = List.of(
            buildLeaseContract(rooms.get(0), lessor, lessee),
            buildLeaseContract(rooms.get(2), lessor, lessee),
            buildLeaseContract(rooms.get(3), lessor, lessee),
            buildLeaseContract(rooms.get(4), lessor, lessee),
            buildLeaseContract(rooms.get(5), lessor, lessee),
            buildLeaseContract(rooms.get(7), lessor, lessee)
        );

        leaseContractRepository.saveAll(leaseContracts);


        List<LeaseEvent> leaseEvents = List.of(
            buildLeaseEvent(leaseContracts.get(0), 15000),
            buildLeaseEvent(leaseContracts.get(1), 20000),
            buildLeaseEvent(leaseContracts.get(2), 50000)

        );

        leaseEventRepository.saveAll(leaseEvents);

        return ResponseEntity.ok("pong");
    }

    private static LeaseEvent buildLeaseEvent(final LeaseContract leaseContract, final int money) {
        final LeaseEvent leaseEvent = new LeaseEvent();
        leaseEvent.setMoney(money);
        leaseEvent.setPaymentMonth(Instant.parse("2023-02-01T00:00:00Z"));
        leaseEvent.setLeaseContractId(leaseContract.getId());
        return leaseEvent;
    }

    private static LeaseContract buildLeaseContract(final Room room, final Lessor lessor, final Lessee lessee) {
        final LeaseContract leaseContract = new LeaseContract();
        leaseContract.setLessorId(lessor.getId());
        leaseContract.setLesseeId(lessee.getId());
        leaseContract.setRoomId(room.getId());

        leaseContract.setRent(50000);
        leaseContract.setStartTime(Instant.parse("2023-01-01T00:00:00Z"));
        leaseContract.setEndTime(Instant.parse("2024-01-01T00:00:00Z"));
        return leaseContract;
    }

    private static Room buildRoom(final BusinessCenterStorey businessCenterStorey) {
        final Room room = new Room();
        room.setName("Комната " + roomCounter.incrementAndGet());
        room.setBusinessCenterStoreyId(businessCenterStorey.getId());
        return room;
    }

    private static Lessor buildLessor(String name) {
        final Lessor lessor = new Lessor();
        lessor.setName(name);
        return lessor;
    }
    private static Lessee buildLessee(String name) {
        final Lessee lessor = new Lessee();
        lessor.setName(name);
        return lessor;
    }

    private static BusinessCenter buildBusinessCenter(String name, Lessor lessor) {
        final BusinessCenter businessCenterFirst = new BusinessCenter();
        businessCenterFirst.setName(name);
        businessCenterFirst.setStoreys(List.of());
        businessCenterFirst.setLessorId(lessor.getId());
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

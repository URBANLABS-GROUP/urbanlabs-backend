package org.example.controller;


import org.example.model.businesscenter.BusinessCenter;
import org.example.model.businesscenter.BusinessCenterStorey;
import org.example.model.businesscenter.Room;
import org.example.model.customer.Lessee;
import org.example.model.customer.Lessor;
import org.example.model.document.LeaseContract;
import org.example.model.event.LeaseEvent;
import org.example.model.request.Request;
import org.example.model.request.RequestStatus;
import org.example.model.request.RequestType;
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
    @Autowired
    RequestRepository requestRepository;

    private static AtomicInteger roomCounter = new AtomicInteger(0);

    public TestDataController(BusinessCenterRepository businessCenterRepository, BusinessCenterStoreyRepository businessCenterStoreyRepository, RoomRepository roomRepository, LessorRepository lessorRepository, LesseeRepository lesseeRepository, LeaseEventRepository leaseEventRepository, LeaseContractRepository leaseContractRepository, RequestRepository requestRepository) {
        this.businessCenterRepository = businessCenterRepository;
        this.businessCenterStoreyRepository = businessCenterStoreyRepository;
        this.roomRepository = roomRepository;
        this.lessorRepository = lessorRepository;
        this.lesseeRepository = lesseeRepository;
        this.leaseEventRepository = leaseEventRepository;
        this.leaseContractRepository = leaseContractRepository;
        this.requestRepository = requestRepository;

        testData();
    }

    @RequestMapping(value = "/fill-test-data", method = RequestMethod.GET)
    public ResponseEntity<String> testData() {

        Lessor lessor = buildLessor("Муратов Иван Игоревич");
        lessor = lessorRepository.save(lessor);

        Lessee lessee = buildLessee("Ваганес Артурович Джан");
        lessee = lesseeRepository.save(lessee);

        final BusinessCenter businessCenterFirst = buildBusinessCenter("Тестовые бизнес центр 1", lessor);
        businessCenterRepository.save(businessCenterFirst);

        final BusinessCenter businessCenterSecond = buildBusinessCenter("Тестовые бизнес центр 2", lessor);
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


        final List<LeaseEvent> leaseEvents = List.of(
            buildLeaseEvent(leaseContracts.get(0), 15000),
            buildLeaseEvent(leaseContracts.get(1), 20000),
            buildLeaseEvent(leaseContracts.get(2), 50000)

        );

        leaseEventRepository.saveAll(leaseEvents);

        List<Request> requests = List.of(
            buildRequest(rooms.get(0), 5000, Instant.parse("2023-01-01T01:00:00Z")),
            buildRequest(rooms.get(1), 7500, Instant.parse("2023-02-01T02:00:00Z")),
            buildRequest(rooms.get(2), 2000, Instant.parse("2023-02-01T12:00:00Z")),
            buildRequest(rooms.get(5), 6500, Instant.parse("2023-02-05T00:00:00Z")),
            buildRequest(rooms.get(7), 8000, Instant.parse("2023-02-27T14:00:00Z")),
            buildRequest(rooms.get(7), 15000, Instant.parse("2023-03-27T14:00:00Z"))

        );

        requests = requestRepository.saveAll(requests);

        return ResponseEntity.ok("pong");
    }

    private static LeaseEvent buildLeaseEvent(final LeaseContract leaseContract, final int money) {
        final LeaseEvent leaseEvent = new LeaseEvent();
        leaseEvent.setMoney(money);
        leaseEvent.setPaymentMonth(Instant.parse("2023-02-01T00:00:00Z"));
        leaseEvent.setLeaseContractId(leaseContract.getId());
        return leaseEvent;
    }

    private static Request buildRequest(final Room room, final int cost, final Instant time) {
        final Request request = new Request();
        request.setType(RequestType.REPAIR);
        request.setRoomId(room.getId());
        request.setCost(cost);
        request.setStatus(RequestStatus.DONE);
        request.setTitle("Ремонт");
        request.setCustomer("Алексей");
        request.setCreateTime(time);
        return request;
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

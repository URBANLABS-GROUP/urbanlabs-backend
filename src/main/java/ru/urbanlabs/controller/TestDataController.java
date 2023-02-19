package ru.urbanlabs.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.urbanlabs.model.businesscenter.BusinessCenter;
import ru.urbanlabs.model.businesscenter.BusinessCenterStorey;
import ru.urbanlabs.model.businesscenter.Room;
import ru.urbanlabs.model.customer.Lessee;
import ru.urbanlabs.model.customer.Lessor;
import ru.urbanlabs.model.document.Check;
import ru.urbanlabs.model.document.LeaseContract;
import ru.urbanlabs.model.event.LeaseEvent;
import ru.urbanlabs.model.iot.equipment.EquipmentType;
import ru.urbanlabs.model.iot.equipment.impl.move.MoveSensor;
import ru.urbanlabs.model.iot.equipment.impl.move.MoveSensorModel;
import ru.urbanlabs.model.iot.equipment.impl.powersocket.PowerSocket;
import ru.urbanlabs.model.iot.equipment.impl.powersocket.PowerSocketModel;
import ru.urbanlabs.model.iot.equipment.impl.smoke.SmokeSensor;
import ru.urbanlabs.model.iot.equipment.impl.smoke.SmokeSensorType;
import ru.urbanlabs.model.iot.equipment.impl.temp.TempSensor;
import ru.urbanlabs.model.iot.equipment.impl.temp.TempSensorModel;
import ru.urbanlabs.model.iot.equipment.impl.water.WaterSensor;
import ru.urbanlabs.model.iot.equipment.impl.water.WaterSensorModel;
import ru.urbanlabs.model.iot.equipment.impl.water.WaterTemp;
import ru.urbanlabs.model.request.Request;
import ru.urbanlabs.model.request.RequestStatus;
import ru.urbanlabs.model.request.RequestType;
import ru.urbanlabs.model.telemetry.impl.iot.*;
import ru.urbanlabs.repository.*;
import ru.urbanlabs.repository.equipment.*;
import ru.urbanlabs.repository.telemetry.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TestDataController {

    BusinessCenterRepository businessCenterRepository;
    BusinessCenterStoreyRepository businessCenterStoreyRepository;
    RoomRepository roomRepository;
    LessorRepository lessorRepository;
    LesseeRepository lesseeRepository;
    LeaseEventRepository leaseEventRepository;
    LeaseContractRepository leaseContractRepository;
    RequestRepository requestRepository;
    PowerSocketRepository powerSocketRepository;
    PowerSocketTelemetryRepository powerSocketTelemetryRepository;
    TempSensorRepository tempSensorRepository;
    TempSensorTelemetryRepository tempSensorTelemetryRepository;
    SmokeSensorRepository smokeSensorRepository;
    SmokeSensorTelemetryRepository smokeSensorTelemetryRepository;
    MoveSensorRepository moveSensorRepository;
    MoveSensorTelemetryRepository moveSensorTelemetryRepository;
    CheckRepository checkRepository;
    WaterSensorRepository waterSensorRepository;
    WaterSensorTelemetryRepository waterSensorTelemetryRepository;

    private static final AtomicInteger roomCounter = new AtomicInteger(0);

    @Autowired
    public TestDataController(BusinessCenterRepository businessCenterRepository,
                              BusinessCenterStoreyRepository businessCenterStoreyRepository,
                              RoomRepository roomRepository,
                              LessorRepository lessorRepository,
                              LesseeRepository lesseeRepository,
                              LeaseEventRepository leaseEventRepository,
                              LeaseContractRepository leaseContractRepository,
                              RequestRepository requestRepository,
                              PowerSocketRepository powerSocketRepository,
                              PowerSocketTelemetryRepository powerSocketTelemetryRepository,
                              TempSensorRepository tempSensorRepository,
                              TempSensorTelemetryRepository tempSensorTelemetryRepository,
                              SmokeSensorRepository smokeSensorRepository,
                              SmokeSensorTelemetryRepository smokeSensorTelemetryRepository,
                              MoveSensorRepository moveSensorRepository,
                              MoveSensorTelemetryRepository moveSensorTelemetryRepository,
                              CheckRepository checkRepository,
                              WaterSensorRepository waterSensorRepository,
                              WaterSensorTelemetryRepository waterSensorTelemetryRepository) {
        this.businessCenterRepository = businessCenterRepository;
        this.businessCenterStoreyRepository = businessCenterStoreyRepository;
        this.roomRepository = roomRepository;
        this.lessorRepository = lessorRepository;
        this.lesseeRepository = lesseeRepository;
        this.leaseEventRepository = leaseEventRepository;
        this.leaseContractRepository = leaseContractRepository;
        this.requestRepository = requestRepository;
        this.powerSocketRepository = powerSocketRepository;
        this.powerSocketTelemetryRepository = powerSocketTelemetryRepository;
        this.tempSensorRepository = tempSensorRepository;
        this.tempSensorTelemetryRepository = tempSensorTelemetryRepository;
        this.smokeSensorRepository = smokeSensorRepository;
        this.smokeSensorTelemetryRepository = smokeSensorTelemetryRepository;
        this.moveSensorRepository = moveSensorRepository;
        this.moveSensorTelemetryRepository = moveSensorTelemetryRepository;
        this.checkRepository = checkRepository;
        this.waterSensorRepository = waterSensorRepository;
        this.waterSensorTelemetryRepository = waterSensorTelemetryRepository;

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

        final BusinessCenter businessCenterOffice = buildBusinessCenter("Офис", lessor);
        businessCenterOffice.setWattPrice(5);
        businessCenterRepository.save(businessCenterOffice);

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

        final BusinessCenterStorey businessCenter2Storey1Floor = buildBusinessCenterStorey(businessCenterOffice, "1 этаж");
        businessCenter2Storey1Floor.setMap("{\"svgContainer\":{\"attributes\":{\"viewBox\":\"0 0 190 728\",\"fill\":\"transparent\"},\"bounding\":[[0,0],[728,190]],\"innerHtml\":\"<path stroke=\\\"#000\\\" stroke-width=\\\"2\\\" d=\\\"M2 664h150\\\"/>\\n <path stroke=\\\"#B0B0B0\\\" stroke-width=\\\"2\\\" d=\\\"M68 665v63M98 665v63M128 665v63M113 665v63M83 665v63M66 2v63M96 2v63M126 2v63M111 2v63M81 2v63\\\"/>\\n <path stroke=\\\"#000\\\" stroke-width=\\\"2\\\" d=\\\"M2 64h151M1 0v728M189 49v631M2 727h186M2 1h188M0 364h190\\\"/>\"}}");
        final BusinessCenterStorey businessCenter2Storey2Floor = buildBusinessCenterStorey(businessCenterOffice, "2 этаж");
        businessCenter2Storey2Floor.setMap("{\"svgContainer\":{\"attributes\":{\"viewBox\":\"0 0 190 728\",\"fill\":\"transparent\"},\"bounding\":[[0,0],[728,190]],\"innerHtml\":\"<path stroke=\\\"#000\\\" stroke-width=\\\"2\\\" d=\\\"M2 664h125\\\"/>\\n <path stroke=\\\"#B0B0B0\\\" stroke-width=\\\"2\\\" d=\\\"M16 665v63M46 665v63M76 665v63M61 665v63M31 665v63M16 2v63M46 2v63M76 2v63M61 2v63M31 2v63\\\"/>\\n <path stroke=\\\"#000\\\" stroke-width=\\\"2\\\" d=\\\"M2 64h125M1 0v728M189 2v726M2 727h186M2 1h188M116 369h74M0 369h74M128 609v56M126 63v56M127 610H60M56 555H0M56 183H0M56 493H0M56 305H0M56 431H0M56 243H0M132 493h56M132 305h56M132 431h56M132 243h56M132 555h56M132 181h56M55 383v-13M55 195v-13M133 443v-13M133 255v-13M133 507v-13M133 319v-13M55 507v-13M55 319v-13M55 445v-13M55 257v-13M133 417v13M133 229v13M55 430v-13M55 242v-13M133 492v-13M133 304v-13M133 554v-13M133 368v-13M55 554v-13M55 368v-13M55 492v-13M55 304v-13M133 370v13M133 182v13M127 119H60M20 610H0M21 119H1\\\"/>\"}}");
        final List<BusinessCenterStorey> officeBusinessCenterStoreys =
            businessCenterStoreyRepository.saveAll(List.of(
                businessCenter2Storey1Floor,
                businessCenter2Storey2Floor
            ));


        List<Room> rooms = new ArrayList<>();
        for (BusinessCenterStorey centerStorey : firstBusinessCenterStoreys) {
            rooms.add(buildRoom(centerStorey));
        }
        for (BusinessCenterStorey centerStorey : secondBusinessCenterStoreys) {
            rooms.add(buildRoom(centerStorey));
        }
        final List<Room> officeRooms = new ArrayList<>();
        Room first = buildRoom(businessCenter2Storey1Floor);
        first.setPosition("{\"coords\":[[365,0],[365,190],[663,190],[663,0]]}");
        first.setArea(40.5);
        officeRooms.add(first);
        Room second = buildRoom(businessCenter2Storey1Floor);
        second.setPosition("{\"coords\":[[65,0],[65,190],[365,190],[365,0]]}");
        second.setArea(54.2);
        officeRooms.add(second);
        officeRooms.add(buildRoom(businessCenter2Storey2Floor));
        officeRooms.add(buildRoom(businessCenter2Storey2Floor));
        officeRooms.add(buildRoom(businessCenter2Storey2Floor));
        officeRooms.add(buildRoom(businessCenter2Storey2Floor));
        officeRooms.add(buildRoom(businessCenter2Storey2Floor));
        officeRooms.add(buildRoom(businessCenter2Storey2Floor));

        rooms.addAll(officeRooms);

        rooms = roomRepository.saveAll(rooms);

        List<LeaseContract> leaseContracts = List.of(
            buildLeaseContract(rooms.get(0), lessor, lessee),
            buildLeaseContract(rooms.get(2), lessor, lessee),
            buildLeaseContract(rooms.get(3), lessor, lessee),
            buildLeaseContract(rooms.get(4), lessor, lessee),
            buildLeaseContract(rooms.get(5), lessor, lessee),
            buildLeaseContract(rooms.get(7), lessor, lessee),
            buildLeaseContract(rooms.get(rooms.size() - 1), lessor, lessee),
            buildLeaseContract(rooms.get(rooms.size() - 2), lessor, lessee),
            buildLeaseContract(rooms.get(rooms.size() - 3), lessor, lessee),
            buildLeaseContract(rooms.get(rooms.size() - 5), lessor, lessee),
            buildLeaseContract(rooms.get(rooms.size() - 6), lessor, lessee),
            buildLeaseContract(rooms.get(rooms.size() - 7), lessor, lessee)
        );

        leaseContractRepository.saveAll(leaseContracts);

        for (LeaseContract leaseContract : leaseContracts) {
            Room room = roomRepository.findById(leaseContract.getRoomId()).get();
            room.setLeaseContractId(leaseContract.getId());
            roomRepository.save(room);
        }

        final List<LeaseEvent> leaseEvents = List.of(
            buildLeaseEvent(leaseContracts.get(0), 15000),
            buildLeaseEvent(leaseContracts.get(1), 20000),
            buildLeaseEvent(leaseContracts.get(2), 50000),
            buildLeaseEvent(leaseContracts.get(6), 45000),
            buildLeaseEvent(leaseContracts.get(7), 50000),
            buildLeaseEvent(leaseContracts.get(8), 50000),
            buildLeaseEvent(leaseContracts.get(9), 50000),
            buildLeaseEvent(leaseContracts.get(10), 50000),
            buildLeaseEvent(leaseContracts.get(11), 1000)
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

        final Instant from = Instant.parse("2022-06-01T00:00:00Z");
        final Instant to = Instant.now();

        final List<PowerSocket> powerSockets = new ArrayList<>();
        buildPowerSocketIotData(officeRooms, from, to, powerSockets);

        final List<TempSensor> tempSensors = new ArrayList<>();
        buildTempIotData(officeRooms, from, to, tempSensors);

        final List<SmokeSensor> smokeSensors = new ArrayList<>();
        buildSmokeIotData(officeRooms, from, to, smokeSensors);

        final List<MoveSensor> moveSensors = new ArrayList<>();
        buildMoveIotData(officeRooms, from, to, moveSensors);

        final List<WaterSensor> waterSensors = new ArrayList<>();
        buildWaterIotData(officeRooms, from, to, waterSensors);

        final List<Check> checks = new ArrayList<>();
        for (Room officeRoom : officeRooms) {
            checks.add(buildCheck(officeRoom, 6300));
        }
        checkRepository.saveAll(checks);

        return ResponseEntity.ok("pong");
    }

    private Check buildCheck(final Room room, final Integer cost) {
        Check check = new Check();
        check.setRoomId(room.getId());
        check.setCost(cost);
        check.setTime(Instant.parse("2023-01-01T00:00:00Z"));

        return check;
    }


    private void buildWaterIotData(List<Room> officeRooms, Instant from, Instant to, List<WaterSensor> waterSensors) {
        for (final Room officeRoom : officeRooms) {
            waterSensors.addAll(buildWaterSensorEquipment(officeRoom));
        }
        waterSensorRepository.saveAll(waterSensors);
        final List<WaterSensorTelemetry> waterTelemetries = new ArrayList<>();
        for (final WaterSensor waterSensor : waterSensors) {
            for (long i = from.getEpochSecond(); i < to.getEpochSecond(); i += 3600) {
                waterTelemetries.add(
                    buildWaterSensorTelemetry(waterSensor,
                        Instant.ofEpochSecond(i),
                        i == 4)
                );
            }
        }
        waterSensorTelemetryRepository.saveAll(waterTelemetries);
    }

    private void buildMoveIotData(List<Room> officeRooms, Instant from, Instant to, List<MoveSensor> moveSensors) {
        for (final Room officeRoom : officeRooms) {
            moveSensors.add(buildMoveSensorEquipment(officeRoom));
        }
        moveSensorRepository.saveAll(moveSensors);
        final List<MoveSensorTelemetry> smokeSocketTelemetries = new ArrayList<>();
        for (final MoveSensor moveSensor : moveSensors) {
            for (long i = from.getEpochSecond(); i < to.getEpochSecond(); i += 3600) {
                smokeSocketTelemetries.add(
                    buildMoveSensorTelemetry(moveSensor,
                        Instant.ofEpochSecond(i),
                        i == 4)
                );
            }
        }
        moveSensorTelemetryRepository.saveAll(smokeSocketTelemetries);
    }

    private void buildPowerSocketIotData(List<Room> officeRooms, Instant from, Instant to, List<PowerSocket> powerSockets) {
        for (final Room officeRoom : officeRooms) {
            powerSockets.add(buildPowerSocketEquipment(officeRoom));
        }
        powerSocketRepository.saveAll(powerSockets);

        final List<PowerSocketTelemetry> powerSocketTelemetries = new ArrayList<>();

        for (final PowerSocket powerSocket : powerSockets) {
            for (long i = from.getEpochSecond(); i < to.getEpochSecond(); i += 3600) {
                powerSocketTelemetries.add(buildPowerSocketTelemetry(powerSocket,
                    Instant.ofEpochSecond(i),
                    powerSocket.getRoomId() == 10
                ));
            }
        }

        powerSocketTelemetryRepository.saveAll(powerSocketTelemetries);
    }

    private void buildTempIotData(List<Room> officeRooms, Instant from, Instant to, List<TempSensor> tempSensors) {
        for (final Room officeRoom : officeRooms) {
            tempSensors.add(buildTempSensorEquipment(officeRoom));
        }
        tempSensorRepository.saveAll(tempSensors);
        final List<TempSensorTelemetry> tempSocketTelemetries = new ArrayList<>();
        for (final TempSensor tempSensor : tempSensors) {
            for (long i = from.getEpochSecond(); i < to.getEpochSecond(); i += 3600) {
                tempSocketTelemetries.add(
                    buildTempSensorTelemetry(tempSensor,
                        Instant.ofEpochSecond(i),
                        tempSensor.getRoomId() == 9)
                );
            }
        }

        tempSensorTelemetryRepository.saveAll(tempSocketTelemetries);
    }

    private void buildSmokeIotData(List<Room> officeRooms, Instant from, Instant to, List<SmokeSensor> smokeSensors) {
        for (final Room officeRoom : officeRooms) {
            smokeSensors.add(buildSmokeSensorEquipment(officeRoom));
        }
        smokeSensorRepository.saveAll(smokeSensors);
        final List<SmokeSensorTelemetry> smokeSocketTelemetries = new ArrayList<>();
        for (final SmokeSensor smokeSensor : smokeSensors) {
            for (long i = from.getEpochSecond(); i < to.getEpochSecond(); i += 3600) {
                smokeSocketTelemetries.add(
                    buildSmokeSensorTelemetry(smokeSensor,
                        Instant.ofEpochSecond(i),
                        i == 3)
                );
            }
        }
        smokeSensorTelemetryRepository.saveAll(smokeSocketTelemetries);
    }

    private static List<WaterSensor> buildWaterSensorEquipment(final Room room) {
        final WaterSensor waterSensorCold = new WaterSensor();
        waterSensorCold.setRoomId(room.getId());
        waterSensorCold.setModel(WaterSensorModel.MOCK);
        waterSensorCold.setWaterTemp(WaterTemp.COLD);

        final WaterSensor waterSensorHot = new WaterSensor();
        waterSensorHot.setRoomId(room.getId());
        waterSensorHot.setModel(WaterSensorModel.MOCK);
        waterSensorCold.setWaterTemp(WaterTemp.HOT);

        return List.of(waterSensorCold, waterSensorHot);
    }

    private static MoveSensor buildMoveSensorEquipment(final Room room) {
        final MoveSensor smokeSensor = new MoveSensor();
        smokeSensor.setRoomId(room.getId());
        smokeSensor.setModel(MoveSensorModel.MOCK);

        return smokeSensor;
    }

    private static WaterSensorTelemetry buildWaterSensorTelemetry(final WaterSensor moveSensor,
                                                                  final Instant time,
                                                                  final boolean max) {
        final WaterSensorTelemetry waterSensorTelemetry = new WaterSensorTelemetry();
        waterSensorTelemetry.setEquipmentId(moveSensor.getId());
        waterSensorTelemetry.setEquipmentType(EquipmentType.WATER_SENSOR);
        waterSensorTelemetry.setFixTime(time);

        waterSensorTelemetry.setWaterVolume((int) (time.getEpochSecond() / 3600));
        if (max) {
            waterSensorTelemetry.setWaterVolume((int) (waterSensorTelemetry.getWaterVolume() * 1.41));
        }

        return waterSensorTelemetry;
    }

    private static MoveSensorTelemetry buildMoveSensorTelemetry(final MoveSensor moveSensor,
                                                                final Instant time,
                                                                final boolean move) {
        final MoveSensorTelemetry tempSensorTelemetry = new MoveSensorTelemetry();
        tempSensorTelemetry.setEquipmentId(moveSensor.getId());
        tempSensorTelemetry.setEquipmentType(EquipmentType.SMOKE_SENSOR);
        tempSensorTelemetry.setFixTime(time);

        tempSensorTelemetry.setMove(move);

        return tempSensorTelemetry;
    }

    private static SmokeSensorTelemetry buildSmokeSensorTelemetry(final SmokeSensor smokeSensor,
                                                                  final Instant time,
                                                                  final boolean fire) {
        final SmokeSensorTelemetry tempSensorTelemetry = new SmokeSensorTelemetry();
        tempSensorTelemetry.setEquipmentId(smokeSensor.getId());
        tempSensorTelemetry.setEquipmentType(EquipmentType.SMOKE_SENSOR);
        tempSensorTelemetry.setFixTime(time);

        tempSensorTelemetry.setFire(fire);

        return tempSensorTelemetry;
    }

    private static SmokeSensor buildSmokeSensorEquipment(final Room room) {
        final SmokeSensor smokeSensor = new SmokeSensor();
        smokeSensor.setRoomId(room.getId());
        smokeSensor.setModel(SmokeSensorType.MOCK);

        return smokeSensor;
    }

    private static TempSensorTelemetry buildTempSensorTelemetry(final TempSensor tempSensor,
                                                                final Instant time,
                                                                final boolean hot) {
        final TempSensorTelemetry tempSensorTelemetry = new TempSensorTelemetry();
        tempSensorTelemetry.setEquipmentId(tempSensor.getId());
        tempSensorTelemetry.setEquipmentType(EquipmentType.TEMP);
        tempSensorTelemetry.setFixTime(time);

        Random random = new Random(time.getEpochSecond());
        int low = 45;
        int high = 74;


        int result = random.nextInt(high - low) + low;

        if (tempSensorTelemetry.hashCode() % 2 == 0) {
            Random random1 = new Random(time.getEpochSecond());

            result = result + random1.nextInt(500 - 200) + 200;
        }
        if (tempSensorTelemetry.hashCode() % 3 == 0) {
            Random random1 = new Random(time.getEpochSecond());

            result = result - random1.nextInt(100 - 80) + 80;
        }
        if (tempSensorTelemetry.hashCode() % 50 == 0) {
            result = (int) (result * 0.7);
        }

        tempSensorTelemetry.setTemp(hot ? 300 + result : 220 + result);

        return tempSensorTelemetry;
    }

    private static TempSensor buildTempSensorEquipment(final Room room) {
        final TempSensor powerSocket = new TempSensor();
        powerSocket.setRoomId(room.getId());
        powerSocket.setModel(TempSensorModel.MOCK);

        return powerSocket;
    }

    private static PowerSocketTelemetry buildPowerSocketTelemetry(final PowerSocket powerSocket, final Instant time, boolean max) {
        final PowerSocketTelemetry powerSocketTelemetry = new PowerSocketTelemetry();
        powerSocketTelemetry.setEquipmentId(powerSocket.getId());
        powerSocketTelemetry.setEquipmentType(EquipmentType.POWER_SOCKET);
        powerSocketTelemetry.setFixTime(time);

        powerSocketTelemetry.setWatt((int) (time.getEpochSecond() * 2 / 3600));
        if (max) {
            powerSocketTelemetry.setWatt((int) (powerSocketTelemetry.getWatt() * 1.4));
        }

        return powerSocketTelemetry;
    }

    private static PowerSocket buildPowerSocketEquipment(final Room room) {
        final PowerSocket powerSocket = new PowerSocket();
        powerSocket.setRoomId(room.getId());
        powerSocket.setModel(PowerSocketModel.MOCK);

        return powerSocket;
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

        leaseContract.setRent(room.getId() == 15 ? 5000 : 50000);
        leaseContract.setStartTime(Instant.parse("2023-01-01T00:00:00Z"));
        leaseContract.setEndTime(Instant.parse("2024-01-01T00:00:00Z"));
        return leaseContract;
    }

    private static Room buildRoom(final BusinessCenterStorey businessCenterStorey) {
        final Room room = new Room();
        room.setName("Комната " + roomCounter.incrementAndGet());
        room.setBusinessCenterStoreyId(businessCenterStorey.getId());
        room.setRequiredTemp(270);
        room.setAllowablePowerConsumption(1000);
        room.setArea(40.0);
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

CREATE TABLE IF NOT EXISTS eventdto (
                                     id SERIAL primary key,
                                     eventId uuid,
                                     transId VARCHAR,
                                     transTms VARCHAR,
                                     rcNum VARCHAR,
                                     clientId VARCHAR,
                                     eventCnt INTEGER,
                                     locationCd VARCHAR,
                                     locationId1 VARCHAR,
                                     locationId2 VARCHAR,
                                     addrNbr VARCHAR
);
COMMIT;

INSERT INTO eventdto
(
        transId,
        transTms,
        rcNum,
        clientId,
        eventCnt,
        locationCd,
        locationId1,
        locationId2,
        addrNbr
        )
VALUES
    (
         '0000abf8-d1f5-4536-8fb0-36fe934b1f45',
         '20151022102011927EDT',
         '10002',
         'RPS-00006',
         5,
         'DESTINATION',
         'T8C',
         '1J7',
         '00000001'
    );
COMMIT;
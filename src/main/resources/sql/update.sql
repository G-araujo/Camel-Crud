update clientes
set
    transId = :#transId,
    transTms = :#transTms,
    rcNum = :#rcNum,
    clientId = :#clientId,
    eventCnt = :#eventCnt,
    locationCd = :#locationCd,
    locationId1 = :#locationId1,
    locationId2 = :#locationId2,
    addrNbr = :addrNbr
where
        id = :#id
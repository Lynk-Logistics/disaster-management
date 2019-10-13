package com.assist.api.util

import java.time.LocalDate
import java.time.ZoneId

class DateUtils {

    static Date getDateBefore(int noOfDays){
        LocalDate date = LocalDate.now().minusDays(noOfDays)
        ZoneId defaultZoneId = ZoneId.systemDefault()
        return Date.from(date.atStartOfDay(defaultZoneId).toInstant())
    }
}

package com.pogotracker.back.pogotracker.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.pogotracker.back.pogotracker.model.XPRecord;

public class XPTrackerUtils {
	public static void calculateAndSetAverageDailyXPDifference(List<XPRecord> xpRecords) {
        if (xpRecords.isEmpty()) {
            return;
        }

        XPRecord lastXPRecord = xpRecords.get(xpRecords.size() - 1);

        int sum = 0;
        for (int i = 0; i < xpRecords.size() - 1; i++) {
            sum += xpRecords.get(i).getDailyXPDifference();
        }

        if (!xpRecords.isEmpty()) {
            sum += lastXPRecord.getDailyXPDifference();
            double average = (double) sum / xpRecords.size();
            BigDecimal bd = BigDecimal.valueOf(average);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            lastXPRecord.setAvgDailyXp(bd);
        }
    }
	
}

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

		    int sum = 0;
		    for (XPRecord record : xpRecords) {
		        sum += record.getDailyXPDifference();
		    }

		    double average = (double) sum / xpRecords.size();

		    BigDecimal bd = BigDecimal.valueOf(average).setScale(2, RoundingMode.HALF_UP);

		    XPRecord lastXPRecord = xpRecords.get(xpRecords.size() - 1);
		    lastXPRecord.setAvgDailyXp(bd);
    }
	
	public static void recalculateDailyXPDifferences(List<XPRecord> xpRecords) {
        for (int i = 1; i < xpRecords.size(); i++) {
            XPRecord currentRecord = xpRecords.get(i);
            XPRecord previousRecord = xpRecords.get(i - 1);

            int dailyDifference = currentRecord.getTotalXP() - previousRecord.getTotalXP();
            currentRecord.setDailyXPDifference(dailyDifference);
        }
    }
}

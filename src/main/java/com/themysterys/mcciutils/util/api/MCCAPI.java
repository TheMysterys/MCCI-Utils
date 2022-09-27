package com.themysterys.mcciutils.util.api;

import com.google.gson.JsonObject;


import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MCCAPI {

    public Calendar mccDate;

    public MCCAPI() {
        JsonObject json;
        try {
            json = APIUtils.readJsonObjectFromUrl("https://api.mcchampionship.com/v1/event");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (json == null) {
            return;
        }
        // Convert string timestamp to date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(Instant.parse(json.get("data").getAsJsonObject().get("date").getAsString())));
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        mccDate = calendar;
    }
}

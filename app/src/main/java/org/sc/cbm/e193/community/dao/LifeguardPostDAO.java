package org.sc.cbm.e193.community.dao;

import com.google.android.gms.maps.model.LatLng;

import org.sc.cbm.e193.community.pojo.LifeguardTower;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LifeguardPostDAO {
    public static List<LifeguardTower> getAll() {
        //TODO JUST A MOCK HERE

        List lp = new ArrayList();
        lp.add(new LifeguardTower("Florianópolis", "Jurerê",
                new LatLng(-27.438172, -48.483922),
                null, null,
                5, Calendar.getInstance().getTime(),
                LifeguardTower.HazardFlag.GREEN,
                LifeguardTower.Weather.CLEAN,
                LifeguardTower.JellyFish.MUCH));

        lp.add(new LifeguardTower("Florianópolis", "Canasvieiras",
                new LatLng(-27.427962, -48.466434),
                null, null,
                5, Calendar.getInstance().getTime(),
                LifeguardTower.HazardFlag.GREEN,
                LifeguardTower.Weather.CLEAN,
                LifeguardTower.JellyFish.LITTLE));

        lp.add(new LifeguardTower("Florianópolis", "Canasvieiras",
                new LatLng(-27.427210, -48.452111),
                null, null,
                5, Calendar.getInstance().getTime(),
                LifeguardTower.HazardFlag.YELLOW,
                LifeguardTower.Weather.CLEAN,
                LifeguardTower.JellyFish.LITTLE));

        lp.add(new LifeguardTower("Florianópolis", "Ponta das Canas",
                new LatLng(-27.411087, -48.428100),
                null, null,
                5, Calendar.getInstance().getTime(),
                LifeguardTower.HazardFlag.BLACK,
                LifeguardTower.Weather.CLOUDY,
                LifeguardTower.JellyFish.NONE));

        lp.add(new LifeguardTower("Florianópolis", "Ponta das Canas",
                new LatLng(-27.398619, -48.429731),
                null, null,
                5, Calendar.getInstance().getTime(),
                LifeguardTower.HazardFlag.YELLOW,
                LifeguardTower.Weather.CLOUDY,
                LifeguardTower.JellyFish.NONE));

        lp.add(new LifeguardTower("Florianópolis", "Brava",
                new LatLng(-27.396553, -48.415182),
                null, null,
                5, Calendar.getInstance().getTime(),
                LifeguardTower.HazardFlag.RED,
                LifeguardTower.Weather.RAINY,
                LifeguardTower.JellyFish.MUCH));

        lp.add(new LifeguardTower("Florianópolis", "Brava",
                new LatLng(-27.401706, -48.413701),
                null, null,
                5, Calendar.getInstance().getTime(),
                LifeguardTower.HazardFlag.RED,
                LifeguardTower.Weather.RAINY,
                LifeguardTower.JellyFish.MUCH));
        return lp;
    }
}

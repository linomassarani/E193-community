package org.sc.cbm.e193.community.dao;

import com.google.android.gms.maps.model.LatLng;

import org.sc.cbm.e193.community.pojo.HazardFlag;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HazardFlagDAO {
    public static List<HazardFlag> getAllFlags() {
        //TODO JUST A MOCK HERE
        List hf = new ArrayList();
        hf.add(new HazardFlag(HazardFlag.Color.GREEN, Calendar.getInstance().getTime(),
                "Florianópolis", "Jurerê", "FJ2", new LatLng(-27.438172, -48.483922)));
        hf.add(new HazardFlag(HazardFlag.Color.GREEN, Calendar.getInstance().getTime(),
                "Florianópolis", "Canasvieiras", "FC1", new LatLng(-27.427962, -48.466434)));
        hf.add(new HazardFlag(HazardFlag.Color.YELLOW, Calendar.getInstance().getTime(),
                "Florianópolis", "Canasvieiras", "FC2", new LatLng(-27.427210, -48.452111)));
        hf.add(new HazardFlag(HazardFlag.Color.GREEN, Calendar.getInstance().getTime(),
                "Florianópolis", "Ponta das Canas", "FP1", new LatLng(-27.411087, -48.428100)));
        hf.add(new HazardFlag(HazardFlag.Color.BLACK, Calendar.getInstance().getTime(),
                "Florianópolis", "Ponta das Canas", "FP1", new LatLng(-27.398619, -48.429731)));
        hf.add(new HazardFlag(HazardFlag.Color.RED, Calendar.getInstance().getTime(),
                "Florianópolis", "Brava", "FB1", new LatLng(-27.396553, -48.415182)));
        hf.add(new HazardFlag(HazardFlag.Color.RED, Calendar.getInstance().getTime(),
                "Florianópolis", "Brava", "FB2", new LatLng(-27.401706, -48.413701)));

        return hf;
    }
}

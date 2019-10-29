package com.advanced.taracat.comparing;

import com.advanced.taracat.dao.entity.Tarakan;

import java.util.Comparator;

public class TarakanComparator implements Comparator<Tarakan> {

    @Override
    public int compare(Tarakan o1, Tarakan o2) {
        if (o1.getExperience() > o2.getExperience()){
            return -1;
        } else
        if (o1.getExperience() < o2.getExperience()){
            return 1;
        } else
        return o1.getTarname().compareTo(o2.getTarname());
    }
}
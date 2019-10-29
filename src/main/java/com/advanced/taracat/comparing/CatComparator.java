package com.advanced.taracat.comparing;

import com.advanced.taracat.dao.entity.Cat;

import java.util.Comparator;

public class CatComparator implements Comparator<Cat> {
    @Override
    public int compare(Cat o1, Cat o2) {
        if (o1.getCat_level() > o2.getCat_level()){
            return -1;
        } else
        if (o1.getCat_level() < o2.getCat_level()){
            return 1;
        } else
            return o1.getName().compareTo(o2.getName());
    }
}

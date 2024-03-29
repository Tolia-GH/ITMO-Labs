package com.example.demo_back.configuration.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class DotBean implements Serializable {
    private List<Dot> dotsList = new ArrayList<>();

    public int getNum(){
        return this.dotsList.size();
    }

    public Dot addDot(Dot dot) {
        this.dotsList.add(dot);
        return dot;
    }

    public Dot deleteDot(Dot dot) {
        this.dotsList.remove(dot);
        return dot;
    }
}

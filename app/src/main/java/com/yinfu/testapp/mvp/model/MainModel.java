package com.yinfu.testapp.mvp.model;

import com.yinfu.common.mvp.BaseModel;
import com.yinfu.testapp.mvp.contract.MainContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainModel extends BaseModel implements MainContract.Model {

    @Override
    public List<String> getMainData() {
        List<String> data = new ArrayList<>();
        int min = 1;
        int max = 9;
        Random random = new Random();
        data.add("1--" + random.nextInt(max) % (max - min + 1) + min);
        data.add("2--" + random.nextInt(max) % (max - min + 1) + min);
        data.add("3--" + random.nextInt(max) % (max - min + 1) + min);
        data.add("4--" + random.nextInt(max) % (max - min + 1) + min);
        data.add("5--" + random.nextInt(max) % (max - min + 1) + min);
        data.add("6--" + random.nextInt(max) % (max - min + 1) + min);
        data.add("7--" + random.nextInt(max) % (max - min + 1) + min);
        return data;
    }
}

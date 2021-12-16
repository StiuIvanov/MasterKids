package com.example.demo.service;

import com.example.demo.model.view.StatsView;

public interface StatsService {
    void onRequest();

    StatsView getStats();
}

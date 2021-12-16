package com.example.demo.model.binding;

public class ActivityFormBindingModel {
    private String activity;
    private Long id;

    public ActivityFormBindingModel() {
    }

    public String getActivity() {
        return activity;
    }

    public ActivityFormBindingModel setActivity(String activity) {
        this.activity = activity;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ActivityFormBindingModel setId(Long id) {
        this.id = id;
        return this;
    }
}

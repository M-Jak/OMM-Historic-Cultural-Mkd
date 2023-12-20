package com.omm.prototype.service;

import com.omm.prototype.model.Pin;

import java.util.List;

public interface PinService {
    List<Pin> getAll();
    List<Pin> getAllByType(String type);
}

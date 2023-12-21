package com.omm.prototype.service.impl;

import com.omm.prototype.model.Pin;
import com.omm.prototype.repository.PinRepository;
import com.omm.prototype.service.PinService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PinServiceImpl implements PinService {

    private final PinRepository repository;
    @Override
    public List<Pin> getAll() {
        return repository.getAll();
    }

    @Override
    public List<Pin> getAllByType(String type) {
        return repository.getAllByType(type);
    }

    @Override
    public List<Pin> getAllFiltered( String text ) {
        return repository.getAll().stream()
                .filter(p-> {
                    if ( p.getName().toLowerCase().contains(text.toLowerCase()) )
                        return true;
                    if ( p.getEn_name().toLowerCase().contains(text.toLowerCase()) )
                        return true;
                    return p.getType().toLowerCase().contains(text.toLowerCase());
                })
                .toList();
    }

}

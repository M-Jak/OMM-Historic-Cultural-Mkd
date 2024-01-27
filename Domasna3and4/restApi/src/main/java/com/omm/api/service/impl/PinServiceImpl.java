package com.omm.api.service.impl;

import com.omm.api.model.Pin;
import com.omm.api.repository.PinRepository;
import com.omm.api.service.PinService;
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
    public List<Pin> getAllFiltered( String text, String type ) {
        if(type == null) {
            return repository.getAll().stream()
                    .filter(p -> {
                if ( p.getName().toLowerCase().contains(text.toLowerCase()) )
                    return true;
                return p.getEn_name().toLowerCase().contains(text.toLowerCase());
            }).toList();
        }
        List<Pin> pinsByType = repository.getAllByType(type);
        return pinsByType.stream()
                .filter(p -> {
                    if ( p.getName().toLowerCase().contains(text.toLowerCase()) )
                        return true;
                    return p.getEn_name().toLowerCase().contains(text.toLowerCase());
                }).toList();
    }

}

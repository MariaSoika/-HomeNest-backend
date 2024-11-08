package org.example.coursework.service;


import lombok.AllArgsConstructor;
import org.example.coursework.entity.Apartment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ApartmentService {

    //private final Apartment apartment;

}

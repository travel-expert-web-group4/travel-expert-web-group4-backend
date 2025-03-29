package org.group4.travelexpertsapi.service;


import org.group4.travelexpertsapi.entity.Agency;
import org.group4.travelexpertsapi.repository.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgencyService {

    @Autowired
    private AgencyRepository agencyRepository;

    public List<Agency> getAllAgencies() {
        return agencyRepository.findAll();
    }

    public Optional<Agency> getAgencyById(Integer id) {
        return agencyRepository.findById(id);
    }

    public Agency createAgency(Agency agency) {
        return agencyRepository.save(agency);
    }

    public Agency updateAgency(Integer id, Agency updatedAgency) {
        return agencyRepository.findById(id).map(agency -> {
            agency.setAgncyaddress(updatedAgency.getAgncyaddress());
            agency.setAgncycity(updatedAgency.getAgncycity());
            agency.setAgncyprov(updatedAgency.getAgncyprov());
            agency.setAgncypostal(updatedAgency.getAgncypostal());
            agency.setAgncycountry(updatedAgency.getAgncycountry());
            agency.setAgncyphone(updatedAgency.getAgncyphone());
            agency.setAgncyfax(updatedAgency.getAgncyfax());
            return agencyRepository.save(agency);
        }).orElse(null);
    }

    public void deleteAgency(Integer id) {
        agencyRepository.deleteById(id);
    }
}


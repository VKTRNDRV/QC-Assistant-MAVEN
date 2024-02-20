package com.example.qcassistantmaven.service.sponsor;

import com.example.qcassistantmaven.domain.dto.sponsor.SponsorAddDto;
import com.example.qcassistantmaven.domain.dto.sponsor.SponsorDisplayDto;
import com.example.qcassistantmaven.domain.dto.sponsor.SponsorEditDto;
import com.example.qcassistantmaven.domain.entity.sponsor.BaseSponsor;
import com.example.qcassistantmaven.domain.entity.sponsor.MedidataSponsor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public abstract class BaseSponsorService {

    protected ModelMapper modelMapper;

    @Autowired
    public BaseSponsorService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    protected void validateNameNotBlank(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException("Name cannot be blank");
        }
    }

    public abstract <T extends BaseSponsor> List<T> getEntities();

    public SponsorEditDto getSponsorEditById(Long id) {
        return this.modelMapper.map(
                getSponsorRepository().findById(id)
                        .orElseThrow(),
                SponsorEditDto.class);
    }

    public List<SponsorDisplayDto> displayAllSponsors() {
        return this.getEntities().stream()
                .map(s -> this.modelMapper
                        .map(s, SponsorDisplayDto.class))
                .sorted((s1, s2) -> s1.getName().compareTo(s2.getName()))
                .collect(Collectors.toList());
    }

    protected void validateAddSponsor(SponsorAddDto sponsorAddDto) {
        sponsorAddDto.trimStringFields();
        String name = sponsorAddDto.getName();
        validateNameNotBlank(name);
        validateUniqueName(name);
    }

    protected void validateEditSponsor(SponsorEditDto sponsorEditDto) {
        sponsorEditDto.trimStringFields();
        String name = sponsorEditDto.getName();
        this.validateNameNotBlank(name);

        // if name changed - validate unique
        if (!getSponsorRepository().findById(sponsorEditDto.getId()).get()
                .getName().trim()
                .equals(sponsorEditDto.getName())) {
            validateUniqueName(name);
        }
    }

    protected void validateUniqueName(String name) {
        if (findFirstByName(name).isPresent()) {
            throw new RuntimeException("Sponsor \"" + name + "\" already present");
        }
    }

    public abstract <T extends BaseSponsor> Optional<T> findFirstByName(String name);

    protected abstract <T extends BaseSponsor> CrudRepository<T, Long> getSponsorRepository();

    public abstract void addSponsor(SponsorAddDto sponsorAddDto);

    public abstract void editSponsor(SponsorEditDto sponsorEditDto);

    public <T extends BaseSponsor> void saveAll(Collection<T> sponsors){
        getSponsorRepository().saveAll(sponsors);
    }

    public abstract  <T extends BaseSponsor> T getUnknownSponsor();
}


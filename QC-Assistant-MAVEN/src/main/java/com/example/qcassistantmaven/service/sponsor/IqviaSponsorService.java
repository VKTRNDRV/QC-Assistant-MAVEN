package com.example.qcassistantmaven.service.sponsor;

import com.example.qcassistantmaven.domain.dto.sponsor.SponsorAddDto;
import com.example.qcassistantmaven.domain.dto.sponsor.SponsorEditDto;
import com.example.qcassistantmaven.domain.entity.BaseEntity;
import com.example.qcassistantmaven.domain.entity.sponsor.BaseSponsor;
import com.example.qcassistantmaven.domain.entity.sponsor.IqviaSponsor;
import com.example.qcassistantmaven.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistantmaven.repository.sponsor.IqviaSponsorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class IqviaSponsorService extends BaseSponsorService{

    private IqviaSponsorRepository sponsorRepository;

    @Autowired
    public IqviaSponsorService(ModelMapper modelMapper, IqviaSponsorRepository sponsorRepository) {
        super(modelMapper);
        this.sponsorRepository = sponsorRepository;
    }

    @Override
    public void addSponsor(SponsorAddDto sponsorAddDto) {
        validateAddSponsor(sponsorAddDto);
        IqviaSponsor sponsor = this.modelMapper.map(
                sponsorAddDto, IqviaSponsor.class);
        this.sponsorRepository.save(sponsor);
    }

    @Override
    public List<IqviaSponsor> getEntities() {
        return this.sponsorRepository
                .findAllByNameNot(BaseEntity.UNKNOWN);
    }

    public SponsorEditDto getSponsorEditById(Long id) {
        return this.modelMapper.map(
                this.sponsorRepository.findById(id)
                        .orElseThrow(),
                SponsorEditDto.class);
    }

    @Override
    public Optional<IqviaSponsor> findFirstByName(String name) {
        return this.sponsorRepository.findFirstByName(name);
    }

    @Override
    public void editSponsor(SponsorEditDto sponsorEditDto) {
        validateEditSponsor(sponsorEditDto);
        IqviaSponsor sponsor = this.modelMapper.map(sponsorEditDto, IqviaSponsor.class);
        this.sponsorRepository.save(sponsor);
    }

//    @Override
//    public Optional<IqviaSponsor> getSponsorByName(String name) {
//        return this.sponsorRepository.findFirstByName(name);
//    }

    @Override
    public <T extends BaseSponsor> void saveAll(Collection<T> sponsors) {
        this.sponsorRepository.saveAll((Collection<IqviaSponsor>) sponsors);
    }

    @Override
    public IqviaSponsor getUnknownSponsor() {
        return this.sponsorRepository.findFirstByName(BaseEntity.UNKNOWN).get();
    }



    @Override
    protected IqviaSponsorRepository getSponsorRepository() {
        return this.sponsorRepository;
    }
}

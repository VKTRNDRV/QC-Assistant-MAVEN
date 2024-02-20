package com.example.qcassistantmaven.unit.entityServices.sponsor;

import com.example.qcassistantmaven.domain.dto.sponsor.SponsorAddDto;
import com.example.qcassistantmaven.domain.dto.sponsor.SponsorDisplayDto;
import com.example.qcassistantmaven.domain.dto.sponsor.SponsorEditDto;
import com.example.qcassistantmaven.domain.entity.BaseEntity;
import com.example.qcassistantmaven.domain.entity.sponsor.MedableSponsor;
import com.example.qcassistantmaven.repository.sponsor.MedableSponsorRepository;
import com.example.qcassistantmaven.service.sponsor.MedableSponsorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MedableSponsorServiceTests {

    private MedableSponsorService sponsorService;

    private MedableSponsorRepository sponsorRepository;

    @Autowired
    public MedableSponsorServiceTests(MedableSponsorService sponsorService,
                                      MedableSponsorRepository sponsorRepository) {
        this.sponsorService = sponsorService;
        this.sponsorRepository = sponsorRepository;
    }

    @Test
    public void getEntities_DoesNotReturnUNKNOWN(){
        List<MedableSponsor> sponsors = this
                .sponsorService.getEntities();

        for(MedableSponsor sponsor : sponsors){
            if(sponsor.getName().equals(BaseEntity.UNKNOWN)){
                Assertions.fail("UNKNOWN sponsor found");
            }
        }

        Assertions.assertTrue(true);
    }

    @Test
    public <T> void displayAllSponsors_ReturnsCorrectTypeAndCount(){
        List<T> fromService = (List<T>) this.sponsorService.displayAllSponsors();

        Assertions.assertEquals(fromService.size(),
                sponsorRepository.count() - 1);

        if(!fromService.isEmpty()){
            Assertions.assertEquals(fromService.get(0).getClass(),
                    SponsorDisplayDto.class);
        }
    }

    @Test
    public void getSponsorEditById_ReturnsCorrectSponsor(){
        MedableSponsor sponsor = this.sponsorRepository
                .findFirstByName(BaseEntity.UNKNOWN).get();

        SponsorEditDto fromService = this.sponsorService
                .getSponsorEditById(sponsor.getId());

        Assertions.assertEquals(sponsor.getName(), fromService.getName());
    }

    @Test
    public void testAddSponsorWithBlankName() {
        SponsorAddDto sponsorAddDto = new SponsorAddDto()
                .setName("  ");

        Assertions.assertThrows(RuntimeException.class, () ->
                sponsorService.addSponsor(sponsorAddDto));
    }

    @Test
    public void testAddSponsorWithExistingName() {
        SponsorAddDto sponsorAddDto = new SponsorAddDto()
                .setName(BaseEntity.UNKNOWN);


        Assertions.assertThrows(RuntimeException.class, () ->
                sponsorService.addSponsor(sponsorAddDto));
    }

    @Test
    public void testEditSponsorWithBlankName() {
        SponsorEditDto editDto = new SponsorEditDto()
                .setName("  ");

        Assertions.assertThrows(RuntimeException.class, () ->
                sponsorService.editSponsor(editDto));
    }
}

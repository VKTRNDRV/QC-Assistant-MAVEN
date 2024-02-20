package com.example.qcassistantmaven.unit.entityServices;

import com.example.qcassistantmaven.domain.dto.destination.DestinationAddDto;
import com.example.qcassistantmaven.domain.dto.destination.DestinationDisplayDto;
import com.example.qcassistantmaven.domain.dto.destination.DestinationEditDto;
import com.example.qcassistantmaven.domain.dto.destination.DestinationNameDto;
import com.example.qcassistantmaven.domain.entity.BaseEntity;
import com.example.qcassistantmaven.domain.entity.destination.Destination;
import com.example.qcassistantmaven.service.DestinationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DestinationServiceTests {

    private DestinationService destinationService;

    @Autowired
    public DestinationServiceTests(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @Test
    public void getEntities_DoesNotReturnUNKNOWN(){
        List<Destination> destinations = this
                .destinationService.getEntities();

        for(Destination destination : destinations){
            if(destination.getName().equals(BaseEntity.UNKNOWN)){
                Assertions.fail("UNKNOWN destination found");
            }
        }

        Assertions.assertTrue(true);
    }

    @Test
    public void getUnknownDestinationEntity_ReturnsUNKNOWN(){
        Destination destination = this.destinationService
                .getUnknownDestinationEntity();

        Assertions.assertEquals(BaseEntity.UNKNOWN,
                destination.getName());
    }

    @Test
    public <T> void displayDestinations_ReturnsCorrectClassAndCount(){
        List<T> returnedObjects = (List<T>) this
                .destinationService.displayDestinations();

        Assertions.assertEquals(
                returnedObjects.size(),
                destinationService.getEntities().size());

        if(!returnedObjects.isEmpty()){
            Assertions.assertEquals(
                    returnedObjects.get(0).getClass(),
                    DestinationDisplayDto.class);
        }
    }

    @Test
    public <T> void getTagDestinations_ReturnsCorrectClassAndCount(){
        List<T> returnedObjects = (List<T>) this
                .destinationService.getTagDestinations();

        Assertions.assertEquals(
                returnedObjects.size(),
                destinationService.getEntities().size());

        if(!returnedObjects.isEmpty()){
            Assertions.assertEquals(
                    returnedObjects.get(0).getClass(),
                    DestinationNameDto.class);
        }
    }

    @Test
    public void testAddDestinationWithNoLanguages() {
        DestinationAddDto destinationAddDto = new DestinationAddDto();
        destinationAddDto.setSelectedLanguages(new ArrayList<>());

        Assertions.assertThrows(RuntimeException.class, () ->
                destinationService.addDestination(destinationAddDto));
    }

    @Test
    public void testAddDestinationWithBlankName() {
        DestinationAddDto destinationAddDto = new DestinationAddDto()
                .setName("  ")
                .setSelectedLanguages(List.of("Lang1", "Lang2"));

        Assertions.assertThrows(RuntimeException.class, () ->
                destinationService.addDestination(destinationAddDto));
    }

    @Test
    public void testAddDestinationWithExistingName() {
        DestinationAddDto destinationAddDto = new DestinationAddDto()
                .setName(BaseEntity.UNKNOWN)
                .setSelectedLanguages(List.of("Lang1", "Lang2"));


        Assertions.assertThrows(RuntimeException.class, () ->
                destinationService.addDestination(destinationAddDto));
    }

    @Test
    public void testEditDestinationWithNoLanguages() {
        DestinationEditDto editDto = new DestinationEditDto();
        editDto.setSelectedLanguages(new ArrayList<>());

        Assertions.assertThrows(RuntimeException.class, () ->
                destinationService.editDestination(editDto));
    }

    @Test
    public void testEditDestinationWithBlankName() {
        DestinationEditDto editDto = new DestinationEditDto()
                .setName("  ")
                .setSelectedLanguages(List.of("Lang1", "Lang2"));

        Assertions.assertThrows(RuntimeException.class, () ->
                destinationService.editDestination(editDto));
    }
}

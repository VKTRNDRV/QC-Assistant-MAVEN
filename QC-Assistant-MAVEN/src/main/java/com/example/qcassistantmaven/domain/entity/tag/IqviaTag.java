package com.example.qcassistantmaven.domain.entity.tag;

import com.example.qcassistantmaven.domain.entity.destination.Destination;
import com.example.qcassistantmaven.domain.entity.study.IqviaStudy;
import com.example.qcassistantmaven.domain.entity.study.MedidataStudy;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "iqvia_tags")
public class IqviaTag extends BaseTag{

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "iqvia_tags_destinations",
            joinColumns = {@JoinColumn(name = "tag_id")},
            inverseJoinColumns = {@JoinColumn(name = "destination_id")}
    )
    private List<Destination> destinations;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "iqvia_tags_studies",
            joinColumns = {@JoinColumn(name = "tag_id")},
            inverseJoinColumns = {@JoinColumn(name = "study_id")}
    )
    private List<IqviaStudy> studies;

    @Override
    public boolean hasDestinationPrecondition() {
        if(this.destinations == null || this
                .destinations.size() == 0){
            return false;
        }

        return true;
    }

    @Override
    public List<Destination> getDestinations() {
        return destinations;
    }

    @Override
    public boolean hasStudyPrecondition() {
        if(this.studies == null || this
                .studies.size() == 0){
            return false;
        }

        return true;
    }

    @Override
    public List<IqviaStudy> getStudies() {
        return studies;
    }

    public void setDestinations(List<Destination> destinations){
        this.destinations = destinations;
    }

    public void setStudies(List<IqviaStudy> studies){
        this.studies = studies;
    }
}

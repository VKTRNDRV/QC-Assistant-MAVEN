package com.example.qcassistantmaven.domain.entity.tag;

import com.example.qcassistantmaven.domain.entity.BaseEntity;
import com.example.qcassistantmaven.domain.entity.destination.Destination;
import com.example.qcassistantmaven.domain.entity.study.BaseStudy;
import com.example.qcassistantmaven.domain.enums.OrderType;
import com.example.qcassistantmaven.domain.enums.Severity;
import com.example.qcassistantmaven.domain.enums.TagType;
import com.example.qcassistantmaven.domain.enums.item.OperatingSystem;
import com.example.qcassistantmaven.domain.enums.item.ShellType;
import jakarta.persistence.*;

import java.util.List;

@MappedSuperclass
public abstract class BaseTag extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column
    private Severity severity;

    @Column(columnDefinition = "TEXT")
    private String text;

    @Enumerated(EnumType.STRING)
    @Column
    private TagType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    private OrderType orderType;

    @Enumerated(EnumType.STRING)
    @Column(name = "shell_type")
    private ShellType shellType;

    @Enumerated(EnumType.STRING)
    @Column(name = "operating_system")
    private OperatingSystem operatingSystem;

    public boolean hasOrderTypePrecondition(){
        if(this.orderType == null ||
                this.orderType.equals(OrderType.OTHER)){
            return false;
        }

        return true;
    }

    public boolean hasShellTypePrecondition(){
        if(this.shellType == null ||
                this.shellType.equals(ShellType.OTHER)){
            return false;
        }

        return true;
    }

    public boolean hasOperatingSystemPrecondition(){
        if(this.operatingSystem == null || this
                .operatingSystem.equals(OperatingSystem.OTHER)){
            return false;
        }

        return true;
    }

    public Severity getSeverity() {
        return severity;
    }

    public BaseTag setSeverity(Severity severity) {
        this.severity = severity;
        return this;
    }

    public String getText() {
        return text;
    }

    public BaseTag setText(String text) {
        this.text = text;
        return this;
    }

    public TagType getType() {
        return type;
    }

    public BaseTag setType(TagType type) {
        this.type = type;
        return this;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public BaseTag setOrderType(OrderType orderType) {
        this.orderType = orderType;
        return this;
    }

    public ShellType getShellType() {
        return shellType;
    }

    public BaseTag setShellType(ShellType shellType) {
        this.shellType = shellType;
        return this;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public BaseTag setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
        return this;
    }

    public abstract boolean hasDestinationPrecondition();
    public abstract List<Destination> getDestinations();

    public abstract boolean hasStudyPrecondition();
    public abstract <T extends BaseStudy> List<T> getStudies();
}

package com.example.qcassistantmaven.service.orderParse;

import com.example.qcassistantmaven.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistantmaven.domain.entity.destination.Destination;
import com.example.qcassistantmaven.domain.entity.destination.Language;
import com.example.qcassistantmaven.domain.entity.sponsor.MedableSponsor;
import com.example.qcassistantmaven.domain.entity.study.MedableStudy;
import com.example.qcassistantmaven.domain.enums.OrderType;
import com.example.qcassistantmaven.domain.enums.item.SimType;
import com.example.qcassistantmaven.domain.item.accessory.Accessory;
import com.example.qcassistantmaven.domain.item.accessory.MedableAccessory;
import com.example.qcassistantmaven.domain.item.device.Device;
import com.example.qcassistantmaven.domain.item.device.android.phone.AndroidPhone;
import com.example.qcassistantmaven.domain.item.device.android.phone.MedableAndroidPhone;
import com.example.qcassistantmaven.domain.item.device.ios.ipad.IPad;
import com.example.qcassistantmaven.domain.item.device.ios.ipad.MedableIPad;
import com.example.qcassistantmaven.domain.item.device.ios.iphone.IPhone;
import com.example.qcassistantmaven.domain.item.device.ios.iphone.MedableIPhone;
import com.example.qcassistantmaven.domain.item.device.medical.MedableMedicalDevice;
import com.example.qcassistantmaven.domain.item.device.medical.MedicalDevice;
import com.example.qcassistantmaven.domain.item.sim.SerializedSIM;
import com.example.qcassistantmaven.domain.order.AccessoryRepository;
import com.example.qcassistantmaven.domain.order.DeviceRepository;
import com.example.qcassistantmaven.domain.order.MedableOrder;
import com.example.qcassistantmaven.domain.order.SimRepository;
import com.example.qcassistantmaven.exception.OrderParsingException;
import com.example.qcassistantmaven.regex.MedableOrderInputRegex;
import com.example.qcassistantmaven.service.DestinationService;
import com.example.qcassistantmaven.service.LanguageService;
import com.example.qcassistantmaven.service.study.MedableStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MedableOrderParseService extends ClinicalOrderParseService{

    private MedableStudyService studyService;

    @Autowired
    public MedableOrderParseService(DestinationService destinationService,
                                    LanguageService languageService,
                                    MedableStudyService studyService) {
        super(destinationService, languageService);
        this.studyService = studyService;
    }

    public MedableOrder parseOrder(RawOrderInputDto inputDto) {
        SegmentedOrderInput segmentedInput = new SegmentedOrderInput(
                inputDto.getParsedRawText());

        validateInput(segmentedInput);

        OrderType orderType = super.getOrderType(segmentedInput);
        Destination destination = super.getDestination(segmentedInput);
        Collection<Language> requestedLanguages = super
                .getRequestedLanguages(segmentedInput);
        MedableStudy study = this.getStudy(segmentedInput);
        MedableSponsor sponsor = study.getSponsor();
        SimRepository sims = this.getSims(segmentedInput);
        AccessoryRepository accessories = this.getAccessories(segmentedInput);
        DeviceRepository devices = this.getDevices(segmentedInput);

        MedableOrder order = new MedableOrder();
        order.setStudy(study)
                .setSponsor(sponsor)
                .setSimRepository(sims)
                .setOrderType(orderType)
                .setDestination(destination)
                .setRequestedLanguages(requestedLanguages)
                .setOrderTermComments(segmentedInput
                        .getOrderTermComments())
                .setDeviceRepository(devices)
                .setAccessoryRepository(accessories);

        return order;
    }

    private DeviceRepository getDevices(SegmentedOrderInput segmentedInput) {
        DeviceRepository deviceRepository = new DeviceRepository();
        String items = segmentedInput.getItemList();
        this.getIPads(items).forEach(deviceRepository::addDevice);
        this.getIPhones(items).forEach(deviceRepository::addDevice);
        this.getAndroidPhones(items).forEach(deviceRepository::addDevice);
        this.getMedicalDevices(items).forEach(deviceRepository::addDevice);

        return deviceRepository;
    }

    private Collection<Device> getMedicalDevices(String items) {
        Collection<Device> medicalDevices = new ArrayList<>();
        Pattern pattern;
        Matcher matcher;
        for(MedableMedicalDevice deviceConst : MedableMedicalDevice.values()){
            pattern = Pattern.compile(deviceConst.getRegexPattern());
            matcher = pattern.matcher(items);
            while (matcher.find()){
                String serial = matcher.group(MedableMedicalDevice.SERIAL_GROUP_NAME);
                medicalDevices.add(new MedicalDevice(
                        deviceConst.getShortName(),
                        deviceConst.getConnectorType(),
                        serial));
            }
        }
        return medicalDevices;
    }

    private Collection<Device> getIPads(String items) {
        Collection<Device> iPads = new ArrayList<>();
        Pattern pattern;
        Matcher matcher;
        for(MedableIPad iPadConst : MedableIPad.values()){
            pattern = Pattern.compile(iPadConst.getRegexPattern());
            matcher = pattern.matcher(items);
            while (matcher.find()){
                String serial = matcher.group(MedableIPad.SERIAL_GROUP_NAME);
                iPads.add(new IPad(
                        iPadConst.getShortName(),
                        iPadConst.getConnectorType(),
                        serial));
            }
        }

        return iPads;
    }

    private Collection<Device> getIPhones(String items) {
        Collection<Device> iPhones = new ArrayList<>();
        Pattern pattern;
        Matcher matcher;
        for(MedableIPhone iPhoneConst : MedableIPhone.values()){
            pattern = Pattern.compile(iPhoneConst.getRegexPattern());
            matcher = pattern.matcher(items);
            while (matcher.find()){
                String serial = matcher.group(MedableIPhone.SERIAL_GROUP_NAME);
                iPhones.add(new IPhone(
                        iPhoneConst.getShortName(),
                        iPhoneConst.getConnectorType(),
                        serial));
            }
        }

        return iPhones;
    }

    private Collection<Device> getAndroidPhones(String items) {
        Collection<Device> phones = new ArrayList<>();
        Pattern pattern;
        Matcher matcher;
        for(MedableAndroidPhone phoneConst : MedableAndroidPhone.values()){
            pattern = Pattern.compile(phoneConst.getRegexPattern());
            matcher = pattern.matcher(items);
            while (matcher.find()){
                String serial = matcher.group(MedableAndroidPhone.SERIAL_GROUP_NAME);
                phones.add(new AndroidPhone(
                        phoneConst.getShortName(),
                        phoneConst.getConnectorType(),
                        serial));
            }
        }

        return phones;
    }


    private void validateInput(SegmentedOrderInput segmentedOrderInput) {
        super.validateOrderType(segmentedOrderInput);
        this.validateClient(segmentedOrderInput);
    }

    private void validateClient(SegmentedOrderInput segmentedOrderInput) {
        Pattern pattern = Pattern.compile(MedableOrderInputRegex
                .CLIENT_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(
                segmentedOrderInput.getItemList());

        if(!matcher.find()){
            throw new OrderParsingException(
                    "Client Medable not detected.");
        }
    }

    private MedableStudy getStudy(SegmentedOrderInput segmentedInput) {
        String studyNameRange = super.getStudyRangeString(segmentedInput);

        for (MedableStudy study : this.studyService.getEntities()){
            if(studyNameRange.contains(study.getName())){
                return study;
            }
        }

        return this.studyService.getUnknownStudy();
    }

    private SimRepository getSims(SegmentedOrderInput segmentedInput) {
        SimRepository simRepository = new SimRepository();
        Pattern pattern = Pattern.compile(MedableOrderInputRegex.SIM_REGEX);
        Matcher matcher = pattern.matcher(segmentedInput.getItemList());
        while(matcher.find()){
            simRepository.addSim(new SerializedSIM(
                    MedableOrderInputRegex.SIM_SHORTNAME,
                    SimType.SIMON_IOT,
                    matcher.group(MedableOrderInputRegex.SIM_SERIAL_GROUP_NAME)));
        }

        return simRepository;
    }

    private AccessoryRepository getAccessories(SegmentedOrderInput segmentedInput) {
        AccessoryRepository accessories = new AccessoryRepository();
        String items = segmentedInput.getItemList();
        Pattern pattern;
        Matcher matcher;
        for(MedableAccessory accessoryConst : MedableAccessory.values()){
            pattern = Pattern.compile(accessoryConst.getRegexPattern());
            matcher = pattern.matcher(items);
            while (matcher.find()){
                accessories.addAccessory(new Accessory(
                        accessoryConst.getShortName()));
            }
        }

        return accessories;
    }
}

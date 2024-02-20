package com.example.qcassistantmaven.service.orderParse;

import com.example.qcassistantmaven.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistantmaven.domain.entity.destination.Destination;
import com.example.qcassistantmaven.domain.entity.destination.Language;
import com.example.qcassistantmaven.domain.entity.sponsor.IqviaSponsor;
import com.example.qcassistantmaven.domain.entity.study.IqviaStudy;
import com.example.qcassistantmaven.domain.enums.OrderType;
import com.example.qcassistantmaven.domain.enums.item.ShellType;
import com.example.qcassistantmaven.domain.enums.item.SimType;
import com.example.qcassistantmaven.domain.item.accessory.Accessory;
import com.example.qcassistantmaven.domain.item.accessory.IqviaAccessory;
import com.example.qcassistantmaven.domain.item.device.Device;
import com.example.qcassistantmaven.domain.item.device.android.phone.AndroidPhone;
import com.example.qcassistantmaven.domain.item.device.android.phone.IqviaAndroidPhone;
import com.example.qcassistantmaven.domain.item.device.android.phone.MedidataAndroidPhone;
import com.example.qcassistantmaven.domain.item.device.android.tablet.AndroidTablet;
import com.example.qcassistantmaven.domain.item.device.android.tablet.IqviaAndroidTablet;
import com.example.qcassistantmaven.domain.item.device.ios.ipad.IPad;
import com.example.qcassistantmaven.domain.item.device.ios.ipad.IqviaIPad;
import com.example.qcassistantmaven.domain.item.device.ios.ipad.MedidataIPad;
import com.example.qcassistantmaven.domain.item.device.ios.iphone.IPhone;
import com.example.qcassistantmaven.domain.item.device.ios.iphone.IqviaIPhone;
import com.example.qcassistantmaven.domain.item.device.ios.iphone.MedidataIPhone;
import com.example.qcassistantmaven.domain.item.device.windows.IqviaWindowsDevice;
import com.example.qcassistantmaven.domain.item.device.windows.WindowsDevice;
import com.example.qcassistantmaven.domain.item.document.Document;
import com.example.qcassistantmaven.domain.item.sim.SerializedSIM;
import com.example.qcassistantmaven.domain.order.*;
import com.example.qcassistantmaven.exception.OrderParsingException;
import com.example.qcassistantmaven.regex.IqviaOrderInputRegex;
import com.example.qcassistantmaven.service.DestinationService;
import com.example.qcassistantmaven.service.LanguageService;
import com.example.qcassistantmaven.service.study.IqviaStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class IqviaOrderParseService extends ClinicalOrderParseService{

    private IqviaStudyService studyService;


    @Autowired
    public IqviaOrderParseService(DestinationService destinationService,
                                  LanguageService languageService,
                                  IqviaStudyService studyService) {
        super(destinationService, languageService);
        this.studyService = studyService;
    }

    public IqviaOrder parseOrder(RawOrderInputDto inputDto) {
        SegmentedOrderInput segmentedInput = new SegmentedOrderInput(
                inputDto.getParsedRawText());

        validateInput(segmentedInput);

        OrderType orderType = super.getOrderType(segmentedInput);
        Collection<Language> requestedLanguages = super
                .getRequestedLanguages(segmentedInput);
        Destination destination = super.getDestination(segmentedInput);
        IqviaStudy study = this.getStudy(segmentedInput);
        IqviaSponsor sponsor = study.getSponsor();
        SimRepository sims = this.getSims(segmentedInput);
        DocumentRepository documents = this.getDocuments(segmentedInput);
        AccessoryRepository accessories = this.getAccessories(segmentedInput);
        DeviceRepository devices = this.getDevices(segmentedInput);

        IqviaOrder order = new IqviaOrder();
        order.setStudy(study)
                .setSponsor(sponsor)
                .setSimRepository(sims)
                .setDocumentRepository(documents)
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
        this.getIPhones(items).forEach(deviceRepository::addDevice);
        this.getIPads(items).forEach(deviceRepository::addDevice);
        this.getAndroidTablets(items).forEach(deviceRepository::addDevice);
        this.getAndroidPhones(items).forEach(deviceRepository::addDevice);
        this.getWindowsDevices(items).forEach(deviceRepository::addDevice);

        return deviceRepository;
    }

    private Collection<Device> getWindowsDevices(String items) {
        Collection<Device> devices = new ArrayList<>();
        Pattern pattern;
        Matcher matcher;
        for(IqviaWindowsDevice deviceConst : IqviaWindowsDevice.values()){
            pattern = Pattern.compile(deviceConst.getRegexPattern());
            matcher = pattern.matcher(items);
            while (matcher.find()){
                String serial = matcher.group(IqviaWindowsDevice.SERIAL_GROUP_NAME);
                devices.add(new WindowsDevice(
                        deviceConst.getShortName(),
                        deviceConst.getConnectorType(),
                        deviceConst.getShellType(),
                        serial));
            }
        }

        return devices;
    }

    private Collection<Device> getAndroidPhones(String items) {
        Collection<Device> phones = new ArrayList<>();
        Pattern pattern;
        Matcher matcher;
        for(IqviaAndroidPhone phoneConst : IqviaAndroidPhone.values()){
            pattern = Pattern.compile(phoneConst.getRegexPattern());
            matcher = pattern.matcher(items);
            while (matcher.find()){
                String serial = matcher.group(IqviaAndroidPhone.SERIAL_GROUP_NAME);
                phones.add(new AndroidPhone(
                        phoneConst.getShortName(),
                        phoneConst.getConnectorType(),
                        serial));
            }
        }

        return phones;
    }

    private Collection<Device> getAndroidTablets(String items) {
        Collection<Device> tablets = new ArrayList<>();
        Pattern pattern;
        Matcher matcher;
        for(IqviaAndroidTablet tabletConst : IqviaAndroidTablet.values()){
            pattern = Pattern.compile(tabletConst.getRegexPattern());
            matcher = pattern.matcher(items);
            while (matcher.find()){
                String serial = matcher.group(IqviaAndroidTablet.SERIAL_GROUP_NAME);
                tablets.add(new AndroidTablet(
                        tabletConst.getShortName(),
                        tabletConst.getConnectorType(),
                        serial));
            }
        }

        return tablets;
    }

    private Collection<Device> getIPads(String items) {
        Collection<Device> iPads = new ArrayList<>();
        Pattern pattern;
        Matcher matcher;
        for(IqviaIPad iPadConst : IqviaIPad.values()){
            pattern = Pattern.compile(iPadConst.getRegexPattern());
            matcher = pattern.matcher(items);
            while (matcher.find()){
                String serial = matcher.group(IqviaIPad.SERIAL_GROUP_NAME);
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
        for(IqviaIPhone iPhoneConst : IqviaIPhone.values()){
            pattern = Pattern.compile(iPhoneConst.getRegexPattern());
            matcher = pattern.matcher(items);
            while (matcher.find()){
                String serial = matcher.group(IqviaIPhone.SERIAL_GROUP_NAME);
                iPhones.add(new IPhone(
                        iPhoneConst.getShortName(),
                        iPhoneConst.getConnectorType(),
                        serial));
            }
        }

        return iPhones;
    }

    private void validateInput(SegmentedOrderInput segmentedOrderInput) {
        super.validateOrderType(segmentedOrderInput);
        this.validateClient(segmentedOrderInput);
    }

    private void validateClient(SegmentedOrderInput segmentedOrderInput) {
        Pattern pattern = Pattern.compile(IqviaOrderInputRegex
                .CLIENT_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(
                segmentedOrderInput.getItemList());

        if(!matcher.find()){
            throw new OrderParsingException(
                    "Client IQVIA not detected.");
        }
    }


    private IqviaStudy getStudy(SegmentedOrderInput segmentedInput) {
        String studyNameRange = super.getStudyRangeString(segmentedInput);

        for (IqviaStudy study : this.studyService.getEntities()){
            if(studyNameRange.contains(study.getName())){
                return study;
            }
        }

        return this.studyService.getUnknownStudy();
    }

    private SimRepository getSims(SegmentedOrderInput segmentedInput) {
        SimRepository simRepository = new SimRepository();
        Pattern pattern = Pattern.compile(IqviaOrderInputRegex.SIM_REGEX);
        Matcher matcher = pattern.matcher(segmentedInput.getItemList());
        while(matcher.find()){
            simRepository.addSim(new SerializedSIM(
                    IqviaOrderInputRegex.SIM_SHORTNAME,
                    SimType.SIMON_IOT,
                    matcher.group(IqviaOrderInputRegex.SIM_SERIAL_GROUP_NAME)));
        }

        return simRepository;
    }

    private DocumentRepository getDocuments(SegmentedOrderInput segmentedInput) {
        DocumentRepository documentRepository = new DocumentRepository();
        Pattern pattern = Pattern.compile(IqviaOrderInputRegex.DOCUMENT_REGEX);
        Matcher matcher = pattern.matcher(segmentedInput.getItemList());
        while (matcher.find()){
            Integer docCount = Integer.parseInt(matcher.group(
                    IqviaOrderInputRegex.DOC_COPIES_COUNT_GROUP));
            documentRepository.addDocument(new Document(
                    IqviaOrderInputRegex.DOC_SHORTNAME,
                    docCount));
        }

        return documentRepository;
    }

    private AccessoryRepository getAccessories(SegmentedOrderInput segmentedInput) {
        AccessoryRepository accessories = new AccessoryRepository();
        String items = segmentedInput.getItemList();
        Pattern pattern;
        Matcher matcher;
        for(IqviaAccessory accessoryConst : IqviaAccessory.values()){
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

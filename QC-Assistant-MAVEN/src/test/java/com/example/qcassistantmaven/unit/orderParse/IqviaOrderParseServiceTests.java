package com.example.qcassistantmaven.unit.orderParse;

import com.example.qcassistantmaven.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistantmaven.domain.enums.OrderType;
import com.example.qcassistantmaven.domain.item.accessory.IqviaAccessory;
import com.example.qcassistantmaven.domain.item.device.android.phone.IqviaAndroidPhone;
import com.example.qcassistantmaven.domain.item.device.android.tablet.IqviaAndroidTablet;
import com.example.qcassistantmaven.domain.item.device.ios.ipad.IqviaIPad;
import com.example.qcassistantmaven.domain.item.device.windows.IqviaWindowsDevice;
import com.example.qcassistantmaven.domain.order.*;
import com.example.qcassistantmaven.service.orderParse.IqviaOrderParseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IqviaOrderParseServiceTests {

    private IqviaOrderParseService orderParseService;

    @Autowired
    public IqviaOrderParseServiceTests(IqviaOrderParseService orderParseService) {
        this.orderParseService = orderParseService;
    }

    @Test
    public void testWrongClientThrowsException(){
        RawOrderInputDto input = new RawOrderInputDto().setRawText(
                IqviaTestOrderInput.WRONG_CLIENT_ORDER);

        Assertions.assertThrows(RuntimeException.class,
                () -> orderParseService.parseOrder(input));
    }

    @Test
    public void testDetectSIMs(){
        IqviaOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(IqviaTestOrderInput.AFW_DEVICES_AND_MULTIPLE_DOC_COPIES_ORDER));

        SimRepository sims = order.getSimRepository();

        Assertions.assertEquals(sims.getSims().size(), 6);
    }

    @Test
    public void testDetectOrderType(){
        IqviaOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(IqviaTestOrderInput.AFW_DEVICES_AND_MULTIPLE_DOC_COPIES_ORDER));

        Assertions.assertEquals(order.getOrderType(), OrderType.PROD);
    }

    @Test
    public void testDetectLanguage(){
        IqviaOrder order = this.orderParseService
                .parseOrder(new RawOrderInputDto().setRawText(
                        IqviaTestOrderInput.WIN_DEVICES_AND_ENGLISH_REQUESTED));

        Assertions.assertTrue(order.isEnglishRequested());
    }

    @Test
    public void testDetectAndroidDevices(){
        IqviaOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(IqviaTestOrderInput.AFW_DEVICES_AND_MULTIPLE_DOC_COPIES_ORDER));

        DeviceRepository devices = order.getDeviceRepository();

        Assertions.assertTrue(devices.containsDevice(
                IqviaAndroidTablet.GALAXY_TAB_A7.getShortName()));

        Assertions.assertTrue(devices.containsDevice(
                IqviaAndroidPhone.A_12.getShortName()));

        Assertions.assertEquals(devices.getDevices().size(), 6);
    }

    @Test
    public void testDetectIOSDevices(){
        IqviaOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(IqviaTestOrderInput.IOS_DEVICES_ORDER));

        DeviceRepository devices = order.getDeviceRepository();

        Assertions.assertTrue(devices.containsDevice(
                IqviaIPad.SEVENTH_GEN.getShortName()));

        Assertions.assertEquals(devices.getDevices().size(), 4);
    }

    @Test
    public void testDetectWindowsDevices(){
        IqviaOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(IqviaTestOrderInput.WIN_DEVICES_AND_ENGLISH_REQUESTED));

        DeviceRepository devices = order.getDeviceRepository();

        Assertions.assertTrue(devices.containsDevice(
                IqviaWindowsDevice.SURFACE_PRO_7.getShortName()));

        Assertions.assertEquals(devices.getDevices().size(), 4);
    }

    @Test
    public void testDetectAccessories(){
        IqviaOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(IqviaTestOrderInput.WIN_DEVICES_AND_ENGLISH_REQUESTED));

        AccessoryRepository accessories = order.getAccessoryRepository();

        Assertions.assertTrue(accessories.containsAccessory(
                IqviaAccessory.PHONE_STAND.getShortName()));
    }

    @Test
    public void testDetectMultipleCopies(){
        IqviaOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(IqviaTestOrderInput.AFW_DEVICES_AND_MULTIPLE_DOC_COPIES_ORDER));

        DocumentRepository documents = order.getDocumentRepository();

        Assertions.assertTrue(documents.areMultipleCopiesRequested());
    }

}

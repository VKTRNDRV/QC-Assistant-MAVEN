package com.example.qcassistantmaven.unit.orderParse;

import com.example.qcassistantmaven.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistantmaven.domain.enums.OrderType;
import com.example.qcassistantmaven.domain.enums.item.SimType;
import com.example.qcassistantmaven.domain.item.accessory.MedidataAccessory;
import com.example.qcassistantmaven.domain.item.device.android.phone.MedidataAndroidPhone;
import com.example.qcassistantmaven.domain.item.device.ios.ipad.MedidataIPad;
import com.example.qcassistantmaven.domain.order.DeviceRepository;
import com.example.qcassistantmaven.domain.order.MedidataOrder;
import com.example.qcassistantmaven.service.orderParse.MedidataOrderParseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MedidataOrderParseServiceTests {

    private MedidataOrderParseService orderParseService;

    @Autowired
    public MedidataOrderParseServiceTests(MedidataOrderParseService orderParseService) {
        this.orderParseService = orderParseService;
    }

    @Test
    public void testWrongClientThrowsException(){
        RawOrderInputDto input = new RawOrderInputDto().setRawText(
                MedidataTestOrderInput.WRONG_CLIENT_ORDER_INPUT);

        Assertions.assertThrows(RuntimeException.class,
                () -> orderParseService.parseOrder(input));
    }

    @Test
    public void testDetectSimType(){
        MedidataOrder order = this.orderParseService
                .parseOrder(new RawOrderInputDto().setRawText(
                        MedidataTestOrderInput.SIMON_IOT_SIM_ORDER));

        Assertions.assertEquals(order.getSimType(), SimType.SIMON_IOT);
    }

    @Test
    public void testDetectOrderType(){
        MedidataOrder order = this.orderParseService
                .parseOrder(new RawOrderInputDto().setRawText(
                        MedidataTestOrderInput.UAT_AND_ENGLISH_REQUESTED_ORDER));

        Assertions.assertEquals(order.getOrderType(), OrderType.UAT);
    }

    @Test
    public void testDetectLanguage(){
        MedidataOrder order = this.orderParseService
                .parseOrder(new RawOrderInputDto().setRawText(
                        MedidataTestOrderInput.UAT_AND_ENGLISH_REQUESTED_ORDER));

        Assertions.assertTrue(order.isEnglishRequested());
    }

    @Test
    public void testDetectHeadphonesAndStyluses(){
        MedidataOrder order = this.orderParseService
                .parseOrder(new RawOrderInputDto().setRawText(
                        MedidataTestOrderInput.HEADPHONES_STYLUSES_DOCUMENTS_ORDER));

        Assertions.assertTrue(order.getAccessoryRepository()
                .containsAccessory(MedidataAccessory.STYLUS.getShortName()));

        Assertions.assertTrue(order.getAccessoryRepository()
                .containsAccessory(MedidataAccessory.HEADPHONES.getShortName()));
    }

    @Test
    public void testDetectDocuments(){
        MedidataOrder order = this.orderParseService
                .parseOrder(new RawOrderInputDto().setRawText(
                        MedidataTestOrderInput.HEADPHONES_STYLUSES_DOCUMENTS_ORDER));

        Assertions.assertEquals(3, order
                .getDocumentRepository().getDocuments().size());
    }

    @Test
    public void testDetectIOSDevices(){
        MedidataOrder order = this.orderParseService
                .parseOrder(new RawOrderInputDto().setRawText(
                        MedidataTestOrderInput.SIMON_IOT_SIM_ORDER));

        DeviceRepository devices = order.getDeviceRepository();

        Assertions.assertTrue(devices.containsDevice(
                MedidataIPad.SIXTH_GEN.getShortName()));

        Assertions.assertEquals(2,
                devices.getDevices().size());
    }

    @Test
    public void testDetectAFWDevices(){
        MedidataOrder order = this.orderParseService
                .parseOrder(new RawOrderInputDto().setRawText(
                        MedidataTestOrderInput.ANDROID_DEVICES_ORDER));

        DeviceRepository devices = order.getDeviceRepository();

        Assertions.assertTrue(devices.containsDevice(
                MedidataAndroidPhone.GALAXY_J3.getShortName()));

        Assertions.assertEquals(3,
                devices.getDevices().size());
    }
}

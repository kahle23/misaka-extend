package misaka.device;

import artoria.beans.BeanUtils;
import artoria.data.RecombineUtils;
import artoria.file.Csv;
import artoria.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FileBasedDeviceProvider implements DeviceProvider {
    private Map<String, Device> deviceMap;

    public FileBasedDeviceProvider(Csv csv) {
        List<Device> deviceList = BeanUtils.mapToBeanInList(csv.toMapList(), Device.class);
        Map<String, Device> modelMap = RecombineUtils.listToMapBean(deviceList, "model");
        deviceMap = Collections.unmodifiableMap(modelMap);
    }

    @Override
    public Device info(String model, String type) {
        Assert.notBlank(model, "Parameter \"model\" must not blank. ");
        return deviceMap.get(model);
    }

}

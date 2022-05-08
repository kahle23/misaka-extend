package misaka.device;

import artoria.beans.BeanUtils;
import artoria.data.RecombineUtils;
import artoria.file.Csv;
import artoria.query.AbstractQueryHandler;
import artoria.util.Assert;
import artoria.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FileBasedDeviceQueryHandler extends AbstractQueryHandler {
    private Map<String, Device> deviceMap;

    public FileBasedDeviceQueryHandler(Csv csv) {
        List<Device> deviceList = BeanUtils.mapToBeanInList(csv.toMapList(), Device.class);
        Map<String, Device> modelMap = RecombineUtils.listToMapBean(deviceList, "model");
        deviceMap = Collections.unmodifiableMap(modelMap);
    }

    @Override
    public <T> T info(Map<?, ?> properties, Object input, Class<T> clazz) {
        isSupport(new Class[]{ Device.class }, clazz);
        DeviceQuery deviceQuery = (DeviceQuery) input;
        String model = deviceQuery.getModel();
        Assert.notBlank(model, "Parameter \"model\" must not blank. ");
        return ObjectUtils.cast(deviceMap.get(model));
    }

}

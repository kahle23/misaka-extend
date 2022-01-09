package misaka.location;

import artoria.beans.BeanUtils;
import artoria.exception.ExceptionUtils;
import artoria.file.Csv;
import artoria.file.FileUtils;
import artoria.util.Assert;
import artoria.util.CollectionUtils;
import artoria.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static artoria.common.Constants.UTF_8;

public class FileBasedGeolocationProvider implements LocationProvider {
    private static final String DEFAULT_FILE_NAME = "geolocation.data";
    private List<Location> dataList = new ArrayList<Location>();
    private Map<String, List<Location>> dataMap = new HashMap<String, List<Location>>();
    private List<Location> countryList = new ArrayList<Location>();
    private Map<String, List<Location>> regionMap = new HashMap<String, List<Location>>();
    private Map<String, List<Location>> cityMap = new HashMap<String, List<Location>>();
    private Map<String, List<Location>> districtMap = new HashMap<String, List<Location>>();
    private Map<String, List<Location>> streetMap = new HashMap<String, List<Location>>();

    public FileBasedGeolocationProvider() {

        this(null, null);
    }

    public FileBasedGeolocationProvider(String filePath, String charset) {
        charset = StringUtils.isNotBlank(charset) ? charset : UTF_8;
        Csv csv = new Csv();
        csv.setCharset(charset);
        try {
            if (StringUtils.isNotBlank(filePath)) {
                File file = new File(filePath);
                if (!file.exists()) {
                    FileUtils.createNewFile(file);
                }
                csv.readFromFile(new File(filePath));
            }
            else {
                csv.readFromClasspath(DEFAULT_FILE_NAME);
            }
        }
        catch (IOException e) {
            throw ExceptionUtils.wrap(e);
        }
        initialize(csv);
    }

    public FileBasedGeolocationProvider(Csv csv) {

        initialize(csv);
    }

    private void initialize(Csv csv) {
        if (csv == null) { return; }
        dataList.addAll(BeanUtils.mapToBeanInList(csv.toMapList(), Location.class));
        for (Location area : dataList) {
            String country = area.getCountry();
            String countryCode = area.getCountryCode();
            String region = area.getRegion();
            String regionCode = area.getRegionCode();
            String city = area.getCity();
            String cityCode = area.getCityCode();
            String district = area.getDistrict();
            String districtCode = area.getDistrictCode();
            String street = area.getStreet();
            String streetCode = area.getStreetCode();
            if (StringUtils.isBlank(region)) {
                addArea(area, countryCode, country, dataMap);
                countryList.add(area);
            }
            else if (StringUtils.isBlank(city)) {
                addArea(area, regionCode, region, dataMap);
                addArea(area, countryCode, country, regionMap);
            }
            else if (StringUtils.isBlank(district)) {
                addArea(area, cityCode, city, dataMap);
                addArea(area, regionCode, region, cityMap);
            }
            else if (StringUtils.isBlank(street)) {
                addArea(area, districtCode, district, dataMap);
                addArea(area, cityCode, city, districtMap);
            }
            else {
                addArea(area, streetCode, street, dataMap);
                addArea(area, districtCode, district, streetMap);
            }
        }
    }

    private void addArea(Location area, String code, String name, Map<String, List<Location>> map) {
        List<Location> list = map.get(code);
        if (list == null) {
            list = new ArrayList<Location>();
            if (code != null) { map.put(code, list); }
            if (name != null) { map.put(name, list); }
        }
        list.add(area);
    }

    @Override
    public Location info(String nameOrCode) {
        List<Location> locationList = search(nameOrCode);
        if (CollectionUtils.isEmpty(locationList)) {
            return null;
        }
        return locationList.get(locationList.size() - 1);
    }

    @Override
    public List<Location> search(String nameOrCode) {

        return dataMap.get(nameOrCode);
    }

    @Override
    public List<Location> subLocations(String nameOrCode, String paramType) {
        if (StringUtils.isBlank(paramType) &&
                StringUtils.isBlank(nameOrCode)) {
            return Collections.unmodifiableList(countryList);
        }
        Assert.notBlank(nameOrCode, "Parameter \"nameOrCode\" must not blank. ");
        Assert.notBlank(paramType, "Parameter \"paramType\" must not blank. ");
        if ("country".equalsIgnoreCase(paramType)) {
            return regionMap.get(nameOrCode);
        }
        else if ("region".equalsIgnoreCase(paramType)) {
            return cityMap.get(nameOrCode);
        }
        else if ("city".equalsIgnoreCase(paramType)) {
            return districtMap.get(nameOrCode);
        }
        else if ("district".equalsIgnoreCase(paramType)) {
            return streetMap.get(nameOrCode);
        }
        else {
            throw new UnsupportedOperationException();
        }
    }

}

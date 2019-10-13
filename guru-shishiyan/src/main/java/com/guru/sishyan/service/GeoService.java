package com.guru.sishyan.service;

import com.byteowls.jopencage.JOpenCageGeocoder;
import com.byteowls.jopencage.model.JOpenCageForwardRequest;
import com.byteowls.jopencage.model.JOpenCageLatLng;
import com.byteowls.jopencage.model.JOpenCageResponse;

public class GeoService {
    public static Double[] getLatLong(String address) {
        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder("3baf54955c69497c82ad11ec9f6caa0b");
        JOpenCageForwardRequest request = new JOpenCageForwardRequest(address);

        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        JOpenCageLatLng firstResultLatLng = response.getFirstPosition(); // get the coordinate pair of the first result
        System.out.println(firstResultLatLng.getLat() + " " + firstResultLatLng.getLng());
        return new Double[] {firstResultLatLng.getLat(), firstResultLatLng.getLng()};
    }
}


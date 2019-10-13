package com.zeemonsters.disman.db;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class Pincode {
	public static final String TABLENAME = "pincode";
	public static final String ID = "id";
	public static final String PINCODE = "pincode";
	public static final String LATITUDE = "lat";
	public static final String LONGITUDE = "long";

	public static boolean insert(String[] values) {
		try {
			return DBUtils.insert(TABLENAME, PINCODE + "," + LATITUDE + "," + LONGITUDE, values);
		} catch (ClassNotFoundException | SQLException e) {
			return false;
		}
	}

	public static JSONArray getLatLong(String wherecol, String whereVal) {
		try {
			JSONObject result = DBUtils.select(TABLENAME, ID + "," + LATITUDE + "," + LONGITUDE, wherecol, whereVal,
					null);
			if (result.has("result")) {
				return result.getJSONArray("result");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}

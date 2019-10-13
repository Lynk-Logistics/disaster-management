package com.zeemonsters.disman.db;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class Profile {
	public static final String TABLENAME = "profile";
	public static final String ID = "id";
	public static final String USERNAME = "username";
	public static final String PINCODE = "pincode";
	public static final String REGION = "region";
	public static final String AVAILABLE = "available";

	public static boolean insert(String[] values) {
		try {

			return DBUtils.insert(TABLENAME, PINCODE + "," + USERNAME + "," + REGION + "," + AVAILABLE, values);
		} catch (ClassNotFoundException | SQLException e) {
			return false;
		}
	}

	public static JSONArray getProfile(String wherecol, String whereVal) {
		try {

			JSONObject result = DBUtils.select(TABLENAME,
					ID + "," + PINCODE + "," + USERNAME + "," + REGION + "," + AVAILABLE, wherecol, whereVal, null);
			if (result.has("result")) {
				return result.getJSONArray("result");
			}
		} catch (ClassNotFoundException | SQLException e) {
		}
		return new JSONArray();
	}

	public static boolean update(String[] values, String whereCol, String whereVal) {
		try {
			DBUtils.delete(TABLENAME, whereCol, whereVal);
			return DBUtils.insert(TABLENAME, PINCODE + "," + USERNAME + "," + REGION + "," + AVAILABLE, values);
		} catch (ClassNotFoundException | SQLException e) {
			
			return false;
		}

	}

}

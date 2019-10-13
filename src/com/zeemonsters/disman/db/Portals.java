package com.zeemonsters.disman.db;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class Portals {
	public static final String TABLENAME = "portals";
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String URL = "url";
	public static final String REGION = "region";
	public static final String DESCRIPTION = "description";
	public static final String DATETIME = "eventdate";

	public static JSONArray getPortals() {
		try {
			JSONObject result = DBUtils.select(TABLENAME,
					ID + "," + NAME + "," + URL + "," + REGION + "," + DESCRIPTION + "," + DATETIME, null, null, null);
			if (result.has("result")) {
				return result.getJSONArray("result");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean insert(String[] values) {
		try {
			return DBUtils.insert(TABLENAME, NAME + "," + URL + "," + REGION + "," + DESCRIPTION + "," + DATETIME,
					values);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static JSONArray getPortals(String wherecol, String whereVal) {
		try {
			JSONObject result = DBUtils.select(TABLENAME,
					ID + "," + NAME + "," + URL + "," + REGION + "," + DESCRIPTION + "," + DATETIME, wherecol, whereVal,
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

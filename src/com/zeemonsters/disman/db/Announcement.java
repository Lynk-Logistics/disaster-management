package com.zeemonsters.disman.db;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class Announcement {
	public static final String TABLENAME = "announcement";
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String NAME = "username";
	public static final String TAG = "tag";
	public static final String DESCRIPTION = "description";
	public static final String DATETIME = "eventdate";
	public static final String PORTALID = "portalid";

	public static JSONArray getAnnouncements() {
		try {
			JSONObject result = DBUtils.select(TABLENAME,
					ID + "," + NAME + "," + TITLE+ "," + TAG + "," + DESCRIPTION + "," + DATETIME+ "," + PORTALID, null, null, null);
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
			return DBUtils.insert(TABLENAME, NAME+ "," + TITLE + "," + TAG + "," + DESCRIPTION + "," + DATETIME+ "," + PORTALID, values);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static JSONArray getAnnouncements(String wherecol, String whereVal) {
		try {
			JSONObject result = DBUtils.select(TABLENAME,
					ID + "," + NAME + "," + TITLE+ "," + TAG + "," + DESCRIPTION + "," + DATETIME+ "," + PORTALID, wherecol, whereVal, null);

			if (result.has("result")) {
				return result.getJSONArray("result");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}

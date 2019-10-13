package com.zeemonsters.disman.db;

import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class Issues {
	public static final String TABLENAME = "issues";
	public static final String ID = "id";
	public static final String USER = "userid";
	public static final String DATETIME = "datetime";
	public static final String MESSAGE = "message";
	public static final String LATITUDE = "lat";
	public static final String PINCODE = "pincode";
	public static final String LONGITUDE = "long";
	public static final String MESSAGEID = "msgid";
	public static final String REPLYTO = "replyto";
	public static final String TAGS = "tags";
	public static final String ASSIGNED = "assigned";

	public static JSONArray getIssues() {
		try {
			JSONObject result = DBUtils.select(TABLENAME,
					USER + "," + DATETIME + "," + MESSAGE + "," + LATITUDE + "," + LONGITUDE + "," + ID + ","
							+ MESSAGEID + "," + REPLYTO + "," + TAGS + "," + PINCODE + "," + ASSIGNED,
					null, null, null);
			if (result.has("result")) {
				return result.getJSONArray("result");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONArray getIssues(String whereCols[], String whereVals[]) {
		try {
			JSONObject result = DBUtils.selectM(TABLENAME,
					USER + "," + DATETIME + "," + MESSAGE + "," + LATITUDE + "," + LONGITUDE + "," + ID + ","
							+ MESSAGEID + "," + REPLYTO + "," + TAGS + "," + PINCODE + "," + ASSIGNED,
					whereCols, whereVals, null);
			if (result.has("result")) {
				return result.getJSONArray("result");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONArray getIssues(String whereCols, String whereVals) {
		try {
			JSONObject result = DBUtils.select(TABLENAME,
					USER + "," + DATETIME + "," + MESSAGE + "," + LATITUDE + "," + LONGITUDE + "," + ID + ","
							+ MESSAGEID + "," + REPLYTO + "," + TAGS + "," + PINCODE + "," + ASSIGNED,
					whereCols, whereVals, null);
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
			return DBUtils.insert(TABLENAME, USER + "," + DATETIME + "," + MESSAGE + "," + REPLYTO + "," + TAGS + ","
					+ LATITUDE + "," + LONGITUDE + "," + PINCODE + "," + ASSIGNED, values);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean update(String cols, String values, String whereCol, String whereVal) {
		try {
			return DBUtils.update(Issues.TABLENAME, cols, values, whereCol, whereVal);

		} catch (ClassNotFoundException | SQLException e) {
			return false;
		}
	}

	public static boolean delete(String id) {
		try {
			return DBUtils.delete(Issues.TABLENAME, Issues.ID, "'" + id + "'");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return false;
		}

	}
}

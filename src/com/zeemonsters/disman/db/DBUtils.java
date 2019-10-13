package com.zeemonsters.disman.db;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import javax.sound.midi.Track;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zeemonsters.disman.auth.Constants;
import com.zeemonsters.disman.auth.SecurityConfig;

public class DBUtils {

	static {
		try {
			initialize();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5555/zeemonsters", "postgres",
				"root");
		return conn;
	}

	public static boolean initialize() throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		Set<String> set = getDBTables(conn);
		if (!set.contains(Users.TABLENAME)) {

			String query = "CREATE TABLE " + Users.TABLENAME + "( " + Users.ID + " serial PRIMARY KEY, "
					+ Users.USERNAME + " VARCHAR(255) UNIQUE NOT NULL, " + Users.PASSWORD + " VARCHAR(255) NOT NULL, "
					+ Users.ROLE + " VARCHAR(255) NOT NULL, " + Users.EMAIL + " VARCHAR(255) NOT NULL, " + Users.MOBILE
					+ " VARCHAR(255) NOT NULL);";
			stmt.execute(query);
			query = "INSERT INTO " + Users.TABLENAME + "(" + Users.USERNAME + "," + Users.PASSWORD + "," + Users.ROLE
					+ "," + Users.EMAIL + "," + Users.MOBILE + ") VALUES "
					+ "('balachandar', 'ee11cbb19052e40b07aac0ca060c23ee', '" + SecurityConfig.ROLE_VOLUNTEER + "','"
					+ Constants.FROM_EMAIL + "','" + Constants.ADMIN_MOBILE + "'), "
					+ "('sujatha', 'ee11cbb19052e40b07aac0ca060c23ee', '" + SecurityConfig.ROLE_VOLUNTEER + "','"
					+ Constants.FROM_EMAIL + "','" + Constants.ADMIN_MOBILE + "') ";
			stmt.execute(query);

		}
		if (!set.contains(Portals.TABLENAME)) {
			String query = "CREATE TABLE " + Portals.TABLENAME + "( " + Portals.ID + " serial PRIMARY KEY, "
					+ Portals.NAME + " VARCHAR(255) UNIQUE NOT NULL, " + Portals.REGION + " VARCHAR(255) NOT NULL, "
					+ Portals.DESCRIPTION + " VARCHAR(255) NOT NULL, " + Portals.DATETIME + " DATE, " + Portals.URL
					+ " VARCHAR(255) UNIQUE NOT NULL);";

			stmt.execute(query);
		}

		if (!set.contains(Issues.TABLENAME)) {
			String query = "CREATE TABLE " + Issues.TABLENAME + "( " + Issues.ID + " serial PRIMARY KEY, " + Issues.USER
					+ " VARCHAR(255) NOT NULL, " + Issues.ASSIGNED + " VARCHAR(255) NULL, " + Issues.PINCODE
					+ " VARCHAR(255) NOT NULL, " + Issues.MESSAGEID + " serial, " + Issues.LATITUDE
					+ " VARCHAR(255) NOT NULL, " + Issues.LONGITUDE + " VARCHAR(255) NOT NULL, " + Issues.REPLYTO
					+ " VARCHAR(255) NOT NULL, " + Issues.TAGS + " VARCHAR(255) NOT NULL, " + Issues.MESSAGE
					+ " VARCHAR(255) NOT NULL, " + Issues.DATETIME + " DATE);";

			stmt.execute(query);
		}
		if (!set.contains(Announcement.TABLENAME)) {
			String query = "CREATE TABLE " + Announcement.TABLENAME + "( " + Announcement.ID + " serial PRIMARY KEY, "
					+ Announcement.PORTALID + " VARCHAR(255) NOT NULL," + Announcement.TITLE + " VARCHAR(255) NOT NULL,"
					+ Announcement.NAME + " VARCHAR(255) NOT NULL, " + Announcement.DESCRIPTION
					+ " VARCHAR(255) NOT NULL, " + Announcement.TAG + " VARCHAR(255) NOT NULL, " + Announcement.DATETIME
					+ " DATE);";

			stmt.execute(query);
		}

		if (!set.contains(Pincode.TABLENAME)) {
			String query = "CREATE TABLE " + Pincode.TABLENAME + "( " + Pincode.ID + " serial PRIMARY KEY, "
					+ Pincode.PINCODE + " VARCHAR(255) NOT NULL, " + Pincode.LATITUDE + " VARCHAR(255) NOT NULL, "
					+ Pincode.LONGITUDE + " VARCHAR(255) NOT NULL);";

			stmt.execute(query);
		}

		if (!set.contains(Profile.TABLENAME)) {
			String query = "CREATE TABLE " + Profile.TABLENAME + "( " + Profile.ID + " serial PRIMARY KEY, "
					+ Profile.REGION + " VARCHAR(255) NOT NULL, " + Profile.USERNAME + " VARCHAR(255) NOT NULL, "
					+ Profile.AVAILABLE + " VARCHAR(255) NOT NULL, " + Profile.PINCODE + " VARCHAR(255) NOT NULL);";

			stmt.execute(query);
		}

		return true;
	}

	public static boolean drop() throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		Set<String> tables = getDBTables(conn);
		for (String string : tables) {
			String query = "DROP TABLE " + string;
			stmt.execute(query);
		}
		return true;
	}

	public static boolean insert(String tablename, String columns, String[] values)
			throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		String query = "INSERT INTO " + tablename + "(" + columns + ") VALUES ";
		for (int i = 0; i < values.length; i++) {
			query += "(" + values[i] + ")";
			if (i < values.length - 1) {
				query += ",";
			}
		}
		try {
			stmt.execute(query);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static int count(String tablename, String col) throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		String query = "SELECT count(" + col + ") as count FROM " + tablename;
		ResultSet rs = stmt.executeQuery(query);
		int count = 0;
		while (rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}

	public static JSONObject select(String tablename, String columns, String whereCol, String whereVal, String groupBY)
			throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		String query = "SELECT " + columns + " FROM " + tablename;
		if (whereCol != null) {
			query += " WHERE " + whereCol + " = " + whereVal;
		}
		ResultSet rs = stmt.executeQuery(query);
		JSONObject jsonresult = new JSONObject();
		JSONArray result = new JSONArray();
		String col[] = columns.split("[,]");
		int count = col.length;
		while (rs.next()) {

			JSONObject json = new JSONObject();
			for (int i = 0; i < col.length; i++) {
				json.put(col[i], rs.getString(col[i]));
			}
			result.put(json);
		}
		jsonresult.put("tablename", tablename);
		jsonresult.put("result", result);
		return jsonresult;
	}

	public static JSONObject selectM(String tablename, String columns, String whereCol[], String whereVal[],
			String groupBY) throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		String query = "SELECT " + columns + " FROM " + tablename;
		if (whereCol != null && whereVal != null && whereCol.length == whereVal.length) {
			query += " WHERE ";
			for (int i = 0; i < whereVal.length; i++) {
				query += whereCol[i] + " LIKE " + whereVal[i];
				if (i < whereVal.length - 1) {
					query += " AND ";
				}
			}

		}
		ResultSet rs = stmt.executeQuery(query);
		JSONObject jsonresult = new JSONObject();
		JSONArray result = new JSONArray();
		String col[] = columns.split("[,]");
		int count = col.length;
		while (rs.next()) {

			JSONObject json = new JSONObject();
			for (int i = 0; i < col.length; i++) {
				json.put(col[i], rs.getString(col[i]));
			}
			result.put(json);
		}
		jsonresult.put("tablename", tablename);
		jsonresult.put("result", result);
		return jsonresult;
	}

	public static boolean update(String tablename, String columns, String values, String whereCol, String whereVal)
			throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		String query = "UPDATE  " + tablename + " SET " + columns + " = " + values;
		if (whereCol != null) {
			query += " WHERE " + whereCol + " = " + whereVal;
		}

		if (stmt.executeUpdate(query) <= 0) {
			return false;
		}
		return true;

	}

	public static boolean delete(String tablename, String whereCol, String whereVal)
			throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		String query = "DELETE FROM  " + tablename;
		if (whereCol != null) {
			query += " WHERE " + whereCol + " = " + whereVal;
		}
		if (stmt.executeUpdate(query) <= 0) {
			return false;
		}
		return true;
	}

	public static String getMd5(String input) {
		try {

			// Static getInstance method is called with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// digest() method is called to calculate message digest
			// of an input digest() return array of byte
			byte[] messageDigest = md.digest(input.getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private static Set<String> getDBTables(Connection targetDBConn) throws SQLException {
		Set<String> set = new HashSet<String>();
		DatabaseMetaData dbmeta = targetDBConn.getMetaData();
		readDBTable(set, dbmeta, "TABLE", null);
		readDBTable(set, dbmeta, "VIEW", null);
		return set;
	}

	private static void readDBTable(Set<String> set, DatabaseMetaData dbmeta, String searchCriteria, String schema)
			throws SQLException {
		ResultSet rs = dbmeta.getTables(null, schema, null, new String[] { searchCriteria });
		while (rs.next()) {
			set.add(rs.getString("TABLE_NAME").toLowerCase());
		}
	}
}

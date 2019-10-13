package com.zeemonsters.disman.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zeemonsters.disman.auth.AppUtils;
import com.zeemonsters.disman.auth.UserAccount;
import com.zeemonsters.disman.db.Issues;
import com.zeemonsters.disman.db.Pincode;
import com.zeemonsters.disman.db.Utils;

@Path("/requests")
public class Requests extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Context
	HttpServletRequest request;
	HttpServletResponse response;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRequest() {
		UserAccount userAccount = AppUtils.getLoginedUser(request.getSession());
		if (userAccount != null) {
			JSONArray arr = com.zeemonsters.disman.db.Profile.getProfile(com.zeemonsters.disman.db.Profile.USERNAME,
					"'" + userAccount.getUserName() + "'");
			if (arr.length() > 0) {
				String pin = arr.getJSONObject(0).getString("pincode");
				JSONArray array = Pincode.getLatLong(Pincode.PINCODE, "'" + pin + "'");
				JSONObject obj = array.getJSONObject(0);

				String lat = obj.getString("lat");
				String longi = obj.getString("long");
				BigDecimal bd = BigDecimal.valueOf(Double.parseDouble(lat));
				bd = bd.setScale(2, RoundingMode.DOWN);
				lat = String.format("%.2f", bd);
				bd = BigDecimal.valueOf(Double.parseDouble(longi));
				bd = bd.setScale(3, RoundingMode.DOWN);
				longi = String.format("%.3f", bd);
				JSONArray respArray = Issues.getIssues(new String[] { Issues.LATITUDE, Issues.LONGITUDE },
						new String[] { "'" + lat + "%'", "'" + longi + "%'" });

				JSONObject respObj = new JSONObject();
				respObj.put("latlong", respArray);

				JSONArray pinArray = Issues.getIssues(Issues.PINCODE, "'" + pin + "'");
				respObj.put("pin", pinArray);
				
				
				JSONArray allArray = Issues.getIssues();
				respObj.put("all", allArray);
				
				
				return Response.status(200).entity(respObj.toString()).build();
			} else {
				JSONObject respObj = new JSONObject();
				JSONArray allArray = Issues.getIssues();
				respObj.put("all", allArray);
				return Response.status(200).entity(respObj.toString()).build();
			}

		} else {
			JSONObject respObj = new JSONObject();
			JSONArray allArray = Issues.getIssues();
			respObj.put("all", allArray);
			return Response.status(200).entity(respObj.toString()).build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	public Response updateProfile(@FormParam("user") String userid, @FormParam("message") String message,
			@FormParam("pincode") String pincode, @FormParam("lat") String lat, @FormParam("longi") String longi,
			@FormParam("replyto") String replyto, @FormParam("tags") String tags,
			@FormParam("assigned") String assigned) throws ServletException, IOException {
		JSONObject responseObj = new JSONObject();

		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String strDate = formatter.format(date);

		if (Utils.checkNotEmptyOrNull(message) && Utils.checkNotEmptyOrNull(userid)
				&& Utils.checkNotEmptyOrNull(pincode)) {
			replyto = (replyto == null) ? "" : replyto;
			tags = (tags == null) ? "" : tags;
			lat = (lat == null) ? "" : lat;
			longi = (longi == null) ? "" : longi;
			pincode = (pincode == null) ? "" : pincode;
			assigned = (assigned == null) ? "" : assigned;
			
//			USER + "," + DATETIME + "," + MESSAGE +  + "," + REPLYTO
//			+ "," + TAGS + "," + LATITUDE + "," + LONGITUDE + "," + PINCODE
			if (Issues.insert(new String[] { "'" + userid + "','" + strDate + "','" + message + "','" + replyto + "','"
					+ tags + "','" + lat + "','" + longi + "','" + pincode + "','" + assigned + "'" })) {
				responseObj.put("message", "Successfully added the issue");
				responseObj.put("status", "SUCCESS");
				return Response.status(201).entity(responseObj.toString()).build();
			} else {
				String errorMessage = "Please check the fields";
				responseObj.put("message", errorMessage);
				responseObj.put("status", "FAILED");
				return Response.status(400).entity(responseObj.toString()).build();
			}

		} else {
			String errorMessage = "Please check the fields";
			responseObj.put("message", errorMessage);
			responseObj.put("status", "FAILED");
			return Response.status(400).entity(responseObj.toString()).build();
		}

	}

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRequestId(@PathParam("id") String id) {
		UserAccount userAccount = AppUtils.getLoginedUser(request.getSession());
		if (userAccount != null) {
			JSONArray array = Issues.getIssues(Issues.ID, "'" + id + "'");
			if (array.length() > 0) {
				JSONObject object = array.getJSONObject(0);
				return Response.status(200).entity(object.toString()).build();
			} else {
				String errorMessage = "Invalid Request";
				JSONObject responseObj = new JSONObject();
				responseObj.put("message", errorMessage);
				responseObj.put("status", "FAILED");
				return Response.status(400).entity(responseObj.toString()).build();
			}

		} else {
			String errorMessage = "You cannot access profiles";
			JSONObject responseObj = new JSONObject();
			responseObj.put("message", errorMessage);
			responseObj.put("status", "FAILED");
			return Response.status(400).entity(responseObj.toString()).build();
		}
	}

	@Path("{id}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	public Response updateRequest(@PathParam("id") String id,@FormParam("user") String userid, @FormParam("message") String message,
			@FormParam("pincode") String pincode, @FormParam("lat") String lat, @FormParam("longi") String longi,
			@FormParam("replyto") String replyto, @FormParam("tags") String tags,
			@FormParam("assigned") String assigned) throws ServletException, IOException {
		JSONObject responseObj = new JSONObject();

		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String strDate = formatter.format(date);
		
		if (Utils.checkNotEmptyOrNull(message) && Utils.checkNotEmptyOrNull(userid)) {
			
			replyto = (replyto == null) ? "" : replyto;
			tags = (tags == null) ? "" : tags;
			lat = (lat == null) ? "" : lat;
			longi = (longi == null) ? "" : longi;
			pincode = (pincode == null) ? "" : pincode;
			assigned = (assigned == null) ? "" : assigned;

			boolean test = Issues.delete(id);
			if (Issues.insert(new String[] { "'" + userid + "','" + strDate + "','" + message + "','" + replyto + "','"
					+ tags + "','" + lat + "','" + longi + "','" + pincode + "','" + assigned + "'" })) {
				responseObj.put("message", "Successfully updated the issue");
				responseObj.put("status", "SUCCESS");
				return Response.status(200).entity(responseObj.toString()).build();
			} else {
				String errorMessage = "Please check the fields";
				responseObj.put("message", errorMessage);
				responseObj.put("status", "FAILED");
				return Response.status(400).entity(responseObj.toString()).build();
			}

		} else {
			String errorMessage = "Please check the fields";
			responseObj.put("message", errorMessage);
			responseObj.put("status", "FAILED");
			return Response.status(400).entity(responseObj.toString()).build();
		}

	}
}

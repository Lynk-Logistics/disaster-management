package com.zeemonsters.disman.servlet;

import java.io.IOException;
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
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.zeemonsters.disman.db.Issues;

@Path("/sms")
public class SMS extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Context
	HttpServletRequest request;
	HttpServletResponse response;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPortals() {
		return Response.status(200).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	public Response updateProfile(@FormParam("SmsMessageSid") String SmsMessageSid,
			@FormParam("NumMedia") String NumMedia, @FormParam("SmsSid") String SmsSid,
			@FormParam("SmsStatus") String SmsStatus, @FormParam("Body") String Body, @FormParam("To") String To,
			@FormParam("NumSegments") String NumSegments, @FormParam("MessageSid") String MessageSid,
			@FormParam("AccountSid") String AccountSid, @FormParam("From") String From,
			@FormParam("ApiVersion") String ApiVersion) throws ServletException, IOException {
		JSONObject responseObj = new JSONObject();
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String strDate = formatter.format(date);
		String replyto = "";
		String tags = "";
		String lat = "";
		String longi = "";
		String pincode = "";
		String assigned = "";

		if (Issues.insert(new String[] { "'" + From + "','" + strDate + "','" + Body + "','" + replyto + "','"
				+ tags + "','" + lat + "','" + longi + "','" + pincode + "','" + assigned + "'" })) {
			responseObj.put("message", "Successfully updated the profile");
			responseObj.put("status", "SUCCESS");
			return Response.status(200).entity(responseObj.toString()).build();
		}
		System.out.println(From);
		System.out.println(Body);
		return Response.ok().entity(responseObj.toString()).build();
	}

}

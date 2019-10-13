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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zeemonsters.disman.auth.AppUtils;
import com.zeemonsters.disman.auth.SecurityConfig;
import com.zeemonsters.disman.auth.UserAccount;
import com.zeemonsters.disman.db.Portals;
import com.zeemonsters.disman.db.Utils;

@Path("/profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Context
	HttpServletRequest request;
	HttpServletResponse response;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfile() {
		UserAccount userAccount = AppUtils.getLoginedUser(request.getSession());
		if (userAccount != null) {
			JSONArray arr = com.zeemonsters.disman.db.Profile.getProfile(com.zeemonsters.disman.db.Profile.USERNAME,
					"'"+userAccount.getUserName()+"'");
			if (arr.length() > 0) {
				return Response.status(200).entity(arr.get(0).toString()).build();
			} else {
				String errorMessage = "Profile not updated";
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

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	public Response updateProfile(@FormParam("pincode") String pincode, @FormParam("region") String region,
			@FormParam("available") String available) throws ServletException, IOException {
		JSONObject responseObj = new JSONObject();
		UserAccount userAccount = AppUtils.getLoginedUser(request.getSession());
		if (userAccount != null) {
			Date date = Calendar.getInstance().getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String strDate = formatter.format(date);
			if (userAccount.getRoles().get(0).equals(SecurityConfig.ROLE_VOLUNTEER)) {
				if (Utils.checkNotEmptyOrNull(region) && Utils.checkNotEmptyOrNull(available)
						&& Utils.checkNotEmptyOrNull(pincode)) {
					JSONArray arry = com.zeemonsters.disman.db.Profile.getProfile(
							com.zeemonsters.disman.db.Profile.USERNAME, "'" + userAccount.getUserName() + "'");
					if (arry.length() == 0) {
						
						com.zeemonsters.disman.db.Profile.insert(

								new String[] { "'" + pincode + "','" + userAccount.getUserName() + "','" + region
										+ "','" + available + "'" });
					} else {
						
						com.zeemonsters.disman.db.Profile.update(
								new String[] { "'" + pincode + "','" + userAccount.getUserName() + "','" + region
										+ "','" + available + "'" },
								com.zeemonsters.disman.db.Profile.USERNAME, "'"+userAccount.getUserName()+"'");
					}
					responseObj.put("message", "Successfully updated the profile");
					responseObj.put("status", "SUCCESS");
					return Response.status(201).entity(responseObj.toString()).build();
				} else {
					String errorMessage = "Please check the fields";
					responseObj.put("message", errorMessage);
					responseObj.put("status", "FAILED");
					return Response.status(400).entity(responseObj.toString()).build();
				}

			} else {
				String errorMessage = "You cannot update profile";
				responseObj.put("message", errorMessage);
				responseObj.put("status", "FAILED");
				return Response.status(400).entity(responseObj.toString()).build();
			}
		} else {
			String errorMessage = "You cannot update profile";
			responseObj.put("message", errorMessage);
			responseObj.put("status", "FAILED");
			return Response.status(400).entity(responseObj.toString()).build();
		}

	}

}

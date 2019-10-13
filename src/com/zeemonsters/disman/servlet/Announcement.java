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

@Path("/announcement")
public class Announcement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Context
	HttpServletRequest request;
	HttpServletResponse response;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	public Response makeAnnouncement(@FormParam("title") String title, @FormParam("tag") String tag,
			@FormParam("description") String description,@FormParam("portal") String portal)
			throws ServletException, IOException {
		JSONObject responseObj = new JSONObject();
		UserAccount userAccount = AppUtils.getLoginedUser(request.getSession());
		if (userAccount != null) {
			Date date = Calendar.getInstance().getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String strDate = formatter.format(date);
			if (userAccount.getRoles().get(0).equals(SecurityConfig.ROLE_VOLUNTEER)) {
				if (Utils.checkNotEmptyOrNull(portal)&&Utils.checkNotEmptyOrNull(description)
						&& Utils.checkNotEmptyOrNull(title) && Utils.checkNotEmptyOrNull(tag)
						&& com.zeemonsters.disman.db.Announcement.insert(new String[] { "'"+ userAccount.getUserName() + "','"  + title + "','" + tag + "','" + description
								+ "',TO_DATE('" + strDate + "', 'DD/MM/YYYY'),'"+portal+"'" })) {
					responseObj.put("message", "Successfully created the announcement");
					responseObj.put("status", "SUCCESS");
					return Response.status(201).entity(responseObj.toString()).build();
				} else {
					String errorMessage = "Please check the fields";
					responseObj.put("message", errorMessage);
					responseObj.put("status", "FAILED");
					return Response.status(400).entity(responseObj.toString()).build();
				}

			} else {
				String errorMessage = "You cannot make announcements";
				responseObj.put("message", errorMessage);
				responseObj.put("status", "FAILED");
				return Response.status(400).entity(responseObj.toString()).build();
			}
		} else {
			String errorMessage = "You cannot make announcements";
			responseObj.put("message", errorMessage);
			responseObj.put("status", "FAILED");
			return Response.status(400).entity(responseObj.toString()).build();
		}

	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getForumDetails(@PathParam("id") String id) {

		JSONArray array = com.zeemonsters.disman.db.Announcement.getAnnouncements(com.zeemonsters.disman.db.Announcement.PORTALID, "'" + id + "'");
		 
		return Response.status(200).entity(array.toString()).build();
	}

}

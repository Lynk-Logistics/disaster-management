package com.zeemonsters.disman.auth.servlet.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zeemonsters.disman.auth.AppUtils;
import com.zeemonsters.disman.auth.SecurityConfig;
import com.zeemonsters.disman.auth.UserAccount;

@WebServlet("/user/bookslot")
public class AddSlot extends HttpServlet {

	private static final long serialVersionUID = 205242440643911308L;

	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserAccount account = AppUtils.getLoginedUser(request.getSession());
		if (account != null) {
			if (account.getRoles().contains(SecurityConfig.ROLE_VOLUNTEER)) {
				RequestDispatcher dispatcher //
						= this.getServletContext().getRequestDispatcher("/WEB-INF/views/userHome.jsp");

				dispatcher.forward(request, response);
			} else {
				response.sendRedirect(request.getServletContext().getContextPath() + "/login");
			}
		} else {
			response.sendRedirect(request.getServletContext().getContextPath() + "/login");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String title = request.getParameter("title");
		String category = request.getParameter("category");
		String desc = request.getParameter("desc");
		String date = request.getParameter("date");

		UserAccount userAccount = AppUtils.getLoginedUser(request.getSession());

		String username = userAccount.getUserName();
		String value = "'" + username + "','" + date + "','" + title + "','" + desc + "','" + category + "'";
//		Booking.insert(new String[] { value });
		response.sendRedirect(request.getServletContext().getContextPath() + "/user/home");

	}
}

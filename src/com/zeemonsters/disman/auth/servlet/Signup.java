package com.zeemonsters.disman.auth.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zeemonsters.disman.auth.AppUtils;
import com.zeemonsters.disman.auth.DataDAO;
import com.zeemonsters.disman.auth.SecurityConfig;
import com.zeemonsters.disman.auth.UserAccount;
import com.zeemonsters.disman.db.DBUtils;
import com.zeemonsters.disman.db.Users;
import com.zeemonsters.disman.db.Utils;

@WebServlet("/signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Signup() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserAccount userAccount = AppUtils.getLoginedUser(request.getSession());
		if (userAccount != null) {
			if (userAccount.getRoles().get(0).equals(SecurityConfig.ROLE_VOLUNTEER)) {
				response.sendRedirect(request.getContextPath() + "/welcome");
			}
		} else {
			RequestDispatcher dispatcher //
					= this.getServletContext().getRequestDispatcher("/WEB-INF/views/signup.jsp");
			dispatcher.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String mobile = request.getParameter("mobile");

		if (Utils.checkNotEmptyOrNull(email) && Utils.checkNotEmptyOrNull(userName)
				&& Utils.checkNotEmptyOrNull(password) && Utils.checkNotEmptyOrNull(mobile)) {
			if (Users.insert(new String[] { "'" + userName + "','" + DBUtils.getMd5(password) + "','" + email + "','"
					+ mobile + "', '" + SecurityConfig.ROLE_VOLUNTEER + "'" })) {
				request.setAttribute("message", "Signed up successfully");
			} else {
				request.setAttribute("message", "Username or email already exists");
			}
		} else {
			request.setAttribute("message", "Please fill the fields properly");
		}
		RequestDispatcher dispatcher //
				= this.getServletContext().getRequestDispatcher("/WEB-INF/views/signup.jsp");
		dispatcher.forward(request, response);

	}

}
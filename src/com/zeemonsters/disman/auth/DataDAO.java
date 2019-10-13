package com.zeemonsters.disman.auth;

import com.zeemonsters.disman.db.Users;

public class DataDAO {

	public static UserAccount findUser(String userName, String password) {
		UserAccount u = Users.checkUser(userName, password);
		return u;
	}

}
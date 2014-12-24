package com.user.model;

import java.util.ArrayList;
import java.util.List;

public class User {
	private UserBasic userBasic;
	private List<UserExtension> userExtensions = null;

	public UserBasic getUserBasic() {
		return userBasic;
	}

	public void setUserBasic(UserBasic userBasic) {
		this.userBasic = userBasic;
	}

	public List<UserExtension> getUserExtensions() {
		return userExtensions;
	}

	public void setUserExtensions(List<UserExtension> userExtensions) {
		this.userExtensions = userExtensions;
	}

	public void addUserExtension(UserExtension userExtension) {
		if (userExtensions == null)
			userExtensions = new ArrayList<UserExtension>();

		userExtensions.add(userExtension);
	}

	public UserExtension getUserExtension(String extValue) {
		if (userExtensions != null) {
			for (int i = 0; i < userExtensions.size(); i++) {
				if (userExtensions.get(i).getExtkey().equals(extValue)) {
					return userExtensions.get(i);
				}
			}
		}
		return null;
	}
}

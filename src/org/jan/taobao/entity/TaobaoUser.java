package org.jan.taobao.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * �Ա��û� ʵ���� �� * @author jan �� * @version 1.0
 */
public class TaobaoUser implements Parcelable {
	private int id;
	private String userName;
	private String userPwd;
	private String email;
	private String phoneNum;
	private String userNiName;

	public TaobaoUser() {
	}

	public TaobaoUser(int id, String userName, String pwd) {
		this.id = id;
		this.userName = userName;
		// this.email = email;
		this.userPwd = pwd;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getUserNiName() {
		return userNiName;
	}

	public void setUserNiName(String userNiName) {
		this.userNiName = userNiName;
	}

	@Override
	public String toString() {
		return "user_id:" + id + ",userName:" + userName + ",password:"
				+ userPwd;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(userName);
		dest.writeString(userPwd);
	}

	public static final Parcelable.Creator<TaobaoUser> CREATOR = new Creator<TaobaoUser>() {
		@Override
		public TaobaoUser[] newArray(int size) {
			return new TaobaoUser[size];
		}

		@Override
		public TaobaoUser createFromParcel(Parcel source) {
			return new TaobaoUser(source.readInt(), source.readString(),
					source.readString());
		}
	};

}

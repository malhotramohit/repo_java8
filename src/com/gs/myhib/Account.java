package com.gs.myhib;

@MyEntity(name = "account")
@MyTable(name = "GS_Account")
public class Account {

	private Integer accId;

	private String accName;

	public Account() {
		super();
	}

	@Override
	public String toString() {
		return "Account [accId=" + accId + ", accName=" + accName + "]";
	}

	@MyColumn(name = "id")
	@MyId
	public Integer getAccId() {
		return accId;
	}

	public void setAccId(Integer accId) {
		this.accId = accId;
	}

	@MyColumn(name = "name")
	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public Account(Integer accId, String accName) {
		super();
		this.accId = accId;
		this.accName = accName;
	}

}

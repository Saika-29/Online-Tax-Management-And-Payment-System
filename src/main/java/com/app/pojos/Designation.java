package com.app.pojos;

public enum Designation {

	JUNIOR_DEVELOPER(25000), SENIOR_DEVELOPER(65000), TESTER(22000), MANAGER(80000), CLERK(36000), OPERATIONS(44000),
	NETWORKING(53000), SALES(47000), HR(15000);

	private double basic;

	Designation(double basic) {
		this.basic = basic;
	}

	public double getBasic() {
		return basic;
	}

}

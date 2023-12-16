package com.olah.bank.dataLayer;

public enum TypeMoney {
	UAH("Гривня ₴", 1),
	USD("Доллар $", 36),
	EURO("Євро €", 40);

	private String name;
	private double coefficient;

	TypeMoney(String name, double coefficient) {
		this.name = name;
		this.coefficient = coefficient;
	}

	public String getName() {
		return name;
	}

	public double getCoefficient() {
		return coefficient;
	}
}
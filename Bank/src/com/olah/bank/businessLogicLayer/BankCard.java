package com.olah.bank.businessLogicLayer;

import com.olah.bank.dataLayer.TypeMoney;

import java.util.Random;

public class BankCard {
	private String id;
	private TypeMoney cardType;
	private String visualId;

	public BankCard(String id, TypeMoney cardType, String visualId) {
		this.id = id;
		this.cardType = cardType;
		this.visualId = visualId;
	}
	public BankCard(String id, TypeMoney cardType) {
		this.id = id;
		this.cardType = cardType;
		this.visualId = generatorRandomVisualId();
	}
	public String getId() {
		return id;
	}
	public TypeMoney getCardType() {
		return cardType;
	}
	public String getVisualId() {
		return visualId;
	}
	private String generatorRandomVisualId() {
		Random random = new Random();
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < 4; i++) {
			int set = 1000 + random.nextInt(9000);
			stringBuilder.append(set);

			if (i < 3) {
				stringBuilder.append(" ");
			}
		}
		return stringBuilder.toString();
	}
}

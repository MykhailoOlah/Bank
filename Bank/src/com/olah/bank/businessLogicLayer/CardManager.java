package com.olah.bank.businessLogicLayer;

import com.olah.bank.dataLayer.TypeMoney;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public final class CardManager {
	private final List<BankCard> bankCards = new ArrayList<>();
	private final AccountManager accountManager;

	public CardManager(AccountManager accountManager) {
		this.accountManager = accountManager;
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

	public void addCard(TypeMoney cardType, String id) {
		String visualId = generatorRandomVisualId();
		visualId = formatVisualId(visualId);
		BankCard bankCard = new BankCard(id, cardType, visualId);
		bankCards.add(bankCard);
	}
	private String formatVisualId(String visualId) {
		String[] parts = visualId.split(" ");
		StringBuilder formattedVisualId = new StringBuilder();

		for (int i = 0; i < parts.length; i++) {
			formattedVisualId.append(String.format("%04d", Integer.parseInt(parts[i])));

			if (i < parts.length - 1) {
				formattedVisualId.append(" ");
			}
		}
		return formattedVisualId.toString();
	}

	public static TypeMoney getTypeMoneyByIndex(int index) {
		TypeMoney[] types = TypeMoney.values();
		if (index >= 1 && index <= types.length) {
			return types[index - 1];
		}
		return null;
	}
	public boolean addMoney(double amount, BankCard selectedCard, String authorizedAccountID) {
		double coefficient = selectedCard.getCardType().getCoefficient();

		List<Account> accountList = accountManager.getAccountList();

		for (Account account : accountList) {
			if (account.getPassportID().equals(authorizedAccountID)) {
				account.setBalance(account.getBalance() + amount * coefficient);
				return true;
			}
		}
		return false;
	}

	public boolean buyProduct(double amount, String authorizedAccountID, String productName) {
		List<Account> accountList = accountManager.getAccountList();

		for (Account account : accountList) {
			if (account.getPassportID().equals(authorizedAccountID)) {
				if (account.getBalance() >= amount) {
					account.setBalance(account.getBalance() - amount);

					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					String dateAndTime = sdf.format(new Date());

					System.out.println("[" + account.getFullName() + "] – купив/ла " +
					    productName + " (1) " + amount + " грн. " +
					    dateAndTime + ". Залишок на балансі – " +
					    account.getBalance() + " грн.");

					return true;
				}
			}
		}
		return false;
	}
	public BankCard getCardByIndex(int index, String authorizedAccountID) {
		int cardNumber = 1;
		for (BankCard bankCard : bankCards) {
			if (bankCard.getId().equals(authorizedAccountID)) {
				if (cardNumber == index) {
					return bankCard;
				}
				cardNumber++;
			}
		}
		return null;
	}
	public List<BankCard> getBankCards() {
		return bankCards;
	}
}

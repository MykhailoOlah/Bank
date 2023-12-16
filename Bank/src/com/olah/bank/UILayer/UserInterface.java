package com.olah.bank.UILayer;

import com.olah.bank.businessLogicLayer.*;
import com.olah.bank.dataLayer.TypeMoney;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
	private static final Scanner scanner = new Scanner(System.in);
	private static final AccountManager accountCreater = new AccountManager();
	private static final CardManager cardManager = new CardManager(accountCreater);
	private static final List<Account> accounts = accountCreater.getAccountList();
	private static final List<BankCard> bankCards = cardManager.getBankCards();
	private static int turn = 1;

	private static final Shop shop = new Shop();
	private String authorizedAccountID;
	public void authMenu() { // меню реєстрації та авторизації
		int choice;
		do {
			System.out.println("$ ₴ € $ ₴ € $ ₴ € $");
			System.out.println("$ Мигальовий Банк $");
			System.out.println("1: Авторизація");
			System.out.println("2: Реєстрація");
			System.out.println("0: Вихід");
			System.out.println("$ ₴ € $ ₴ € $ ₴ € $");
			System.out.print("Вибір: ");
			choice = scanner.nextInt();

			switch (choice) {
				case 1:
					authorizedAccount();
					break;
				case 2:
					linkToCreateAccount();
					break;
				case 0:
					System.out.println("Вихід з програми");
					break;
				default:
					System.out.println("Невірний вибір!");
					break;
			}
		} while (choice != 0);
	}

	public void mainMenu() { // головне меню
		int choice;
		do {

			System.out.println("$ ₴ € $ ₴ € $ ₴ € $ ₴ € $ ₴ € $ ₴ € $ ₴ € $ ₴ € $ ₴ €");
			System.out.println("$ Мигальовий Банк $");
			System.out.println("1: Створення картки");
			System.out.println("2: Перегляд інформації про картки та обліковий запис");
			System.out.println("3: Поповнення балансу");
			System.out.println("4: Придбання товару");
			System.out.println("0: Перехід в меню реєстрації/авторизації");
			System.out.println("$ ₴ € $ ₴ € $ ₴ € $ ₴ € $ ₴ € $ ₴ € $ ₴ € $ ₴ € $ ₴ €");
			System.out.print("Вибір: ");
			choice = scanner.nextInt();

			switch (choice) {
				case 1:
					linkToCreateCard();
					break;
				case 2:
					displayAllAcc();
					displayAllCard();
					break;
				case 3:
					addMoney();
					break;
				case 4:
					buyProduct();
					break;
				case 0:
					System.out.println("Вихід з програми");
					break;
				default:
					System.out.println("Невірний вибір!");
					break;
			}
		} while (choice != 0);
	}
	public void shopProductsDisplay() {
		List<Product> productList = shop.getProductList();
		int productsPerRow = 3;

		System.out.println("-------------------------------------------");
		System.out.printf("|%-4s|%-25s|%-10s|%n","№","Продукт","Ціна(грн)");
		System.out.println("-------------------------------------------");

		for (int i = 0; i < productList.size(); i++) {
			Product product = productList.get(i);

			System.out.printf("|%-4d|%-25s|%-10d|%n", product.getId(), product.getName(), product.getPrice());

			if ((i + 1) % productsPerRow == 0 || (i + 1) == productList.size()) {
				System.out.println("-------------------------------------------");
			}
		}
	}


	public void buyProduct() {
		shopProductsDisplay();
		System.out.print("Виберіть продукт: ");
		int productIndex = scanner.nextInt();

		double productCost = shop.getProductCostByIndex(productIndex);

		if (cardManager.buyProduct(productCost, authorizedAccountID, shop.getProductList().get(productIndex).getName())) {
			System.out.println("Продукт придбано!");
		} else {
			System.out.println("Помилка! Недостатньо коштів або неправильний індекс продукту!");
		}
	}
	public void addMoney(){
		System.out.print("Впишіть суму поповнення: ");
		int money = scanner.nextInt();
		System.out.println("Оберіть потрібну картку: ");

		displayAllCard();

		int indexCard = scanner.nextInt();
		if(cardManager.addMoney(money, cardManager.getCardByIndex(indexCard, authorizedAccountID), authorizedAccountID)){
			System.out.println("Баланс успішно поповнено!");
		}else{
			System.out.println("Помилка!");
		}
	}
	public void authorizedAccount() { // авторизація
		Person person = new Person(accountCreater);

		scanner.nextLine();

		System.out.print("Введіть id-паспорту: ");
		String userPassportID = scanner.nextLine();

		System.out.print("Введіть пароль: ");
		String userParol = scanner.nextLine();

		if (person.authorized(userPassportID, userParol)) {
			System.out.println("Вас авторизовано!");
			authorizedAccountID = userPassportID;
			mainMenu();
		} else {
			System.out.println("Такого користувача не існує");
		}
	}
	public void displayAllAcc() { // показ аккаунта
		for (Account account : accounts) {
			if (authorizedAccountID.equals(account.getPassportID())) {
				System.out.println("Логін: " + account.getFullName()
				    + ", ID: " + account.getPassportID()
				    + ", баланс: " + account.getBalance() + " грн.");
			}
		}
	}
	public void displayAllCard() { //показ карт які привязані до аккаунта
		if (bankCards.isEmpty()){
			System.out.println("Картки відсутні!");
		}

		turn = 1;

		for (BankCard bankCard : bankCards) {
			if (authorizedAccountID.equals(bankCard.getId())) {
				System.out.println(turn + ": "
				    + "тип валюти: " + bankCard.getCardType().getName() + " ID: " + bankCard.getVisualId());
			}
			turn++;
		}
	}
	public void linkToCreateAccount() { // створення облікового запису

		scanner.nextLine();

		System.out.print("Введіть логін: ");
		String fullName = scanner.nextLine();

		System.out.print("Введіть паспорт-ID: ");
		String passportID = scanner.nextLine();

		System.out.print("Введіть пароль: ");
		String parol = scanner.nextLine();

		if (accountCreater.createAccount(fullName, passportID, parol)) {
			authorizedAccountID = passportID;
			System.out.println("Обліковий запис успішно створено!");
			mainMenu();
		} else {
			System.out.println("Не вдалося додати карту! "
			    + "Обліковий запис з введеним ID паспорту вже існує.");
		}
	}
	private void displayCardTypes() {
		System.out.println("Типи карток, які підтримують певну валюту:");
		TypeMoney[] types = TypeMoney.values();
		for (int i = 0; i < types.length; i++) {
			System.out.println((i + 1) + ": " + types[i].getName());
		}
	}
	public void linkToCreateCard() { // створення карти
		scanner.nextLine();


		System.out.println("Введіть потрібний тип картки зі списку: ");
		displayCardTypes();

		System.out.print("Ваш вибір: ");
		int typeIndex = scanner.nextInt();

		TypeMoney cardType = CardManager.getTypeMoneyByIndex(typeIndex);

		if (cardType != null) {
			cardManager.addCard(cardType, authorizedAccountID);
			System.out.println("Карта успішно додана!");
		} else {
			System.out.println("Введено невірний номер типу картки.");
		}
	}
}
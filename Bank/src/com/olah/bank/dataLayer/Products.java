package com.olah.bank.dataLayer;

public enum Products {
	APPLE("Яблуко"),
	BANANA("Банан"),
	ORANGE("Апельсин"),
	STRAWBERRY("Полуниця"),
	TOMATO("Помідор"),
	CARROT("Морква"),
	CUCUMBER("Огірок"),
	LETTUCE("Салат"),
	BROCCOLI("Брокколі"),
	ONION("Цибуля"),
	GRAPES("Виноград"),
	WATERMELON("Кавун"),
	PINEAPPLE("Ананас"),
	MANGO("Манго"),
	PEACH("Персик"),
	PEAR("Груша"),
	PLUM("Слива"),
	CHERRY("Вишня"),
	BLUEBERRY("Черешня"),
	RASPBERRY("Малина");

	String name;

	Products(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}

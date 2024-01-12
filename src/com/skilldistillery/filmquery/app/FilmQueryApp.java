package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//		app.test();
    app.launch();
	}

//	private void test() {
//		Film film;
//		List<Actor> actor;
//		film = db.findFilmById(1);
//		actor = db.findActorById(1);
//		actor = db.findActorsByFilmId(1);
//		
//		System.out.println(film);
//	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
		int userInput = 0;
		
		System.out.println("Please choose between the options below");
		System.out.println("(1)Look up a film by its id");
		System.out.println("(2)Look up a film by a search keyword");
		System.out.println("(3)Exit the application");
		
		
		while (userInput != 3) {
			
		}
	}

}
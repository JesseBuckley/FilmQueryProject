package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
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
		int choice = 0;

		while (choice != 3) {
			printMenu();
			 choice = getUserChoice();
			
			switch (choice) {
			case 1:
				lookupFilmById();
				break;
			case 2:
				lookupFilmByKeyword();
				break;
			case 3:
				System.out.println("Exiting the application... See ya!");
				break;
			default:
				System.out.println("That is not a valid input. Please try again.");
				break;
			}

		}
	}

	private int getUserChoice() {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter your choice (1-3):");
		while (!input.hasNextInt()) {
			input.nextLine();
			
			System.out.println("invalid");
		}
		int choice = input.nextInt();
		input.nextLine();
		return choice;
}

	private void lookupFilmById() {
		Scanner input = new Scanner(System.in);

		System.out.println("Enter the film ID: ");
		
		
		while (!input.hasNextLine()) {
			input.nextLine();
			
			System.out.println("invalid");
		}
		int filmId = input.nextInt();
		input.nextLine();

		Film film = db.findFilmById(filmId);
				
		if (film != null) {
			displayFilmDetails(film);
		} else {
			System.out.println("Film not found for ID: " + filmId);
		}
	}

	private void displayFilmDetails(Film film) {
		System.out.println("Film Details: ");
		System.out.println("ID:" + film.getId());
		System.out.println("Title: " + film.getTitle());
		System.out.println("Year: " + film.getReleaseYear());
		System.out.println("Rating: " + film.getRating());
		System.out.println("Description: " + film.getDescription());

	}

	private void lookupFilmByKeyword() {
		Scanner input = new Scanner(System.in);
		input.nextLine();
		System.out.println("Enter the search keyword: ");
		String keyword = input.nextLine();
		List<Film> films = db.findFilmByKeyword(keyword);

		if (!films.isEmpty()) {
			for (Film film : films) {
				displayFilmDetails(film);
			}
		} else {
			System.out.println("No films found for the keyword: " + keyword);
		}
	}

	private void printMenu() {
		System.out.println("==========Please choose between the options below==========");
		System.out.println("(1)Look up a film by its id");
		System.out.println("(2)Look up a film by a search keyword");
		System.out.println("(3)Exit the application");
		System.out.println("===========================================================");
	}

}
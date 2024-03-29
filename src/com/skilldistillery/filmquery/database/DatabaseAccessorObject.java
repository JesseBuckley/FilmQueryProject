package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	public static final String user = "student";
	public static final String pass = "student";

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		String filmSql = "SELECT * FROM film WHERE id = ?";
		String actorSql = "SELECT * FROM actor JOIN film_actor ON "
				+ "actor.id = film_actor.actor_id WHERE film_actor.film_id = ?";

		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement filmStmt = conn.prepareStatement(filmSql);
				PreparedStatement actorStmt = conn.prepareStatement(actorSql);) {

			filmStmt.setInt(1, filmId);

			try (ResultSet filmR = filmStmt.executeQuery();) {
				if (filmR.next()) {
					film = new Film();
					film.setId(filmR.getInt("id"));
					film.setTitle(filmR.getString("title"));
					film.setReleaseYear(filmR.getInt("release_year"));
					film.setRating(filmR.getString("rating"));
					film.setDescription(filmR.getString("description"));
					film.setLanguageId(filmR.getInt("language_id"));

					actorStmt.setInt(1, filmId);
					List<Actor> actors = new ArrayList<>();

					try (ResultSet actorR = actorStmt.executeQuery()) {
						while (actorR.next()) {
							Actor actor = new Actor();
							actor.setId(actorR.getInt("id"));
							actor.setFirstName(actorR.getString("first_name"));
							actor.setLastName(actorR.getString("last_name"));

							actors.add(actor);
						}
					}
					film.setActors(actors);

				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return film;
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";

		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, actorId);

			try (ResultSet actorResult = stmt.executeQuery()) {
				if (actorResult.next()) {
					actor = new Actor();
					actor.setId(actorResult.getInt("id"));
					actor.setFirstName(actorResult.getString("first_name"));
					actor.setLastName(actorResult.getString("last_name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		String sql = "SELECT * FROM actor JOIN film_actor ON actor.id = film_actor.actor_id WHERE film_actor.film_id = ?";

		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, filmId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Actor actor = new Actor();
					actor.setId(rs.getInt("id"));
					actor.setFirstName(rs.getString("first_name"));
					actor.setLastName(rs.getString("last_name"));

					actors.add(actor);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;
	}

	@Override
	public List<Film> findFilmByKeyword(String word) {
		List<Film> films = new ArrayList<>();

		String sql = "SELECT * FROM film WHERE title LIKE ? OR description LIKE ?";

		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement stmt = conn.prepareStatement(sql);) {

			String search = "%" + word + "%";
			stmt.setString(1, search);
			stmt.setString(2, search);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Film film = createFilmFromResultSet(rs);

					films.add(film);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return films;
	}

	private Film createFilmFromResultSet(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String title = rs.getString("title");
		int year = rs.getInt("release_year");
		String rating = rs.getString("rating");
		String description = rs.getString("description");
		int languageId = rs.getInt("language_id");

		return new Film(id, title, year, rating, description, languageId);
	}

	public String getLanguageStringById(int languageId) {
		String sql = "SELECT name FROM language WHERE id = ?";

		try (Connection conn = DriverManager.getConnection(URL, user, pass);
				PreparedStatement stmt = conn.prepareStatement(sql);) {

			stmt.setInt(1, languageId);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					return rs.getString("name");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

}

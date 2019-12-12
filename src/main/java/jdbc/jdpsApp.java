package jdbc;

import java.sql.*;
import java.util.Scanner;


public class jdpsApp {

    final static String JDBC_CONNECTION_STR = "jdbc:mysql://127.0.0.1:3306/marvel";
    final static String USERNAME = "root";
    final static String PASSWORD = "gok123nur";
    private static void heroListele() {

        String sql = "Select * from hero";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int heroId = resultSet.getInt("heroId");
                String name = resultSet.getString("Name");
                String surname = resultSet.getString("Surname");

                System.out.printf("%d - %s - %s \n", heroId, name, surname);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // HERO TABLOSU GETİRİLİR:
    private static void movieListele() {

        System.out.println("Movie listelendi....");
        String sql = "Select * from movie";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String movie = resultSet.getString("movie");
                int movieBudget = resultSet.getInt("movieBudget");
                int heroId = resultSet.getInt("heroId");

                System.out.printf("%s - %d - %d \n", movie, movieBudget, heroId);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   //MOVIE TABLOSU GETİRİLİR.

    private static void heroEkle(Hero hero) {

        String sql = "insert into hero (name, surname) " +
                "values (?, ?) ";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

           // preparedStatement.setInt(1, hero.getHeroId());
            preparedStatement.setString(1, hero.getName());
            preparedStatement.setString(2, hero.getSurname());

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(affectedRows + " satır eklendi.");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   // HERO TABLOSUNA VERİLER EKLENDİ.
    private static void movieEkle(Movie movie) {

        String sql = "insert into movie (heroId, movie, movieBudget) " +
                "values (?, ?, ?) ";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, movie.getHeroId());
            preparedStatement.setString(2, movie.getMovie());
            preparedStatement.setInt(3, movie.getMovieBudget());


            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(affectedRows + " satır eklendi.");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }  // MOVIE TABLOSUNA VERİLER EKLENDİ.

    private static void heroListele(String name) {

        String sql = "Select * from hero where name like ?";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, "%" + name + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int heroId = resultSet.getInt("heroId");
                String name1 = resultSet.getString("Name");
                String surname = resultSet.getString("Surname");

                System.out.printf("%d - %s - %s \n", heroId, name1, surname);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   // HERO TABLOSUNDA NAME ARA.
    private static void movieListele(String movieName) {

        String sql = "Select * from movie where movie like ?";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(2, "%" + movieName + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String movie = resultSet.getString("name");
                int movieBudget = resultSet.getInt("movieBudget");
                int heroId = resultSet.getInt("heroId");

                System.out.printf("%s - %d - %d \n", movie, movieBudget, heroId);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   // MOVIE TABLOSUNDA MOVIEFİLM ARA.

    private static void totalBudget() {

        System.out.println("Total budget listelendi....");

        String sql = "select concat(hero.name," + "' '" + ",hero.surname) as hero,"+
        " sum(movie.movieBudget) as total_budget "+
        "from hero hero " +
        "left join movie movie "+
        "on hero.heroId = movie.heroId " +
        "group by concat(hero.name," + "' '" +",hero.surname) " +
        "order by sum(movie.movieBudget) desc";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("hero");
                String totalBudget = resultSet.getString("total_budget");
                //String totalBudget= resultSet.getString("totalBudget");

                System.out.printf(" %s - %s  \n", name, totalBudget);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void movieCount(){

        System.out.println("Movie Count listelendi....");
        String sql = "select concat(hero.name,"+"' '"+ ",hero.surname) as hero, " +
                                "count(movie.heroId) as movie_count " +
                                "from hero hero " +
                                  "inner join movie on hero.heroId=movie.heroId " +
                                 "group by hero "+
                                 "order by movie_count desc";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                String name = resultSet.getString("hero");
                String movieCount = resultSet.getString("movie_count");


                System.out.printf("%s - %s \n",name,movieCount);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {

        boolean isBaglantiHazir = baglantiyiKontrolEt();
        if (!isBaglantiHazir) {
            System.out.println("Bağlantı problemi var. Lütfen kontrol edin.");
        } else {

            heroListele();
            movieListele();
            totalBudget();
            movieCount();;

            Scanner scanner = new Scanner(System.in);

            System.out.println("HeroId giriniz...");
            int id = scanner.nextInt();

            System.out.println("Name");
            String name = scanner.nextLine();

            System.out.println("Surname");
            String surname = scanner.nextLine();

            System.out.println("Movie");
            String movie = scanner.nextLine();

            System.out.println("movieBudget");
            int movieBudget = scanner.nextInt();


            Hero hero = new Hero();
            hero.setHeroId(id);
            hero.setName(name);
            hero.setSurname(surname);
            heroEkle(hero);

            Movie movie1 = new Movie();
            movie1.setMovie(movie);
            movie1.setMovieBudget(movieBudget);
            movie1.setHeroId(id);
            movieEkle(movie1);

            System.out.println("Aranacak adı giriniz");
            String aranacakAd = scanner.nextLine();
            heroListele(aranacakAd);

            System.out.println("Aranacak filmi giriniz");
            String aranacakFilm = scanner.nextLine();
            movieListele(aranacakFilm);
        }
    }

    private static boolean baglantiyiKontrolEt() {

        try (Connection conn = DriverManager.getConnection(
                JDBC_CONNECTION_STR, USERNAME, PASSWORD)) {

            if (conn != null) {
                return true;
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}

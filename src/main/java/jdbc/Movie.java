package jdbc;

public class Movie {

    private String movie;
    private int movieBudget;
    private String totalBudget;
    private String movieCount;



    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public int getMovieBudget() {
        return movieBudget;
    }

    public void setMovieBudget(int movieBudget) {
        this.movieBudget = movieBudget;
    }

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    private int heroId;

    public String getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(String totalBudget) {
        this.totalBudget = totalBudget;
    }

    public String getMovieCount() {
        return movieCount;
    }

    public void setMovieCount(String movieCount) {
        this.movieCount = movieCount;
    }
}

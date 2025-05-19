package com.pregame.gametesting.model;

import java.math.BigDecimal; // For DECIMAL type from DB
import java.util.Date;
import java.util.Objects;

public class Game implements java.io.Serializable {
    private int gameId;
    private String title;
    private Date releaseDate;
    private String esrbRating; // Schema has 'ESRB'
    private String type;
    private BigDecimal size;   // Schema has DECIMAL(5,2), use BigDecimal for precision
    private String version;
    private String description; // Schema has 'DescriptionofGame'
    private int gameDeveloperId; // Foreign Key

    // Optional full object, to be populated by a service layer
    private GameDeveloper developer;

    public Game() {
    }

    // Constructor with essential fields
    public Game(String title, Date releaseDate, String esrbRating, String type,
                BigDecimal size, String version, String description, int gameDeveloperId) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.esrbRating = esrbRating;
        this.type = type;
        this.size = size;
        this.version = version;
        this.description = description;
        this.gameDeveloperId = gameDeveloperId;
    }


    // --- Getters and Setters ---
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getEsrbRating() {
        return esrbRating;
    }

    public void setEsrbRating(String esrbRating) {
        this.esrbRating = esrbRating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getSize() { // Use BigDecimal
        return size;
    }

    public void setSize(BigDecimal size) { // Use BigDecimal
        this.size = size;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGameDeveloperId() {
        return gameDeveloperId;
    }

    public void setGameDeveloperId(int gameDeveloperId) {
        this.gameDeveloperId = gameDeveloperId;
        // If the full developer object exists and its ID doesn't match, nullify it
        // to signal that it might be stale and needs reloading by the service.
        if (this.developer != null && this.developer.getGameDeveloperid() != gameDeveloperId) {
            this.developer = null;
        }
    }

    public GameDeveloper getDeveloper() {
        return developer;
    }

    public void setDeveloper(GameDeveloper developer) {
        this.developer = developer;
        // If a developer object is set, ensure its ID matches our gameDeveloperId
        if (developer != null) {
            this.gameDeveloperId = developer.getGameDeveloperid();
        }
        // If developer is set to null, gameDeveloperId remains as is.
        // You could choose to set gameDeveloperId = 0 here if developer is nulled.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return gameId == game.gameId &&
                gameDeveloperId == game.gameDeveloperId &&
                Objects.equals(title, game.title) &&
                Objects.equals(version, game.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, title, version, gameDeveloperId);
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", title='" + title + '\'' +
                ", version='" + version + '\'' +
                ", gameDeveloperId=" + gameDeveloperId +
                '}';
    }
}
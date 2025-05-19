 package com.pregame.gametesting.model;
 // Or your package

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class Feedback {
    private int gamerId;        // Foreign Key to Gamer
    private int gameId;         // Foreign Key to Game
    private String feedbackText;
    private byte[] attachments;
    private Date feedbackDate;

    // Optional full objects, populated by a service layer
    private Gamer gamer;
    private Game game;

    public Feedback() {
        this.feedbackDate = new Date(); // Default to current date
    }

    public Feedback(int gamerId, int gameId, String feedbackText, byte[] attachments, Date feedbackDate) {
        this.gamerId = gamerId;
        this.gameId = gameId;
        this.feedbackText = feedbackText;
        this.attachments = attachments;
        this.feedbackDate = (feedbackDate != null) ? feedbackDate : new Date();
    }

    // --- Getters and Setters for IDs ---
    public int getGamerId() {
        return gamerId;
    }

    public void setGamerId(int gamerId) {
        this.gamerId = gamerId;
        // If full Gamer object exists and its ID doesn't match, nullify it
        if (this.gamer != null && this.gamer.getGamerId() != gamerId) {
            this.gamer = null;
        }
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
        // If full Game object exists and its ID doesn't match, nullify it
        if (this.game != null && this.game.getGameId() != gameId) {
            this.game = null;
        }
    }

    // --- Getters and Setters for content ---
    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public byte[] getAttachments() {
        return attachments;
    }

    public void setAttachments(byte[] attachments) {
        this.attachments = attachments;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    // --- Getters and Setters for full objects (used by service layer) ---
    public Gamer getGamer() {
        return gamer;
    }

    public void setGamer(Gamer gamer) {
        this.gamer = gamer;
        if (this.gamer != null) {
            this.gamerId = this.gamer.getGamerId(); // Keep ID in sync
        }
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
        if (this.game != null) {
            this.gameId = this.game.getGameId(); // Keep ID in sync
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return gamerId == feedback.gamerId &&
                gameId == feedback.gameId &&
                Objects.equals(feedbackText, feedback.feedbackText) &&
                Arrays.equals(attachments, feedback.attachments) &&
                Objects.equals(feedbackDate, feedback.feedbackDate);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(gamerId, gameId, feedbackText, feedbackDate);
        result = 31 * result + Arrays.hashCode(attachments);
        return result;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "gamerId=" + gamerId +
                ", gameId=" + gameId +
                ", feedbackText='" + (feedbackText != null ? feedbackText.substring(0, Math.min(feedbackText.length(), 30)) + "..." : "null") + '\'' +
                ", feedbackDate=" + feedbackDate +
                '}';
    }
}
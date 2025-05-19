package com.pregame.gametesting.model;

import java.util.Date;

public class Review {
    private Tester tester;
    private Game game;
    private String reviewText;
    private byte[] attachments;
    private Date reviewDate;
    private int testerId; // Added direct fields for IDs
    private int gameId;

    public Review() {
        this.reviewDate = new Date();
    }

    public Review(Tester tester, Game game, String reviewText, byte[] attachments) {
        this.tester = tester;
        if (tester != null) {
            this.testerId = tester.getTesterId();
        }
        this.game = game;
        if (game != null) {
            this.gameId = game.getGameId();
        }
        this.reviewText = reviewText;
        this.attachments = attachments;
        this.reviewDate = new Date();
    }

    public Tester getTester() {
        return this.tester;
    }

    public void setTester(Tester tester) {
        this.tester = tester;
        if (tester != null) {
            this.testerId = tester.getTesterId();
        }
    }

    public void setTesterId(int testerId) {
        this.testerId = testerId;
        if (this.tester != null) {
            this.tester.setTesterId(testerId);
        }
    }

    public int getTesterId() {
        return this.testerId;
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
        if (game != null) {
            this.gameId = game.getGameId();
        }
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
        if (this.game != null) {
            this.game.setGameId(gameId);
        }
    }

    public int getGameId() {
        return this.gameId;
    }

    public String getReviewText() {
        return this.reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public byte[] getAttachments() {
        return this.attachments;
    }

    public void setAttachments(byte[] attachments) {
        this.attachments = attachments;
    }

    public Date getReviewDate() {
        return this.reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }
}
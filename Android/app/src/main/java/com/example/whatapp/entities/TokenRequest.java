package com.example.whatapp.entities;

public class TokenRequest {
    private String tokenId;

    public TokenRequest(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}

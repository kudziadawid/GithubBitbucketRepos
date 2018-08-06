package com.kudziadawid.githubbitbucketrepos.model;

public class SingleRepo {

    private String repoName;
    private String ownerName;
    private String avatarUrl;
    private String repoDescription;

    public SingleRepo(String repoName, String ownerName, String avatarUrl, String repoDescription) {

        this.repoName = repoName;
        this.ownerName = ownerName;
        this.avatarUrl = avatarUrl;
        this.repoDescription = repoDescription;
    }

    public SingleRepo() {

    }

    public String getRepoName() {
        return repoName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getRepoDescription() {
        return repoDescription;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setRepoDescription(String repoDescription) {
        this.repoDescription = repoDescription;
    }
}
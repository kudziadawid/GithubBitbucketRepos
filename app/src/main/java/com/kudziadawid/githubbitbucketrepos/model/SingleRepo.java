package com.kudziadawid.githubbitbucketrepos.model;

public class SingleRepo {

    private String repoName;
    private String ownerName;
    private String avatarUrl;
    private String repoDescription;
    private boolean bitbucket;

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

    public boolean isBitbucket() {
        return bitbucket;
    }

    public void setBitbucket(boolean bitbucket) {
        this.bitbucket = bitbucket;
    }
}

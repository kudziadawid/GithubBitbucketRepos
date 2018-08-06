package com.kudziadawid.githubbitbucketrepos.model;

import java.util.List;

public class Repos {

    private List<SingleRepo> repoList;

    public Repos() {
    }

    public void addToRepos(SingleRepo singleRepo) {
        repoList.add(singleRepo);
    }

    public List<SingleRepo> getRepoList() {
        return repoList;
    }
}

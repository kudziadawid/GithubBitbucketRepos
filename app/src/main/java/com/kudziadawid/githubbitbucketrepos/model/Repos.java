package com.kudziadawid.githubbitbucketrepos.model;

import java.util.ArrayList;
import java.util.List;

public class Repos {

    private List<SingleRepo> repoList = new ArrayList<>();

    public Repos() {
    }

    public void addToRepos(SingleRepo singleRepo) {
        repoList.add(singleRepo);
    }

    public List<SingleRepo> getRepoList() {
        return repoList;
    }
}

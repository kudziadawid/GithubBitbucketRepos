package com.kudziadawid.githubbitbucketrepos.comparator;

import com.kudziadawid.githubbitbucketrepos.model.SingleRepo;

import java.util.Comparator;

public class SingleRepoComparator implements Comparator<SingleRepo> {

    @Override
    public int compare(SingleRepo o1, SingleRepo o2) {
        return o1.getRepoName().toUpperCase().compareTo(o2.getRepoName().toUpperCase());
    }
}

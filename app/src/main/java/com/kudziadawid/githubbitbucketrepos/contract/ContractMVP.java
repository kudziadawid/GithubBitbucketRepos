package com.kudziadawid.githubbitbucketrepos.contract;

import com.kudziadawid.githubbitbucketrepos.model.SingleRepo;

import java.util.List;

public interface ContractMVP {

    interface View {
        void showRepos(List<SingleRepo> reposList);
    }

    interface Presenter {
        void getRepos();
    }
}

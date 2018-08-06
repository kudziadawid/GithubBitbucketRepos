package com.kudziadawid.githubbitbucketrepos.contract;

public interface ContractMVP {

    interface View {
        void showRepos(String name);
    }

    interface Presenter {
        void getRepos();
    }
}

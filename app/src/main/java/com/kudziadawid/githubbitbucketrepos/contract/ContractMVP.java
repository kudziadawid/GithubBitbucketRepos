package com.kudziadawid.githubbitbucketrepos.contract;

public interface ContractMVP {

    interface View {
        void showRepos();
    }

    interface Presenter {
        void getRepos();
    }
}

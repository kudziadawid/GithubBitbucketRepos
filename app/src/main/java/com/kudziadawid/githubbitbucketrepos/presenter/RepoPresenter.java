package com.kudziadawid.githubbitbucketrepos.presenter;

import com.kudziadawid.githubbitbucketrepos.contract.ContractMVP;
import com.kudziadawid.githubbitbucketrepos.model.Repo;

public class RepoPresenter extends BasePresenter<ContractMVP.View> implements ContractMVP.Presenter {

    private Repo repo;

    public RepoPresenter(Repo repo) {
        this.repo = repo;
    }

    @Override
    public void getRepos() {

        view.showRepos();
    }
}

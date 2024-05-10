import RepositoryService from '../../services/RepositoryService';
import { addNewAC, setLoadingAC, setCurrentRepositoryAC, setTokenAC, setTokenLoadingAC, setRepositoriesAC, deleteRepositoryAC } from '../actionCreators/repositoryActionCreators';

export const addNewRepository = (repository) => async (dispatch) => {
    dispatch(setLoadingAC());

    // imitate loading
    setTimeout(() => {
        dispatch(addNewAC(repository));
    }, 500)
}

export const setCurrentRepository = (repositoryId) => async (dispatch) => {
    dispatch(setLoadingAC());

    let repositoryObj = {
        "displayPath" : "/",
        "displayname" : "repos1",
        "displayFullName" : "/repos2",
        "description" : "some description1",
        "access_types" : ["protected_rw", "public_r"],
        "owner" : "username1"
    };

    // imitate loading
    setTimeout(() => {
        dispatch(setCurrentRepositoryAC(repositoryObj));
    }, 500)
}

export const setRepositoryAccess = (username, repositoryId, accessTypes) => async (dispatch) => {
    dispatch(setLoadingAC());

    let repositoryObj = {
        "displayPath" : "/",
        "displayname" : "repos1",
        "displayFullName" : "/repos1",
        "description" : "some description1",
        "access_types" : accessTypes,
        "owner" : "username1"
    };

    // imitate loading
    setTimeout(() => {
        dispatch(setCurrentRepositoryAC(repositoryObj));
    }, 500)
}

export const getRepositoryToken = (repositoryId) => async (dispatch) => {
    dispatch(setTokenLoadingAC());

    let token = 'fdgfdgfdgfdg';

    // imitate loading
    setTimeout(() => {
        dispatch(setTokenAC(token));
    }, 500)
}

export const loadLatestRepositories = (page = 1, size = 10) => async (dispatch) => {
    dispatch(setLoadingAC());

    let reps = {
        content: [
            {
                "displayPath" : "/",
                "displayname" : "repos1",
                "displayFullName" : "/repos1",
                "description" : "some description1",
                "access_types" : 'accessTypes',
                "owner" : "username1"
            },
            {
                "displayPath" : "/",
                "displayname" : "repos2",
                "displayFullName" : "/repos1",
                "description" : "some description1",
                "access_types" : 'accessTypes',
                "owner" : "username1"
            },
        ],
        "page": page,
        "size": 10,
        "sort": "displayFilename,acs",
        "totalSize": 15
    };

    // imitate loading
    setTimeout(() => {
        dispatch(setRepositoriesAC(reps));
    }, 500)
}

export const searchRepositories = (searchParam, page = 1, size = 10) => async (dispatch) => {
    dispatch(setLoadingAC());

    let reps = {
        content: [
            {
                "displayPath" : "/",
                "displayname" : "repos1",
                "displayFullName" : "/repos1",
                "description" : "some description1",
                "access_types" : 'accessTypes',
                "owner" : "username1"
            },
            {
                "displayPath" : "/",
                "displayname" : "repos2",
                "displayFullName" : "/repos1",
                "description" : "some description1",
                "access_types" : 'accessTypes',
                "owner" : "username1"
            },
        ],
        "page": page,
        "size": 10,
        "sort": "displayFilename,acs",
        "totalSize": 15
    };

    // imitate loading
    setTimeout(() => {
        dispatch(setRepositoriesAC(reps));
    }, 500)
}

export const deleteRepository = (name) => async (dispatch) => {
    dispatch(setLoadingAC());

    // imitate loading
    setTimeout(() => {
        dispatch(deleteRepositoryAC(name));
    }, 500)
}
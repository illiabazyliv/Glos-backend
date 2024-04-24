import RepositoryService from '../../services/RepositoryService';
import { addNewAC, setLoadingAC, setCurrentRepositoryAC } from '../actionCreators/repositoryActionCreators';

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
        "displayFullName" : "/repos1",
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


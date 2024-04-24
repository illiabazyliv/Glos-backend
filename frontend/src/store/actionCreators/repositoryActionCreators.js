import { repositoryActionTypes } from '../actionTypes';

export const addNewAC = (repository) => {
    return {
        type: repositoryActionTypes.ADD_NEW,
        payload: {
            repository
        }
    }
}

export const setLoadingAC = () => {
    return {
        type: repositoryActionTypes.SET_LOADING,
    }
}
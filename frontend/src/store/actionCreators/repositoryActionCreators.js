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

export const setCurrentRepositoryAC = (repository) => {
    return {
        type: repositoryActionTypes.SET_CURRENT_REPOSITORY,
        payload: {
            repository
        }
    }
}

export const setTokenLoadingAC = () => {
    return {
        type: repositoryActionTypes.SET_TOKEN_LOADING,
    }
}

export const setTokenAC = (sharedToken) => {
    return {
        type: repositoryActionTypes.SET_TOKEN,
        payload: {
            sharedToken
        }
    }
}
import { fileActionTypes } from '../actionTypes';

export const addNewAC = (file) => {
    return {
        type: fileActionTypes.ADD_NEW,
        payload: {
            file
        }
    }
}

export const setLoadingAC = () => {
    return {
        type: fileActionTypes.SET_LOADING,
    }
}

export const setCurrentFileAC = (file) => {
    return {
        type: fileActionTypes.SET_CURRENT_FILE,
        payload: {
            file
        }
    }
}
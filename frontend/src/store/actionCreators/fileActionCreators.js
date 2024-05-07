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

export const setFilesAC = (files) => {
    return {
        type: fileActionTypes.SET_FILES,
        payload: {
            files
        }
    }
}

export const setErrorsAC = (errors) => {
    return {
        type: fileActionTypes.SET_ERRORS,
        payload: {
            errors
        }
    }
}

export const deleteFileAC = (filename) => {
    return {
        type: fileActionTypes.DELETE,
        payload: {
            filename
        }
    }
}
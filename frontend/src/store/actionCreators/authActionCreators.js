import { authActionTypes } from "../actionTypes"

export const setLoadingAC = () => {
    return {
        type: authActionTypes.SET_LOADING,
    }
}

export const setUserAC = (user, token) => {
    return {
        type: authActionTypes.SET_USER,
        payload: {
            user, token
        }
    }
}

export const setErrorAC = (errors) => {
    return {
        type: authActionTypes.SET_ERROR,
        payload: {
            errors
        }
    }
}


export const logoutAC = () => {
    return {
        type: authActionTypes.LOG_OUT,
    }
}

export const registerAC = (user, token) => {
    return {
        type: authActionTypes.REGISTER,
        payload: {
            user, token
        }
    }
}

export const updateUserAC = (user) => {
    return {
        type: authActionTypes.UPDATE_USER,
        payload: {
            user
        }
    }
}


export const setUpdateStatusAC = (isUpdateSuccessful, errors = []) => {
    return {
        type: authActionTypes.SET_UPDATE_STATUS,
        payload: {
            isUpdateSuccessful,
            errors
        }
    }
}

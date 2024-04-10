import { authActionTypes } from '../actionTypes';

let initState = {
    token: localStorage.getItem('token'),
    isLoading: false,
    isUpdateSuccessful: false,
    user: null,
    isAuth: false,
    errors: [],
}

const authReducer = (state = initState, action) => {
    switch (action.type) {
        case authActionTypes.SET_LOADING:
            return {
                ...state,
                isLoading: true,
            }
        case authActionTypes.SET_USER:
            return {
                ...state,
                isAuth: true,
                user: action.payload.user,
                token: action.payload.token,
                errors: [],
                isLoading: false,
            }
        case authActionTypes.SET_ERROR:
            return {
                ...state,
                errors: action.payload.errors,
                isLoading: false,
            }
        case authActionTypes.LOG_OUT:
            return {
                ...state,
                user: null,
                token: null,
                isAuth: false,
                errors: [],
                isLoading: false,
            }
        case authActionTypes.UPDATE_USER:
            return {
                ...state,
                user: action.payload.user,
                errors: [],
                isLoading: false,
            }
        case authActionTypes.SET_UPDATE_STATUS:
            return {
                ...state,
                isUpdateSuccessful: action.payload.isUpdateSuccessful,
                errors: action.payload.errors,
            }
        default: 
            return state;
    }
}

export default authReducer;
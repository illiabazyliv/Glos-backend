import { fileActionTypes } from '../actionTypes';

let initialState = {
    files: [],
    currentFile: null,
    newFile: null,
    isLoading: false,
    errors: [],
};

const fileReducer = (state = initialState, action) => {
    switch(action.type) {
        case fileActionTypes.SET_LOADING:
            return {
                ...state,
                isLoading: true,
            }
        case fileActionTypes.ADD_NEW:
            return {
                ...state,
                isLoading: false,
                newFile: action.payload.file,
            }
        case fileActionTypes.SET_CURRENT_FILE:
            return {
                ...state,
                isLoading: false,
                currentFile: action.payload.file,
            }
        default:
            return state; 
    }
}

export default fileReducer;
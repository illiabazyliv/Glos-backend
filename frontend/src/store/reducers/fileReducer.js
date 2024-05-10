import { fileActionTypes } from '../actionTypes';

let initialState = {
    files: {},
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
            console.log(action.payload.file)
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
        case fileActionTypes.SET_FILES:
            return {
                ...state,
                files: action.payload.files,
                isLoading: false,
            }
        case fileActionTypes.SET_ERRORS:
            return {
                ...state,
                errors: action.payload.errors,
                isLoading: false,
            }
        case fileActionTypes.DELETE:
            return {
                ...state,
                files: {...state.files, content: state.files?.content.filter(item => {
                    return item.displayFilename != action.payload.filename
                })},
                isLoading: false,
            }
        default:
            return state; 
    }
}

export default fileReducer;
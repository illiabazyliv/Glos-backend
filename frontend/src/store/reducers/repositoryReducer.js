import { repositoryActionTypes } from '../actionTypes';

let initialState = {
    repositories: [],
    currentRepository: null,
    newRepository: null,
    isLoading: false,
    errors: [],
};

const repositoryReducer = (state = initialState, action) => {
    switch(action.type) {
        case repositoryActionTypes.SET_LOADING:
            return {
                ...state,
                isLoading: true,
            }
        case repositoryActionTypes.ADD_NEW:
            return {
                ...state,
                isLoading: false,
                newRepository: action.payload.repository,
            }
        case repositoryActionTypes.SET_CURRENT_REPOSITORY:
            return {
                ...state,
                isLoading: false,
                currentRepository: action.payload.repository,
            }
        default:
            return state; 
    }
}

export default repositoryReducer;
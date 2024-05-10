import { repositoryActionTypes } from '../actionTypes';

let initialState = {
    repositories: {},
    currentRepository: null,
    newRepository: null,
    isLoading: false,
    errors: [],
    sharedToken: '',
    isSharedTokenLoading: false,
};

const repositoryReducer = (state = initialState, action) => {
    switch(action.type) {
        case repositoryActionTypes.SET_LOADING:
            return {
                ...state,
                isLoading: true,
            }
        case repositoryActionTypes.SET_TOKEN_LOADING:
            return {
                ...state,
                isSharedTokenLoading: true,
            }
        case repositoryActionTypes.SET_TOKEN:
            return {
                ...state,
                isSharedTokenLoading: false,
                sharedToken: action.payload.sharedToken,
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
        case repositoryActionTypes.SET_REPOSITORIES:
            return {
                ...state,
                isLoading: false,
                repositories: action.payload.repositories,
            }
        case repositoryActionTypes.SET_ERRORS:
            return {
                ...state,
                isLoading: false,
                errors: action.payload.errors,
            }
        case repositoryActionTypes.DELETE:
            return {
                ...state,
                repositories: {...state.repositories, content: state.repositories?.content.filter(item => {
                    return item.displayname != action.payload.name
                })},
                isLoading: false,
            }
        default:
            return state; 
    }
}

export default repositoryReducer;
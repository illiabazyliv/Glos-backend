import { groupActionTypes } from '../actionTypes';

let initialState = {
    repositories: [],
    currentGroup: null,
    newGroup: null,
    isLoading: false,
    errors: [],
};

const groupReducer = (state = initialState, action) => {
    switch(action.type) {
        case groupActionTypes.SET_LOADING:
            return {
                ...state,
                isLoading: true,
            }
        case groupActionTypes.ADD_NEW:
            return {
                ...state,
                isLoading: false,
                newGroup: action.payload.group,
            }
        case groupActionTypes.SET_CURRENT_GROUP:
            return {
                ...state,
                isLoading: false,
                currentGroup: action.payload.group,
            }
        default:
            return state; 
    }
}

export default groupReducer;
import { friendActionTypes } from '../actionTypes';

let initialState = {
    friends: [],
    currentFriend: null,
    newFriend: null,
    isLoading: false,
    errors: [],
};

const friendReducer = (state = initialState, action) => {
    switch(action.type) {
        case friendActionTypes.SET_LOADING:
            return {
                ...state,
                isLoading: true,
            }
        case friendActionTypes.ADD_NEW:
            return {
                ...state,
                isLoading: false,
                newFriend: action.payload.friend,
            }
        case friendActionTypes.SET_CURRENT_FRIEND:
            return {
                ...state,
                isLoading: false,
                currentFriend: action.payload.friend,
            }
        default:
            return state; 
    }
}

export default friendReducer;
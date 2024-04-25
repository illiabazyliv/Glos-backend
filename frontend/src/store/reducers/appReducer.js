import { appActionTypes } from '../actionTypes';

let initialState = {
    isInitialized: false
};

const appReducer = (state = initialState, action) => {
    switch(action.type) {
        case appActionTypes.SET_INITIALIZED:
            return {
                ...state,
                isInitialized: action.payload.isInitialized,
            }
        default:
            return state; 
    }
}

export default appReducer;
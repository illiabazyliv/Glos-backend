import { friendActionTypes } from '../actionTypes';

export const addNewAC = (friend) => {
    return {
        type: friendActionTypes.ADD_NEW,
        payload: {
            friend
        }
    }
}

export const setLoadingAC = () => {
    return {
        type: friendActionTypes.SET_LOADING,
    }
}

export const setCurrentFriendAC = (friend) => {
    return {
        type: friendActionTypes.SET_CURRENT_FRIEND,
        payload: {
            friend
        }
    }
}
import { groupActionTypes } from '../actionTypes';

export const addNewAC = (group) => {
    return {
        type: groupActionTypes.ADD_NEW,
        payload: {
            group
        }
    }
}

export const setLoadingAC = () => {
    return {
        type: groupActionTypes.SET_LOADING,
    }
}

export const setCurrentGroupAC = (group) => {
    return {
        type: groupActionTypes.SET_CURRENT_GROUP,
        payload: {
            group
        }
    }
}
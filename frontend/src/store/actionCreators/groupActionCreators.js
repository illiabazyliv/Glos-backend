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

export const deleteGroupAC = (groupName) => {
    return {
        type: groupActionTypes.DELETE,
        payload: {
            groupName
        }
    }
}

export const setGroupsAC = (groups) => {
    return {
        type: groupActionTypes.SET_GROUPS,
        payload: {
            groups
        }
    }
}

export const addUserAC = (user) => {
    return {
        type: groupActionTypes.ADD_USER,
        payload: {
            user
        }
    }
}

export const removeUserAC = (username) => {
    return {
        type: groupActionTypes.REMOVE_USER,
        payload: {
            username
        }
    }
}
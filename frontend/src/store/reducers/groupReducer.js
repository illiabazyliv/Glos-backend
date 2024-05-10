import { groupActionTypes } from '../actionTypes';

let initialState = {
    groups: [],
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
        case groupActionTypes.SET_GROUPS:
            return {
                ...state,
                groups: action.payload.groups,
                isLoading: false,
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
        case groupActionTypes.DELETE:
            return {
                ...state,
                groups: state.groups?.filter(item => {
                    return item.groupName != action.payload.groupName
                }),
                isLoading: false,
            }
        case groupActionTypes.ADD_USER:
            return {
                ...state,
                isLoading: false,
                currentGroup:  {...state.currentGroup, users: [action.payload.user, ...state.currentGroup.users]},
            }
        case groupActionTypes.REMOVE_USER:
            return {
                ...state,
                isLoading: false,
                currentGroup:  {...state.currentGroup, 
                    users: state.currentGroup.users.filter(user => user.username != action.payload.username)
                },
            }
        default:
            return state; 
    }
}

export default groupReducer;
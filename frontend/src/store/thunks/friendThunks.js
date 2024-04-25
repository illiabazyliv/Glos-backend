import FriendService from '../../services/FriendService';
import { addNewAC, setLoadingAC, setCurrentFriendAC } from '../actionCreators/friendActionCreators';

export const addNewFriend = (username, friendUsername) => async (dispatch) => {
    dispatch(setLoadingAC());

    // imitate loading
    setTimeout(() => {
        dispatch(addNewAC(friendUsername)); // probably will have to change to set all friends
    }, 500)
}

export const setCurrentFriend = (friendId) => async (dispatch) => {
    dispatch(setLoadingAC());

    let friendObj = {
        "displayPath" : "/",
        "displayname" : "repos1",
        "displayFullName" : "/repos1",
        "description" : "some description1",
        "access_types" : ["protected_rw", "public_r"],
        "owner" : "username1"
    };

    // imitate loading
    setTimeout(() => {
        dispatch(setCurrentFriendAC(friendObj));
    }, 500)
}

export const setFriendAccess = (username, friendId, accessTypes) => async (dispatch) => {
    dispatch(setLoadingAC());

    let friendObj = {
        "displayPath" : "/",
        "displayname" : "repos1",
        "displayFullName" : "/repos1",
        "description" : "some description1",
        "access_types" : accessTypes,
        "owner" : "username1"
    };

    // imitate loading
    setTimeout(() => {
        dispatch(setCurrentFriendAC(friendObj));
    }, 500)
}


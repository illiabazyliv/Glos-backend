import GroupService from '../../services/GroupService';
import { addNewAC, setLoadingAC, setCurrentGroupAC } from '../actionCreators/groupActionCreators';

export const addNewGroup = (username, groupName, group) => async (dispatch) => {
    dispatch(setLoadingAC());

    // imitate loading
    setTimeout(() => {
        dispatch(addNewAC(group));
    }, 500)
}

export const setCurrentGroup = (groupId) => async (dispatch) => {
    dispatch(setLoadingAC());

    let groupObj = {
        "groupName": "name1",
        "access_type" :  "protected_rw | null | empty",
        "users" : [
            {
                "username": "username1",
                "email": "email1@mail.com",
                "phoneNumber": "+380123456789",
                "firstName" : "john1",
                "lastName" : "doe1",
                "gender" : "male",
                "ISOBirthDate" : "1996.03.22"
            },
            {
                "username": "username2",
                "email": "email1@mail.com",
                "phoneNumber": "+380123456789",
                "firstName" : "john2",
                "lastName" : "doe2",
                "gender" : "male",
                "ISOBirthDate" : "1996.03.22"
            },
        ]
    };

    // imitate loading
    setTimeout(() => {
        dispatch(setCurrentGroupAC(groupObj));
    }, 500)
}

export const setGroupAccess = (username, groupId, accessTypes) => async (dispatch) => {
    dispatch(setLoadingAC());

    let groupObj = {
        "displayPath" : "/",
        "displayname" : "repos1",
        "displayFullName" : "/repos1",
        "description" : "some description1",
        "access_types" : accessTypes,
        "owner" : "username1"
    };

    // imitate loading
    setTimeout(() => {
        dispatch(setCurrentGroupAC(groupObj));
    }, 500)
}


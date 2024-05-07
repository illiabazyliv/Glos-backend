import GroupService from '../../services/GroupService';
import { addNewAC, setLoadingAC, setCurrentGroupAC, deleteGroupAC, setGroupsAC, addUserAC, removeUserAC } from '../actionCreators/groupActionCreators';

export const addNewGroup = (username, groupName, group) => async (dispatch, getState) => {
    dispatch(setLoadingAC());

    // imitate loading
    setTimeout(() => {
        dispatch(addNewAC(group));
    }, 500)
}

export const setCurrentGroup = (groupId) => async (dispatch, getState) => {
    dispatch(setLoadingAC());

    let groupObj = {
        "groupName": "name1",
        "access_type" :  "protected_rw",
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

export const setGroupAccess = (groupName, accessType) => async (dispatch) => {
    dispatch(setLoadingAC());

    let groupObj = {
        "groupName": groupName,
        "access_type" : accessType,
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

export const deleteGroup = (groupName) => async (dispatch) => {
    dispatch(setLoadingAC());

    // imitate loading
    setTimeout(() => {
        dispatch(deleteGroupAC(groupName));
    }, 500)
}


export const loadGroups = () => async (dispatch) => {
    dispatch(setLoadingAC());

    const groups = [
        {
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
        },
        {
            "groupName": "name2",
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
        }
    ];

    // imitate loading
    setTimeout(() => {
        dispatch(setGroupsAC(groups));
    }, 500)
}

export const addUserToGroup = (groupName, username) => async (dispatch, getState) => {
    dispatch(setLoadingAC());

    // imitate loading
    setTimeout(() => {
        // get user object by username and sed request

        let user = {
            "username": "username3",
            "email": "email1@mail.com",
            "phoneNumber": "+380123456789",
            "firstName" : "john1",
            "lastName" : "doe1",
            "gender" : "male",
            "ISOBirthDate" : "1996.03.22"
        };

        dispatch(addUserAC(user));
    }, 500)
}

export const removeUserFromGroup = (groupName, username) => async (dispatch, getState) => {
    dispatch(setLoadingAC());

    // imitate loading
    setTimeout(() => {
        // get user object by username and sed request

        let user = {
            "username": "username3",
            "email": "email1@mail.com",
            "phoneNumber": "+380123456789",
            "firstName" : "john1",
            "lastName" : "doe1",
            "gender" : "male",
            "ISOBirthDate" : "1996.03.22"
        };

        dispatch(removeUserAC(username));
    }, 500)
}
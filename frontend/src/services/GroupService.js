import { httpInstance } from "../http/httpInstance";
import { handleHttpError } from "../helpers/helpers";

const GroupService = {
    async getUserGroups(username) { // GET /users/{username}/groups
        return await httpInstance.get(`/users/${username}/groups`)
        .then(function (response) {
            // return response.data;

            return [
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
        })
        .catch(error => handleHttpError(error))
    },

    async getGroup(username, groupName) { // GET /users/{username}/groups/{groupName}
        return await httpInstance.get(`/users/${username}/groups/${groupName}`)
        .then(function (response) {
            // return response.data;

            return {
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
        })
        .catch(error => handleHttpError(error))
    },

    // todo: ask how can group be created if there's no group name yet?
    // todo: in api map but not in dto map, does it still exist?, same for delete operation
    async createGroup(username, groupName, group) { // PUT /users/{username}/group/{groupName}
        return await httpInstance.put(`/users/${username}/groups/${groupName}`, group)
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    // todo: ask about groupName and users
    // todo: ask what happend if access field is null
    async updateGroup(username, groupName, group) { // PATCH /users/{username}/groups/{groupName}
        return await httpInstance.patch(`/users/${username}/groups/${groupName}`, group)
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

    async deleteGroup(username, groupName) { // DELETE /users/{username}/groups/{groupName}
        return await httpInstance.delete(`/users/${username}/groups/${groupName}`)
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

    async addUsersToGroup(username, groupName, users) { // PATCH /users/{username}/groups/{groupName}/append-users
        return await httpInstance.put(`/users/${username}/groups/${groupName}/append-users`, users)
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

    async removeUsersFromGroup(username, groupName, users) { // DELETE /users/{username}/groups/{groupName}/delete-users
        return await httpInstance.delete(`/users/${username}/groups/${groupName}/delete-users`, users)
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },
};

export default GroupService;
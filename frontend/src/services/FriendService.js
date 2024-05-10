import { httpInstance } from "../http/httpInstance";
import { handleHttpError } from "../helpers/helpers";

const FriendService = {
    async getUserFriends(username) { // GET /users/{username}/friends
        return await httpInstance.get(`/users/${username}/friends`)
        .then(function (response) {
            // return response.data;

            return {
                "groupName": "friends",
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

    async addFriend(username, friendUsername) { // PUT /users/{username}/friends/{username}
        return await httpInstance.put(`/users/${username}/friends/${friendUsername}`)
        .then(function (response) {
            // return response.data;

            return {
                "groupName": "friends",
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

    async deleteFriend(username, friendUsername) { // DELETE /users/{username}/friends/{username}
        return await httpInstance.delete(`/users/${username}/friends/${friendUsername}`)
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },
};

export default FriendService;
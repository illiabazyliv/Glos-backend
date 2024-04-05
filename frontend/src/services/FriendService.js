import { httpInstance } from "../http/httpInstance";
import { handleHttpError } from "../helpers/helpers";

const FriendService = {
    async getUserFriends(username) { // GET /users/{username}/friends
        return await httpInstance.get(`/users/${username}/friends`)
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    async addFriend(username, friendUsername) { // PUT /users/{username}/friends/{username}
        return await httpInstance.put(`/users/${username}/friends/${friendUsername}`)
        .then(function (response) {
            return response.data;
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
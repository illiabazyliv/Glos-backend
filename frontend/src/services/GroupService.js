import { httpInstance } from "../http/httpInstance";
import { handleHttpError } from "../helpers/helpers";

const GroupService = {
    async getUserGroups(username) { // GET /users/{username}/groups
        return await httpInstance.get(`/users/${username}/groups`)
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    async getGroup(username, groupName) { // GET /users/{username}/groups/{groupName}
        return await httpInstance.get(`/users/${username}/groups/${groupName}`)
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    // todo: ask how can group be created if there's no group name yet?
    async createGroup(username, groupName, group) { // PUT /users/{username}/group/{groupName}
        return await httpInstance.put(`/users/${username}/groups/${groupName}`, group)
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

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
};

export default GroupService;
import { httpInstance } from "../http/httpInstance";
import { handleHttpError } from "../helpers/helpers";

const UserService = {
    async getUser(username) { // GET /users/{username}
        return await httpInstance.get(`/users/${username}`)
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    async getUserImage(username) { // GET /users/{username}/image
        return await httpInstance.get(`/users/${username}/image`)
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    async setUserImage(username, image) { // PUT /users/{username}/image
        const formData = new FormData();
        formData.append('image', image);

        return await httpInstance.put(`/users/${username}/image`, formData, 
        {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    async updateUser(username, user) { // PUT /users/{username}
        return await httpInstance.put(`/users/${username}`, user)
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

    async blockUser(username) { // POST /users/{username}/block
        return await httpInstance.post(`/users/${username}/block`)
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

    async enableUser(username) { // POST /users/{username}/enable
        return await httpInstance.post(`/users/${username}/enable`)
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

    async searchUsers(searchParam, page, size, orderBy, order) { // GET /users?login=<username/email/phoneNumber>&page=0&size=10&size="username,asc"
        return await httpInstance.get('/users', {
            params: {
                login: searchParam,
                page,
                size,
                sort: `${orderBy.toLowerCase()},${order.toLowerCase()}`,
            }
        })
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error)) 
    },
};

export default UserService;
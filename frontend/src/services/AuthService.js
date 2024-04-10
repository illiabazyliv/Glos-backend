import { httpInstance } from "../http/httpInstance";
import { handleHttpError } from "../helpers/helpers";

const AuthService = {
    async register(user) { // POST /auth/register
        return await httpInstance.post('/auth/register', user)
        .then(function (response) {
            // return response.data;

            return {
                "token": "ufhskdfhksndkf",
                "refreshToken": "238466u823"
            };
        })
        .catch(error => handleHttpError(error))
    },

    async registerAdmin(user) { // POST /auth/register/admin
        return await httpInstance.post('/auth/register/admin', user)
        .then(function (response) {
            // return response.data;

            return {
                "token": "ufhskdfhksndkf",
                "refreshToken": "238466u823"
            };
        })
        .catch(error => handleHttpError(error))
    },

    async login(login, password) { // POST /auth/login
        return await httpInstance.post('/auth/login', {login, password})
        .then(function (response) {
            // return response.data;

            return {
                "token": "ufhskdfhksndkf",
                "refreshToken": "238466u823"
            }
        })
        .catch(error => handleHttpError(error))
    },

    async logout() { // GET /auth/logout
        return await httpInstance.get('/auth/logout')
        .then(function (response) {
            // return response.data;

            return {
                "expiredToken" : "sjahiasmdid" 
            };
        })
        .catch(error => handleHttpError(error))
    },

    async refreshToken(refreshToken) { // GET /auth/refresh - i suppose it should be post
        return await httpInstance.post('/auth/refresh', {refreshToken})
        .then(function (response) {
            // return response.data;

            return {
                "token": "ufhskdfhksndkf",
                "refreshToken": "238466u823"
            }
        })
        .catch(error => handleHttpError(error))
    },

    async executeOperation(username, code) { // POST /auth/users/{username}/execute-operation
        return await httpInstance.post(`/auth/users/${username}/execute-operation`, {code})
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

    async changePassword(username, oldPassword, newPassword) { // PUT /auth/users/{username}/change-password
        return await httpInstance.put(`/auth/users/${username}/change-password`, {oldPassword, newPassword})
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

    async changePhoneNumber(username, oldPhoneNumber, newPhoneNumber) { // PUT /auth/users/{username}/change-phone-number
        return await httpInstance.put(`/auth/users/${username}/change-phone-number`, {oldPhoneNumber, newPhoneNumber})
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

    async changeUsername(username, oldUsername, newUsername) { // PUT /auth/users/{username}/change-username
        return await httpInstance.put(`/auth/users/${username}/change-username`, {old: oldUsername, new: newUsername})
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

    async changeEmail(username, oldEmail, newEmail) { // PUT /auth/users/{username}/change-email
        return await httpInstance.put(`/auth/users/${username}/change-email`, {old: oldEmail, new: newEmail})
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

    async deleteAccount(username) { // DELETE /auth/users/{username}/drop-account
        return await httpInstance.delete(`/auth/users/${username}/drop-account`)
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },
};

export default AuthService;
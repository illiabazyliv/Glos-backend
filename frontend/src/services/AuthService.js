import { httpInstance } from "../http/httpInstance";
import { handleHttpError } from "../helpers/helpers";

const AuthService = {
    register(user) {
        return httpInstance.post('/auth/register', user)
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    registerAdmin(user) {
        return httpInstance.post('/auth/register/admin', user)
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    login(login, password) {
        return httpInstance.post('/auth/login', {login, password})
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    logout() {
        return httpInstance.get('/auth/logout')
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    refreshToken(refreshToken) { // GET /auth/refresh - i suppose it should be post
        return httpInstance.post('/auth/refresh', {refreshToken})
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    executeOperation(username, code) {
        return httpInstance.post(`/auth/users/${username}/execute-operation`, {code})
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

    changePassword(oldPassword, newPassword) {
        return httpInstance.put(`/auth/users/${username}/change-password`, {oldPassword, newPassword})
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

    changePhoneNumber(oldNumber, newNumber) {
        return httpInstance.put(`/auth/users/${username}/change-phone-number`, {old: oldNumber, new: newNumber})
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

    changeUsername(oldUsername, newUsername) {
        return httpInstance.put(`/auth/users/${username}/change-username`, {oldUsername, newUsername})
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

    changeEmail(oldEmail, newEmail) {
        return httpInstance.put(`/auth/users/${username}/change-email`, {oldEmail, newEmail})
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

    dropAccount() {
        return httpInstance.delete(`/auth/users/${username}/drop-account`)
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },
};

export default AuthService;
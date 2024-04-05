import { httpInstance } from "../http/httpInstance";
import { handleHttpError } from "../helpers/helpers";

const RepositoryService = {
    async getRepository(id, sharedToken) { // GET /repositories/{id}?sharedtoken=<Shared token>
        return await httpInstance.get(`/repositories/${id}`, {
            params: { sharedToken }
        })
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    async getRepositorySharedToken(id,) { // GET /repositories/{id}/shared-token
        return await httpInstance.get(`/repositories/${id}/shared-token`)
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    async setRepositoryAccess(username, id, accessTypes) { // PUT /users/{username}/repositories/{id}/access
        return await httpInstance.put(`/users/${username}/repositories/${id}/access`, {accessTypes})
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    async setRepositoryAccessForUser(username, id, targetUsername, accessType) { // PUT /users/{username}/repositories/{id}/access/{username}
        return await httpInstance.put(`/users/${username}/repositories/${id}/access/${targetUsername}`, {accessType})
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    async searchRepositories(search, username, onlyRoots, page, size, orderBy, order) { // GET /repositories?search=rep&username=usr1&page=0&onlyroots=false&size=10&sort=name,asc
        return await httpInstance.get('/repositories', {
            params: {
                search,
                username,
                onlyroots: onlyRoots,
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

    async searchUserRepositories(username, search, page, size, orderBy, order) { // GET /users/{username}/repositories?search=rep&page=0&size=10&sort=name,asc
        // ?search=rep&page=0&size=10&sort=name,asc
        return await httpInstance.get(`/users/${username}/repositories`, {
            params: {
                search,
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

export default RepositoryService;
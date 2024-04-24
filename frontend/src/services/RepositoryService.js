import { httpInstance } from "../http/httpInstance";
import { handleHttpError } from "../helpers/helpers";

const RepositoryService = {
    async getRepository(id, sharedToken) { // GET /repositories/{id}?sharedtoken=<Shared token>
        return await httpInstance.get(`/repositories/${id}`, {
            params: { sharedToken }
        })
        .then(function (response) {
            // todo: ask how it is going to work
            // return response.data;

            return {
                "displayPath" : "/",
                "displayname" : "repos1",
                "displayFullName" : "/repos1",
                "description" : "some description1",
                "access_types" : ["protected_rw", "public_r"],
                "owner" : "username1"
            };
        })
        .catch(error => handleHttpError(error))
    },

    async getRepositorySharedToken(id,) { // GET /repositories/{id}/shared-token
        return await httpInstance.get(`/repositories/${id}/shared-token`)
        .then(function (response) {
            return response.data; // todo: ask for response format
        })
        .catch(error => handleHttpError(error))
    },

    async setRepositoryAccess(username, repository) { // POST /users/{username}/repositories
        return await httpInstance.post(`/users/${username}/repositories`, repository)
        .then(function (response) {
            // return response.data;

            return {
                "displayPath" : "/",
                "displayname" : "repos1",
                "displayFullName" : "/repos1",
                "description" : "some description1",
                "access_types" : ["protected_rw", "public_r"],
                "owner" : "username1"
            };
        })
        .catch(error => handleHttpError(error))
    },

    async setRepositoryAccess(username, id, accessTypes) { // PUT /users/{username}/repositories/{id}/access
        return await httpInstance.put(`/users/${username}/repositories/${id}/access`, {accessTypes})
        .then(function (response) {
            // return response.data;

            return {
                "displayPath" : "/",
                "displayname" : "repos1",
                "displayFullName" : "/repos1",
                "description" : "some description1",
                "access_types" : ["protected_rw", "public_r"],
                "owner" : "username1"
            };
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

    // GET /repositories?search=rep&username=usr1&tags=tag1,tag2,tag3&acs-tps=protected_rw,public_r&dateTime=2024-09-25T18-45Z&page=0&onlyroots=false&size=10&sort=name,asc
    async searchRepositories(search, username, tags, accessTypes, dateTime, onlyRoots, page, size, orderBy, order) { // GET /repositories?search=rep&username=usr1&page=0&onlyroots=false&size=10&sort=name,asc
        return await httpInstance.get('/repositories', {
            params: {
                search,
                username,
                tags,
                "acs-tps": accessTypes,
                dateTime, // todo: verify if exists
                onlyroots: onlyRoots,
                page,
                size,
                sort: `${orderBy.toLowerCase()},${order.toLowerCase()}`,
            }
        })
        .then(function (response) {
            // return response.data;

            return {   
                content : [
                    {
                        "displayPath" : "/",
                        "displayname" : "repos1",
                        "displayFullName" : "/repos1",
                        "description" : "some description1",
                        "access_types" : ["protected_rw", "public_r"],
                        "owner" : "username1"
                    },
                    {
                        "displayPath" : "/",
                        "displayname" : "repos2",
                        "displayFullName" : "/repos2",
                        "description" : "some description2",
                        "access_types" : ["protected_rw", "public_r"],
                        "owner" : "username1"
                    }
                ],
                "page": 1,
                "size": 10,
                "sort": "username,acs",
                "totalSize": 15
            };
        })
        .catch(error => handleHttpError(error)) 
    },

    // GET /users/{username}/repositories?search=rep&username=usr1&tags=tag1,tag2,tag3&dateTime=2024-09-25T18-45Z&page=0&onlyroots=false&size=10&sort=name,asc
    async searchUserRepositories(username, search, tags, dateTime, onlyroots, page, size, orderBy, order) { // GET /users/{username}/repositories?search=rep&page=0&size=10&sort=name,asc
        // ?search=rep&page=0&size=10&sort=name,asc
        return await httpInstance.get(`/users/${username}/repositories`, {
            params: {
                username,
                search,
                tags,
                dateTime,
                onlyroots,
                page,
                size,
                sort: `${orderBy.toLowerCase()},${order.toLowerCase()}`,
            }
        })
        .then(function (response) {
            // return response.data;

            return {   
                content : [
                    {
                        "displayPath" : "/",
                        "displayname" : "repos1",
                        "displayFullName" : "/repos1",
                        "description" : "some description1",
                        "owner" : "username1",
                        "access_types" : ["protected_rw", "public_r"]
                    },
                    {
                        "displayPath" : "/",
                        "displayname" : "repos2",
                        "displayFullName" : "/repos2",
                        "description" : "some description2",
                        "owner" : "username1",
                        "access_types" : ["protected_rw", "public_r"]
                    }
                ],
                "page": 1,
                "size": 10,
                "sort": "username,acs",
                "totalSize": 15
            };
        })
        .catch(error => handleHttpError(error)) 
    },
};

export default RepositoryService;
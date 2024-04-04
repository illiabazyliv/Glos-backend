import { httpInstance } from "../http/httpInstance";
import { handleHttpError } from "../helpers/helpers";

const TagService = {
    async getTags() { // GET /tags
        return await httpInstance.get(`/tags`)
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    // todo: ask if this is an update or a create route
    async updateTag(name, tag) { // PUT /tags/{name}
        return await httpInstance.put(`/tags/${name}`, tag)
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

    async deleteTag(name) { // DELETE /tags/{name}
        return await httpInstance.delete(`/tags/${name}`)
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

    // todo: ???
    async addRepositoryTag(username, id, name, tag) { // PUT /users/{username}/repositories/{id}/tags/{name}
        return await httpInstance.put(`/users/${username}/repositories/${id}/tags/${name}`, tag)
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

    async deleteRepositoryTag(username, id, name) { // DELETE /users/{username}/repositories/{id}/tags/{name}
        return await httpInstance.delete(`/users/${username}/repositories/${id}/tags/${name}`)
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },
    
    // todo: ???
    async addFileTag(username, id, filename, name, tag) { // PUT /users/{username}/repositories/{id}/files/{filename}/tags/{name}
        return await httpInstance.put(`/users/${username}/repositories/${id}/files/${filename}/tags/${name}`, tag)
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

    async deleteFileTag(username, id, filename, name) { // DELETE /users/{username}/repositories/{id}/files/{filename}/tags/{name}
        return await httpInstance.delete(`/users/${username}/repositories/${id}/files/${filename}/tags/${name}`)
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },
};

export default TagService;
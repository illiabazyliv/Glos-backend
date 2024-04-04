import { httpInstance } from "../http/httpInstance";
import { handleHttpError } from "../helpers/helpers";

const CommentService = {
    async getRepositoryComments(username, id) { // GET /users/{username}/repositories/{id}/comments
        return await httpInstance.get(`/users/${username}/repositories/${id}/comments`)
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    async addRepositoryComment(username, id, comment) { // POST /users/{username}/repositories/{id}/comments
        return await httpInstance.post(`/users/${username}/repositories/${id}/comments`, comment)
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    async getFileComments(username, filename) { // GET /users/{username}/files/{filename}/comments
        return await httpInstance.get(`/users/${username}/files/${filename}/comments`)
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    async addFileComment(username, filename, comment) { // POST /users/{username}/files/{filename}/comments
        return await httpInstance.post(`/users/${username}/files/${filename}/comments`, comment)
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    async updateComment(id, comment) { // PUT /comments/{id}
        return await httpInstance.put(`/comments/${id}`, comment)
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

    async deleteComment(id) { // DELETE /comments/{id}
        return await httpInstance.delete(`/comments/${id}`)
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },
};

export default CommentService;
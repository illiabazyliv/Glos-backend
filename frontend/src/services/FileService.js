import { httpInstance } from "../http/httpInstance";
import { handleHttpError } from "../helpers/helpers";

const FileService = {
    async setFileAccessForUser(username, repositoryId, filename, targetUsername, accessType) { // POST /users/{username}/repositories/{id}/files/{filename}/access/{username}
        return await httpInstance.put(`/users/${username}/repositories/${repositoryId}/files/${filename}/access/${targetUsername}`, {accessType})
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    async getFile(username, filename) { // GET /users/{username}/files/{filename}/download
        return await httpInstance.get(`/users/${username}/files/${filename}/download`)
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    async downloadFile() {
        // will get info from getFile() and download file through SFTP
    },

    async getFileInfo(username, filename) { // GET /users/{username}/files/{filename}
        return await httpInstance.get(`/users/${username}/files/${filename}`)
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    async getFileSharedToken(username, filename) { // GET /users/{username}/files/{filename}/shared-token
        return await httpInstance.get(`/users/${username}/files/${filename}/shared-token`)
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    async uploadFile() { // PUT /users/{username}/files/{filename}/upload
        // waiting for final request body example
        // todo: also ask how can a file be uploaded if no filename exists yet
    },

    async moveFile(username, filename, from, to) { // POST /users/{username}/files/{filename}/files/move
        return await httpInstance.post(`/users/${username}/files/${filename}/files/`, {from, to})
        .then(function (response) {
            return response.data;
        })
        .catch(error => handleHttpError(error))
    },

    async deleteFile(username, id, filename) { // DELETE /users/{username}/files/{id}/file/{filename}
        return await httpInstance.delete(`/users/${username}/files/${id}/file/${filename}`)
        .then(function (response) {
            return response;
        })
        .catch(error => handleHttpError(error))
    },

};

export default FileService;
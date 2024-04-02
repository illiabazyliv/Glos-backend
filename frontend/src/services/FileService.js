const FileService = {
    setFileAccessForUser() { // POST /users/{username}/repositories/{id}/files/{filename}/access/{username}
    
    },

    getFile() { // GET /users/{username}/files/{filename}/download

    },

    downloadFile() {
        // will get info from getFile() and download file through SFTP
    },

    getFileInfo() { // GET /users/{username}/files/{filename}

    },

    getFileSharedToken() { // GET /users/{username}/files/{filename}/shared-token

    },

    uploadFile() { // PUT /users/{username}/files/{filename}/upload

    },

    moveFile() { // POST /users/{username}/files/{filename}/files/move

    },

    deleteFile() { // DELETE /users/{username}/files/{id}/file/{filename}

    },

};

export default FileService;
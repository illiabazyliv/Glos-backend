const RepositoryService = {
    getRepository() { // GET /repositories/{id}?sharedtoken=<Shared token>

    },

    getRepositorySharedToken() { // GET /repositories/{id}?sharedtoken=<Shared token>

    },

    setRepositoryAccess() { // PUT /users/{username}/repositories/{id}/access

    },

    setRepositoryAccessForUser() { // PUT /users/{username}/repositories/{id}/access/{username}

    },

    addRepositoryTag() { // PUT /users/{username}/repositories/{id}/tags/{name}

    },

    deleteRepositoryTag() { // DELETE /users/{username}/repositories/{id}/tags/{name}

    },

    searchRepositories() { // GET /repositories?search=rep&username=usr1&page=0&onlyroots=false&size=10&sort=name,asc

    },

    searchUserRepositories() { // GET /users/{username}/repositories?search=rep&page=0&size=10&sort=name,asc

    },
};

export default RepositoryService;
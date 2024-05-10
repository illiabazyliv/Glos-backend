import { httpInstance } from "../http/httpInstance";
import { handleHttpError } from "../helpers/helpers";

const UserService = {
    async getUser(username) { // GET /users/{username}
        return await httpInstance.get(`/users/${username}`)
        .then(function (response) {
            // return response.data;

            return {
                "username": "username1",
                "email": "email1@mail.com",
                "phoneNumber": "+380123456789",
                "firstName" : "john1",
                "lastName" : "doe1",
                "gender" : "male",
                "BirthDate" : "1996.03.22" 
            };
        })
        .catch(error => handleHttpError(error))
    },

    async getUserImage(username) { // GET /users/{username}/image
        return await httpInstance.get(`/users/${username}/image`)
        .then(function (response) {
            // return response.data;

            return {
                "tempUrl" : "https://i0.wp.com/sunrisedaycamp.org/wp-content/uploads/2020/10/placeholder.png?ssl=1"
            };
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
            // return response.data;

            return {
                "imgUrl" : "https://i0.wp.com/sunrisedaycamp.org/wp-content/uploads/2020/10/placeholder.png?ssl=1"
            };
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
                login: searchParam, //todo: ask about this param in DTO
                page,
                size,
                sort: `${orderBy.toLowerCase()},${order.toLowerCase()}`,
            }
        })
        .then(function (response) {
            // return response.data;

            return {
				"content": [
					{
						"username": "username1",
                        "email": "email1@mail.com",
                        "phoneNumber": "+380123456789",
                        "firstName" : "john1",
                        "lastName" : "doe1",
                        "gender" : "male",
                        "ISOBirthDate" : "1996.03.22"
					},
					{
						"username": "username2",
                        "email": "email2@mail.com",
                        "phoneNumber": "+380123456789",
                        "firstName" : "john2",
                        "lastName" : "doe2",
                        "gender" : "female",
                        "ISOBirthDate" : "1996.03.23"
					},
				],
				"page": 1,
				"size": 10,
				"sort": "username,acs",
				"totalSize": 5
		    };
        })
        .catch(error => handleHttpError(error)) 
    },
};

export default UserService;
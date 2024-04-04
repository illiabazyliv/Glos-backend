import axios from "axios";

const BASE_URL = 'https://glos.com/';

export const httpInstance = axios.create({
    baseURL : BASE_URL,
    headers: {
        'Content-Type': "application/json",
    }
});

// export const httpInstanceWithAuth = axios.create({
//     baseURL : BASE_URL,
//     headers: {
//         'Authorization': `Bearer <Your Auth Token>`,
//         'Content-Type': "application/json",
//     },
//     withCredentials: true,
// });
import axios from "axios";

// const BASE_URL = 'https://glos.com/';
const BASE_URL = 'https://005c5676-aad8-45c1-bd7c-8ac013d8aa81.mock.pstmn.io'; // endpoint that will always return 2000

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
import AuthService from '../../services/AuthService';
import { setLoadingAC, updateUserAC } from '../actionCreators/authActionCreators';


export const login = (login, password) => async (dispatch) => {
    
}

export const updateUser = (user) => async (dispatch) => {
    dispatch(setLoadingAC());

    // imitate loading
    setTimeout(() => {
        // send reqeuest to the api
        const newUser = {
            "username": "newUserName",
            "email": "email1@mail.com",
            "phoneNumber": "+380123456789",
            "firstName" : "john1",
            "lastName" : "doe1",
            "gender" : "male",
            "BirthDate" : "1996.03.22" 
        };

        dispatch(updateUserAC(newUser));
    }, 500)

    
}
import AuthService from '../../services/AuthService';
import { setInitializedAC } from '../actionCreators/appActionCreators';
import { setUserAC } from '../actionCreators/authActionCreators';

export const initialize = () => async (dispatch) => {
    // set user and token here
    // todo: ask for an endpoint to get current currend logged in user by auth header

    dispatch(setUserAC({
        "username": "username1",
        "email": "email1@mail.com",
        "phoneNumber": "+380123456789",
        "firstName" : "john1",
        "lastName" : "doe1",
        "gender" : "male",
        "BirthDate" : "1996.03.22" 
    }));

    dispatch(setInitializedAC(true));
}
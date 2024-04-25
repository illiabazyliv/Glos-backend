import AuthService from '../../services/AuthService';
import UserService from '../../services/UserService';
import { setInitializedAC } from '../actionCreators/appActionCreators';
import { setUserAC } from '../actionCreators/authActionCreators';

export const initialize = () => async (dispatch) => {
    // set user, user image and token here
    // todo: ask for an endpoint to get current currend logged in user by auth header

    const token = '2345678';
    const user = {
        "username": "username1",
        "email": "email1@mail.com",
        "phoneNumber": "+380123456789",
        "firstName" : "john1",
        "lastName" : "doe1",
        "gender" : "male",
        "BirthDate" : "1996.03.22" 
    };
    const userImage = await UserService.getUserImage(user.username);
    
    dispatch(setUserAC(user, userImage.tempUrl, token));
    dispatch(setInitializedAC(true));
}
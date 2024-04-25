import { appActionTypes } from '../actionTypes';

export const setInitializedAC = (isInitialized) => {
    return {
        type: appActionTypes.SET_INITIALIZED,
        payload: {
            isInitialized
        }
    }
}

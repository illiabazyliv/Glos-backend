import RepositoryService from '../../services/RepositoryService';
import { addNewAC, setLoadingAC } from '../actionCreators/repositoryActionCreators';

export const addNewRepository = (repository) => async (dispatch) => {
    dispatch(setLoadingAC());

    // imitate loading
    setTimeout(() => {
        dispatch(addNewAC(repository));
    }, 500)
}
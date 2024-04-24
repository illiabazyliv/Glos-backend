import FileService from '../../services/FileService';
import { addNewAC, setLoadingAC, setCurrentFileAC } from '../actionCreators/fileActionCreators';

export const addNewFile = (username, filename, file) => async (dispatch) => {
    dispatch(setLoadingAC());

    // imitate loading
    setTimeout(() => {
        dispatch(addNewAC(filename));
    }, 500)
}

export const setCurrentFile = (fileId) => async (dispatch) => {
    dispatch(setLoadingAC());

    let fileObj = {
        "displayPath" : "/dir1/dir2",
        "displayFilename" : "file.txt",
        "displayFullName" : "/dir1/dir2/file.txt",
        "tags": ["tag1", "tag2"],
        "accessTypes" : ["protected_r"],
    };

    // imitate loading
    setTimeout(() => {
        dispatch(setCurrentFileAC(fileObj));
    }, 500)
}

export const setFileAccess = (username, fileId, accessTypes) => async (dispatch) => {
    dispatch(setLoadingAC());

    let fileObj = {
        "displayPath" : "/dir1/dir2",
        "displayFilename" : "file.txt",
        "displayFullName" : "/dir1/dir2/file.txt",
        "tags": ["tag1", "tag2"],
        "accessTypes" : accessTypes,
    };

    // imitate loading
    setTimeout(() => {
        dispatch(setCurrentFileAC(fileObj));
    }, 500)
}

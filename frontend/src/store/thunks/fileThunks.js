import FileService from '../../services/FileService';
import { addNewAC, setLoadingAC, setCurrentFileAC, setFilesAC, deleteFileAC } from '../actionCreators/fileActionCreators';

export const addNewFile = (username, filename, file) => async (dispatch) => {
    dispatch(setLoadingAC());

    // todo: edit filename

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

export const loadLatestFiles = (page = 1, size = 10) => async (dispatch) => {
    dispatch(setLoadingAC());

    let files = {
        content: [
            {
                "displayPath": "/dir1/dir2",
                "displayFilename": "file2.txt",
                "displayFullName": "/dir1/dir2/file.txt",
                "tags": ["tag1", "tag2"]
            },
            {
                "displayPath": "/dir1/dir2",
                "displayFilename": "file.txt",
                "displayFullName": "/dir1/dir2/file.txt",
                "tags": ["tag1", "tag2"]
            },
        ],
        "page": page,
        "size": 10,
        "sort": "displayFilename,acs",
        "totalSize": 15
    };

    // imitate loading
    setTimeout(() => {
        dispatch(setFilesAC(files));
    }, 500)
}

export const searchFiles = (searchParam, page = 1, size = 10) => async (dispatch) => {
    dispatch(setLoadingAC());

    let files = {
        content: [
            {
                "displayPath": "/dir1/dir2",
                "displayFilename": "file2.txt",
                "displayFullName": "/dir1/dir2/file.txt",
                "tags": ["tag1", "tag2"]
            },
            {
                "displayPath": "/dir1/dir2",
                "displayFilename": "file.txt",
                "displayFullName": "/dir1/dir2/file.txt",
                "tags": ["tag1", "tag2"]
            },
        ],
        "page": page,
        "size": 10,
        "sort": "displayFilename,acs",
        "totalSize": 15
    };

    // imitate loading
    setTimeout(() => {
        dispatch(setFilesAC(files));
    }, 500)
}

export const deleteFile = (filename) => async (dispatch) => {
    dispatch(setLoadingAC());

    // imitate loading
    setTimeout(() => {
        dispatch(deleteFileAC(filename));
    }, 500)
}
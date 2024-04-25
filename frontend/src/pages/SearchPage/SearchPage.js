import { connect } from 'react-redux';
import { updateUser } from '../../store/thunks/authThunks';
import Loader from '../../components/Loader/Loader';
import FileList from '../../components/FileList/FileList';
import SortDropdown from '../../components/SortDropdown/SortDropdown';
import { useState } from 'react';
import RepositoryList from '../../components/RepositoryList/RepositoryList';
import { useSearchParams } from 'react-router-dom';

function SearchPage({ isLoading, user, updateUser }) {
    let [searchParams, setSearchParams] = useSearchParams();

    const sortByOptions = [
        { name: 'By name A to Z', value: 'displayFilename,asc' },
        { name: 'By name Z to A', value: 'displayFilename,desc' },
    ];

    const files = {
        content: [
            {
                "displayPath": "/dir1/dir2",
                "displayFilename": "file.txt",
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
        "page": 1,
        "size": 10,
        "sort": "displayFilename,acs",
        "totalSize": 15
    }

    const repositories = {   
        content : [
            {
                "displayPath" : "/",
                "displayname" : "re pos1",
                "displayFullName" : "/repos1",
                "description" : "some description1",
                "owner" : "username1",
                "access_types" : ["protected_rw", "public_r"]
            },
            {
                "displayPath" : "/",
                "displayname" : "repos2",
                "displayFullName" : "/repos2",
                "description" : "some description2",
                "owner" : "username1",
                "access_types" : ["protected_rw", "public_r"]
            }
        ],
        "page": 1,
        "size": 10,
        "sort": "username,acs",
        "totalSize": 15
    }

    const [selectedOption, setSelectedOption] = useState(sortByOptions[0]);

    const onSortChange = (newOption) => {
        setSelectedOption(newOption);
        // send request to resort files
    }

    if (isLoading) {
        return <Loader />
    }

    console.log(searchParams.get('s'))

    return (
        <div className="inner-page w-100">
            <div className='pagetitle d-flex align-items-center justify-content-between mb-4'>
                <h1>Search for: {searchParams.get('s')}</h1>
                {/* <div className='d-flex align-items-center gap-4'>
                    <SortDropdown sortByOptions={sortByOptions} selectedOption={selectedOption} onChange={onSortChange} />
                    <a href='#'>Filter (remove later)</a>
                </div> */}
            </div>

            <h5>Files</h5>
            <FileList files={files.content}/>
            <hr className='my-3'/>
            <h5>Repositories</h5>
            <RepositoryList repositories={repositories.content}/>
        </div>
    );
}

const mapStateToProps = (state) => {
    return {
        user: state.authReducer.user,
        isLoading: state.authReducer.isLoading,
    }
}

const mapDispatchToProps = {
    updateUser
};

export default connect(mapStateToProps, mapDispatchToProps)((SearchPage));
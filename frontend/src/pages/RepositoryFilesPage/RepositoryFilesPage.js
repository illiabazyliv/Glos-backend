import { connect } from 'react-redux';
import { updateUser } from '../../store/thunks/authThunks';
import Loader from '../../components/Loader/Loader';
import FileList from '../../components/FileList/FileList';
import SortDropdown from '../../components/SortDropdown/SortDropdown';
import { useState } from 'react';
import { useParams } from 'react-router-dom';

function RepositoryFilesPage({ isLoading, user, updateUser }) {
    let { repositoryName } = useParams();

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

    const [selectedOption, setSelectedOption] = useState(sortByOptions[0]);

    const onSortChange = (newOption) => {
        setSelectedOption(newOption);
        // send request to resort files
    }

    if (isLoading) {
        return <Loader />
    }

    return (
        <div className="inner-page w-100">
            <div className='pagetitle d-flex align-items-center justify-content-between mb-4'>
                <h1>{repositoryName}</h1>
                <div className='d-flex align-items-center gap-4'>
                    <SortDropdown sortByOptions={sortByOptions} selectedOption={selectedOption} onChange={onSortChange} />
                    {/* <a href='#'>Filter</a> */}
                </div>
            </div>

            <FileList files={files.content}/>
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

export default connect(mapStateToProps, mapDispatchToProps)((RepositoryFilesPage));
import { connect } from 'react-redux';
import { updateUser } from '../../store/thunks/authThunks';
import Loader from '../../components/Loader/Loader';
import SortDropdown from '../../components/SortDropdown/SortDropdown';
import { useState } from 'react';
import RepositoryList from '../../components/RepositoryList/RepositoryList';

function AllRepositoriesPage({ isLoading, user, updateUser }) {
    const sortByOptions = [
        { name: 'By name A to Z', value: 'displayFilename,asc' },
        { name: 'By name Z to A', value: 'displayFilename,desc' },
    ];

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

    return (
        <div className="inner-page w-100">
            <div className='pagetitle d-flex align-items-center justify-content-between mb-4'>
                <h1>All repositories</h1>
                <div className='d-flex align-items-center gap-4'>
                    <SortDropdown sortByOptions={sortByOptions} selectedOption={selectedOption} onChange={onSortChange} />
                    {/* <a href='#'>Filter</a> */}
                </div>
            </div>

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

export default connect(mapStateToProps, mapDispatchToProps)((AllRepositoriesPage));
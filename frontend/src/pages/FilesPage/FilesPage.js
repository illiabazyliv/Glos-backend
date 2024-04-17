import { connect } from 'react-redux';
import { updateUser } from '../../store/thunks/authThunks';
import Loader from '../../components/Loader/Loader';
import FileList from '../../components/FileList/FileList';
import SortDropdown from '../../components/SortDropdown/SortDropdown';
import { useState } from 'react';

function FilesPage({isLoading, user, updateUser}) {
    const sortByOptions = [
        {name: 'By name A to Z', value: 'name,asc'},
        {name: 'By name Z to A', value: 'name,desc'},
    ];

    const [selectedOption, setSelectedOption] = useState(sortByOptions[0]);

    const onSortChange = (newOption) => {
        setSelectedOption(newOption);
        // send request to resort files
    }

    if(isLoading) {
        return <Loader/>
    }

    return (
        <div className="inner-page w-100">
            <div className='pagetitle d-flex align-items-center justify-content-between'>
                <h1>Uploaded files</h1>
                <div className='d-flex align-items-center gap-4'>
                    <SortDropdown sortByOptions={sortByOptions} selectedOption={selectedOption} onChange={onSortChange}/>
                    {/* <a href='#'>Filter</a> */}
                </div>
            </div>

            <FileList/>


            {/* page content here <br/>
            username: {user.username} <br/>
            <button onClick={() => {
                updateUser()
            }}>click me</button> */}
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

export default connect(mapStateToProps, mapDispatchToProps)((FilesPage));
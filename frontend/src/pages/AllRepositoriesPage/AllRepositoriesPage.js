import { connect } from 'react-redux';
import { updateUser } from '../../store/thunks/authThunks';
import Loader from '../../components/Loader/Loader';
import SortDropdown from '../../components/SortDropdown/SortDropdown';
import { useEffect, useState } from 'react';
import RepositoryList from '../../components/RepositoryList/RepositoryList';
import { loadLatestRepositories } from '../../store/thunks/repositoryThunks';

function AllRepositoriesPage({ loadLatestRepositories, isLoading, repositories, errors }) {
    const PAGE_SIZE = 10;

    const sortByOptions = [
        { name: 'By name A to Z', value: 'displayFilename,asc' },
        { name: 'By name Z to A', value: 'displayFilename,desc' },
    ];

    const [selectedOption, setSelectedOption] = useState(sortByOptions[0]);

    const onSortChange = (newOption) => {
        setSelectedOption(newOption);
        // send request to resort files
    }

    useEffect(() => {
        loadLatestRepositories(1, PAGE_SIZE);
    }, []);

    const onPageChange = (page) => {
        loadLatestRepositories(page, PAGE_SIZE);
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

            <RepositoryList repositories={repositories} errors={errors} onPageChange={onPageChange}/>
        </div>
    );
}

const mapStateToProps = (state) => {
    return {
        repositories: state.repositoryReducer.repositories,
        errors: state.repositoryReducer.errors,
        isLoading: state.repositoryReducer.isLoading,
    }
}

const mapDispatchToProps = {
    loadLatestRepositories
};

export default connect(mapStateToProps, mapDispatchToProps)((AllRepositoriesPage));
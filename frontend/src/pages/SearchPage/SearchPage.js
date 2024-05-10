import { connect } from 'react-redux';
import { updateUser } from '../../store/thunks/authThunks';
import Loader from '../../components/Loader/Loader';
import FileList from '../../components/FileList/FileList';
import SortDropdown from '../../components/SortDropdown/SortDropdown';
import { useEffect, useState } from 'react';
import RepositoryList from '../../components/RepositoryList/RepositoryList';
import { useSearchParams } from 'react-router-dom';
import { searchFiles } from '../../store/thunks/fileThunks';
import { searchRepositories } from '../../store/thunks/repositoryThunks';

function SearchPage({ files, filesLoading, repositories, repositoriesLoading, fileErrors, 
    repositoryErrors, searchFiles, searchRepositories }) {
    const PAGE_SIZE = 10;
    let [searchParams, setSearchParams] = useSearchParams();

    const sortByOptions = [
        { name: 'By name A to Z', value: 'displayFilename,asc' },
        { name: 'By name Z to A', value: 'displayFilename,desc' },
    ];

    const [selectedOption, setSelectedOption] = useState(sortByOptions[0]);

    useEffect(() => {
        searchFiles(searchParams.get('s'), 1, PAGE_SIZE);
        searchRepositories(searchParams.get('s'), 1, PAGE_SIZE);
    }, []);

    const onSortChange = (newOption) => {
        setSelectedOption(newOption);
        // send request to resort files
    }

    const onFilePageChange = (page) => {
        searchFiles(searchParams.get('s'), page, PAGE_SIZE);
    }

    const onRepositoryPageChange = (page) => {
        searchRepositories(searchParams.get('s'), page, PAGE_SIZE);
    }

    if (filesLoading || repositoriesLoading) {
        return <Loader />
    }

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
            <FileList files={files} errors={fileErrors} onPageChange={onFilePageChange}/>
            <hr className='my-3'/>
            <h5>Repositories</h5>
            <RepositoryList repositories={repositories} errors={repositoryErrors} onPageChange={onRepositoryPageChange}/>
        </div>
    );
}

const mapStateToProps = (state) => {
    return {
        files: state.fileReducer.files,
        filesLoading: state.fileReducer.isLoading,
        repositories: state.repositoryReducer.repositories,
        repositoriesLoading: state.repositoryReducer.isLoading,
        fileErrors: state.fileReducer.errors,
        repositoryErrors: state.repositoryReducer.errors,
    }
}

const mapDispatchToProps = {
    searchFiles,
    searchRepositories
};

export default connect(mapStateToProps, mapDispatchToProps)((SearchPage));
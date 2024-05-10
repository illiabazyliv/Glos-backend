import { connect } from 'react-redux';
import { updateUser } from '../../store/thunks/authThunks';
import Loader from '../../components/Loader/Loader';
import FileList from '../../components/FileList/FileList';
import SortDropdown from '../../components/SortDropdown/SortDropdown';
import { useEffect, useState } from 'react';
import { loadLatestFiles } from '../../store/thunks/fileThunks';

function AllFilesPage({ files, isLoading, loadLatestFiles, errors, }) {
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
        loadLatestFiles(1, PAGE_SIZE);
    }, []);

    const onPageChange = (page) => {
        loadLatestFiles(page, PAGE_SIZE);
    }

    if (isLoading) {
        return <Loader />
    }

    return (
        <div className="inner-page w-100">
            <div className='pagetitle d-flex align-items-center justify-content-between mb-4'>
                <h1>Uploaded files</h1>
                <div className='d-flex align-items-center gap-4'>
                    <SortDropdown sortByOptions={sortByOptions} selectedOption={selectedOption} onChange={onSortChange} />
                    {/* <a href='#'>Filter</a> */}
                </div>
            </div>

            <FileList files={files} errors={errors} onPageChange={onPageChange}/>
        </div>
    );
}

const mapStateToProps = (state) => {
    return {
        isLoading: state.fileReducer.isLoading,
        files: state.fileReducer.files,
        errors: state.fileReducer.errors,
    }
}

const mapDispatchToProps = {
    loadLatestFiles
};

export default connect(mapStateToProps, mapDispatchToProps)((AllFilesPage));
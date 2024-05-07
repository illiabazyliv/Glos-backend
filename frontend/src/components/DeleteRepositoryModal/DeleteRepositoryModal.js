import { connect } from 'react-redux';
import { deleteRepository, setRepositoryAccess } from "../../store/thunks/repositoryThunks";
import Loader from '../../components/Loader/Loader';
import { createRef } from 'react';

function DeleteRepositoryModal({ deleteRepository, isLoading, currentRepository }) {
    const closeBtn = createRef();

    const onDelete = () => {
        deleteRepository(currentRepository.displayname);
        closeBtn.current.click();
    }

    return (
        <div id="deleteRepositoryModal" className="modal" tabIndex="-1">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Delete repository</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" ref={closeBtn}></button>
                    </div>
                    <div className="modal-body">
                        {
                            isLoading ? <Loader /> : <p>Are you sure you want to delete this repository?</p>
                        }
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" onClick={onDelete}>Yes</button>
                        <button type="button" className="btn btn-primary" data-bs-dismiss="modal">No</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

const mapStateToProps = (state) => {
    return {
        isLoading: state.repositoryReducer.isLoading,
        currentRepository: state.repositoryReducer.currentRepository,
    }
};

const mapDispatchToProps = {
    deleteRepository
};

export default connect(mapStateToProps, mapDispatchToProps)((DeleteRepositoryModal));
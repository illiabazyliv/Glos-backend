import { connect } from 'react-redux';
import { deleteGroup, setGroupAccess } from "../../store/thunks/groupThunks";
import Loader from '../Loader/Loader';
import { createRef } from 'react';

function DeleteGroupModal({ deleteGroup, isLoading, currentGroup }) {
    const closeBtn = createRef();

    const onDelete = () => {
        deleteGroup(currentGroup.groupName);
        closeBtn.current.click();
    }

    return (
        <div id="deleteGroupModal" className="modal" tabIndex="-1">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Delete group</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" ref={closeBtn}></button>
                    </div>
                    <div className="modal-body">
                        {
                            isLoading ? <Loader /> : <p>Are you sure you want to delete this group?</p>
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
        isLoading: state.groupReducer.isLoading,
        currentGroup: state.groupReducer.currentGroup,
    }
};

const mapDispatchToProps = {
    deleteGroup
};

export default connect(mapStateToProps, mapDispatchToProps)((DeleteGroupModal));
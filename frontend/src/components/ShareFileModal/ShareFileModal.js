import { connect } from 'react-redux';
import { useForm } from "react-hook-form";
import { ACCESS_TYPES } from "../../helpers/constants";
import { addNewFile } from "../../store/thunks/fileThunks";
import Loader from '../../components/Loader/Loader';
import { createRef, useEffect } from 'react';

function ShareFileModal({ isLoading, user, addNewFile, newFile }) {
    useEffect(() => {
        // get link here
    }, []);

    return (
        <div id="shareFileModal" className="modal" tabIndex="-1">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Share file</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div className="modal-body">
                        {
                            isLoading ? <Loader /> :
                            <p>Link for sharing file</p>
                        }

                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

const mapStateToProps = (state) => {
    return {
        isLoading: state.fileReducer.isLoading,
        newFile: state.fileReducer.newFile,
        user: state.authReducer.user,
    }
}

const mapDispatchToProps = {
    addNewFile
};

export default connect(mapStateToProps, mapDispatchToProps)((ShareFileModal));
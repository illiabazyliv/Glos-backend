import { connect } from 'react-redux';
import { useForm } from "react-hook-form";
import { ACCESS_TYPES } from "../../helpers/constants";
import { getRepositoryToken } from "../../store/thunks/repositoryThunks";
import Loader from '../../components/Loader/Loader';
import { createRef, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

function ShareRepositoryModal({ currentRepository, isSharedTokenLoading, sharedToken, getRepositoryToken }) {
    const [link, setLink] = useState('');
    useEffect(() => {
        if(currentRepository?.displayname) getRepositoryToken(currentRepository?.displayname);
    }, [currentRepository]);

    console.log(isSharedTokenLoading, 'shared loading')

    useEffect(() => {
        // todo: change to host name
        setLink(`https://glos.com/repositories/${currentRepository?.displayname}/${sharedToken}`);
    }, [sharedToken]);

    return (
        <div id="shareRepositoryModal" className="modal" tabIndex="-1">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Share repository</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div className="modal-body">
                        {
                            isSharedTokenLoading || !sharedToken ? <Loader /> :
                            <div>Link for sharing: <i><Link to={link}>{link}</Link></i></div>
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
        isSharedTokenLoading: state.repositoryReducer.isSharedTokenLoading,
        sharedToken: state.repositoryReducer.sharedToken,
        currentRepository: state.repositoryReducer.currentRepository,
    }
}

const mapDispatchToProps = {
    getRepositoryToken
};

export default connect(mapStateToProps, mapDispatchToProps)((ShareRepositoryModal));
import { connect } from 'react-redux';
import { updateUser } from '../../store/thunks/authThunks';
import Loader from '../../components/Loader/Loader';

function GroupsPage({isLoading, user, updateUser}) {
    if(isLoading) {
        return <Loader/>
    }

    return (
        <div className="inner-page">
            <div className='pagetitle d-flex flex-wrap justify-content-between align-items-center gap-2'>
                <h1>My groups</h1>
                <a className='btn btn-primary' href="#" data-bs-toggle="modal" data-bs-target="#newGroupModal">
                    <i className='bi bi-plus sub-menu-special-icon me-2'></i>
                    <span>Add new group</span>
                </a>
            </div>
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

export default connect(mapStateToProps, mapDispatchToProps)((GroupsPage));
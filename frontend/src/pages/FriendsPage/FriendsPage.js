import { connect } from 'react-redux';
import { updateUser } from '../../store/thunks/authThunks';
import Loader from '../../components/Loader/Loader';

function FriendsPage({isLoading, user, updateUser}) {
    if(isLoading) {
        return <Loader/>
    }

    return (
        <div className="inner-page">
            <div className='pagetitle d-flex flex-wrap justify-content-between align-items-center gap-2'>
                <h1>My friends</h1>
                <a className='btn btn-primary' href="#" data-bs-toggle="modal" data-bs-target="#newFriendModal">
                    <i className='bi bi-plus sub-menu-special-icon me-2'></i>
                    <span>Add new friend</span>
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

export default connect(mapStateToProps, mapDispatchToProps)((FriendsPage));
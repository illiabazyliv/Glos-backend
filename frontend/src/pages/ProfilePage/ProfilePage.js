import { connect } from 'react-redux';
import { updateUser } from '../../store/thunks/authThunks';
import Loader from '../../components/Loader/Loader';

function ProfilePage({isLoading, user, updateUser}) {
    if(isLoading) {
        return <Loader/>
    }

    return (
        <div className="inner-page">
            <div className='pagetitle'>
                <h1>Profile Settings</h1>
            </div>
            
            username: {user.username} <br/>
            <button onClick={() => {
                updateUser()
            }}>click me</button>
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

export default connect(mapStateToProps, mapDispatchToProps)((ProfilePage));
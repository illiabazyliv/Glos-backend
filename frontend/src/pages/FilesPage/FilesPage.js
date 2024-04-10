import { connect } from 'react-redux';
import { updateUser } from '../../store/thunks/authThunks';
import Loader from '../../components/Loader/Loader';

function FilesPage({isLoading, user, updateUser}) {
    if(isLoading) {
        return <Loader/>
    }

    return (
        <div className="inner-page">
            page content here <br/>
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

export default connect(mapStateToProps, mapDispatchToProps)((FilesPage));
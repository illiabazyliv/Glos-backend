import { connect } from 'react-redux';
import { useForm } from "react-hook-form";
import { addNewFriend } from "../../store/thunks/friendThunks";
import Loader from '../Loader/Loader';
import { createRef, useEffect } from 'react';

function NewFriendModal({ isLoading, user, addNewFriend, newFriend }) {
    const { register, handleSubmit, reset, formState: { errors: formErrors } } = useForm();
    const closeBtn = createRef();

    useEffect(() => {
        if(!newFriend) return;
        closeBtn.current.click();
        //todo: redirect to repos page
    }, [newFriend]);

    const resetForm = () => {
        reset({
            username: '',
        });
    }

    const onFormSubmit = (data) => {
        addNewFriend(user.username, data.username);
    };

    return (
        <div id="newFriendModal" className="modal" tabIndex="-1">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Add new friend</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" ref={closeBtn}
                            onClick={resetForm}></button>
                    </div>
                    <div className="modal-body">
                        {
                            isLoading ? <Loader /> :
                            <form onSubmit={handleSubmit(onFormSubmit)}>
                                <div className="mb-3">
                                    <label htmlFor="username" className="">Usernmae</label>
                                    <input type="text" className="form-control"
                                        {...register("username", {
                                            required: "This field is required.",
                                            maxLength: 200,
                                        })} />
                                    {formErrors.username ?
                                        <small className="text-danger d-block">{formErrors.username.message}</small>
                                        : null}
                                    <small className="text-muted">Please, provide the username of the user you would like to add.</small>
                                </div>
                            </form>
                        }

                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal" onClick={resetForm}>Cancel</button>
                        <button type="button" className="btn btn-primary" onClick={handleSubmit(onFormSubmit)}>Save</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

const mapStateToProps = (state) => {
    return {
        isLoading: state.friendReducer.isLoading,
        newFriend: state.friendReducer.newFriend,
        user: state.authReducer.user,
    }
}

const mapDispatchToProps = {
    addNewFriend
};

export default connect(mapStateToProps, mapDispatchToProps)((NewFriendModal));
import { connect } from 'react-redux';
import { useForm } from "react-hook-form";
import { ACCESS_TYPES } from "../../helpers/constants";
import { addUserToGroup, removeUserFromGroup, setGroupAccess } from "../../store/thunks/groupThunks";
import Loader from '../Loader/Loader';
import { createRef, useEffect } from 'react';

function GroupUsersModal({ isLoading, addUserToGroup, removeUserFromGroup, currentGroup }) {
    const { register, handleSubmit, setValue, reset, formState: { errors: formErrors } } = useForm();
    const closeBtn = createRef();

    const resetForm = () => {
        reset({
            username: [],
        });
    }

    const onFormSubmit = (data) => {
        addUserToGroup(currentGroup.groupName, data.username);
        resetForm();
    };

    return (
        <div id="groupUsersModal" className="modal" tabIndex="-1">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Manage users</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" ref={closeBtn}
                            onClick={resetForm}></button>
                    </div>
                    <div className="modal-body">
                        {
                            isLoading ? <Loader /> :
                                <div>
                                    <form onSubmit={handleSubmit(onFormSubmit)}>
                                        <div className="mb-3">
                                            <label htmlFor="username" className="">Add user</label>
                                            <div className='d-flex'>
                                                <input type="text" className="form-control"
                                                    {...register("username", {
                                                        required: "This field is required.",
                                                        maxLength: 200,
                                                    })} />
                                                <button type='submit' className='btn btn-primary ms-1'>Add</button>
                                            </div>
                                            {formErrors.username ?
                                                <small className="text-danger d-block">{formErrors.username.message}</small>
                                                : null}
                                        </div>
                                    </form>
                                    <div className="mb-3">
                                        <table class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <th colSpan={2}>Current memebers</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                {
                                                    currentGroup?.users?.length ? currentGroup.users.map(user => {
                                                        return <tr key={user.username}>
                                                            <td>{user.username}</td>
                                                            <td align='right'>
                                                                <button className='btn btn-primary' onClick={() => {removeUserFromGroup(currentGroup.groupName, user.username)}}>
                                                                    <i className="bi bi-trash"></i>
                                                                </button>
                                                            </td>
                                                        </tr>
                                                    }) :
                                                    <tr>
                                                        <td>No users found.</td>
                                                    </tr>
                                                }
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                        }
                    </div>
                    {/* <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal" onClick={resetForm}>Cancel</button>
                        <button type="button" className="btn btn-primary" onClick={handleSubmit(onFormSubmit)}>Save</button>
                    </div> */}
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
    addUserToGroup,
    removeUserFromGroup
};

export default connect(mapStateToProps, mapDispatchToProps)((GroupUsersModal));
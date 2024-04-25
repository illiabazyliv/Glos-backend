import { connect } from 'react-redux';
import { useForm } from "react-hook-form";
import { ACCESS_TYPES } from "../../helpers/constants";
import { addNewGroup } from "../../store/thunks/groupThunks";
import Loader from '../Loader/Loader';
import { createRef, useEffect } from 'react';

function NewGroupModal({ isLoading, user, addNewGroup, newGroup }) {
    const { register, handleSubmit, reset, formState: { errors: formErrors } } = useForm();
    const closeBtn = createRef();

    useEffect(() => {
        if(!newGroup) return;
        closeBtn.current.click();
        //todo: redirect to repos page
    }, [newGroup]);

    const resetForm = () => {
        reset({
            name: '',
            accessTypes: [],
        });
    }

    const onFormSubmit = (data) => {
        console.log(data);
        addNewGroup(user.username, data.name, {
            "groupName": data.name,
            "access_type": data.accessTypes,
            "users": []
        });
    };

    return (
        <div id="newGroupModal" className="modal" tabIndex="-1">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Add new group</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" ref={closeBtn}
                            onClick={resetForm}></button>
                    </div>
                    <div className="modal-body">
                        {
                            isLoading ? <Loader /> :
                            <form onSubmit={handleSubmit(onFormSubmit)}>
                                <div className="mb-3">
                                    <label htmlFor="name" className="">Name</label>
                                    <input type="text" className="form-control"
                                        {...register("name", {
                                            required: "This field is required.",
                                            pattern: {
                                                value: /^[a-zA-Z0-9_-]+$/,
                                                message: "Invalid value."
                                            },
                                            maxLength: 200,
                                        })} />
                                    {formErrors.name ?
                                        <small className="text-danger d-block">{formErrors.name.message}</small>
                                        : null}
                                    <small className="text-muted">Tip: name can only contain letters, numbers, underscores and dashes.</small>
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="accessTypes" className="">Access</label>
                                    <select className="form-control"
                                        {...register("accessTypes", {
                                            required: "This field is required.",
                                        })}>
                                        {
                                            ACCESS_TYPES.map(item => <option value={item.value} key={item.value}>
                                                {item.name}
                                            </option>)
                                        }
                                    </select>
                                    {formErrors.accessTypes ?
                                        <small className="text-danger d-block">{formErrors.accessTypes.message}</small>
                                        : null}
                                    {/* <small className="text-muted">Tip: hold CTRL to select multiple.</small> */}
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
        isLoading: state.groupReducer.isLoading,
        newGroup: state.groupReducer.newGroup,
        user: state.authReducer.user,
    }
}

const mapDispatchToProps = {
    addNewGroup
};

export default connect(mapStateToProps, mapDispatchToProps)((NewGroupModal));
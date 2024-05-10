import { connect } from 'react-redux';
import { useForm } from "react-hook-form";
import { ACCESS_TYPES } from "../../helpers/constants";
import { addNewRepository } from "../../store/thunks/repositoryThunks";
import Loader from '../../components/Loader/Loader';
import { createRef, useEffect } from 'react';

function NewRepositoryModal({ isLoading, user, addNewRepository, newRepository }) {
    const { register, handleSubmit, reset, formState: { errors: formErrors } } = useForm();
    const closeBtn = createRef();

    useEffect(() => {
        if(!newRepository) return;
        closeBtn.current.click();
        //todo: redirect to repos page
    }, [newRepository]);

    const resetForm = () => {
        reset({
            name: '',
            description: '',
            accessTypes: [],
        });
    }

    const onFormSubmit = (data) => {
        console.log(data);
        addNewRepository({
            "displayPath": "/",
            "displayname": data.name,
            "displayFullName": "/" + data.name,
            "description": data.description,
            "access_types": data.accessTypes,
            "owner": user.username
        });
    };

    return (
        <div id="newRepositoryModal" className="modal" tabIndex="-1">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Add new repository</h5>
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
                                    <label htmlFor="description" className="">Description</label>
                                    <input type="text" className="form-control"
                                        {...register("description", {
                                            required: "This field is required.",
                                            maxLength: 200,
                                        })} />
                                    {formErrors.description ?
                                        <small className="text-danger d-block">{formErrors.description.message}</small>
                                        : null}
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="accessTypes" className="">Access</label>
                                    <select className="form-control" multiple={true}
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
                                    <small className="text-muted">Tip: hold CTRL to select multiple.</small>
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
        isLoading: state.repositoryReducer.isLoading,
        newRepository: state.repositoryReducer.newRepository,
        user: state.authReducer.user,
    }
}

const mapDispatchToProps = {
    addNewRepository
};

export default connect(mapStateToProps, mapDispatchToProps)((NewRepositoryModal));
import { connect } from 'react-redux';
import { useForm } from "react-hook-form";
import { ACCESS_TYPES } from "../../helpers/constants";
import { setRepositoryAccess } from "../../store/thunks/repositoryThunks";
import Loader from '../../components/Loader/Loader';
import { createRef, useEffect } from 'react';

function EditRepositoryModal({ isLoading, user, setRepositoryAccess, currentRepository }) {
    const { register, handleSubmit, setValue, reset, formState: { errors: formErrors } } = useForm();
    const closeBtn = createRef();

    useEffect(() => {
        if(!currentRepository) return;
        // closeBtn.current.click();
        setValue('accessTypes', currentRepository.access_types);
    }, [currentRepository]);

    const resetForm = () => {
        reset({
            accessTypes: [],
        });
    }

    const onFormSubmit = (data) => {
        setRepositoryAccess(user.username, currentRepository.displayname, data.accessTypes);
    };

    return (
        <div id="editRepositoryModal" className="modal" tabIndex="-1">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Edit repository access</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" ref={closeBtn}
                            onClick={resetForm}></button>
                    </div>
                    <div className="modal-body">
                        {
                            isLoading ? <Loader /> :
                            <form onSubmit={handleSubmit(onFormSubmit)}>
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
        currentRepository: state.repositoryReducer.currentRepository,
        user: state.authReducer.user,
    }
};

const mapDispatchToProps = {
    setRepositoryAccess
};

export default connect(mapStateToProps, mapDispatchToProps)((EditRepositoryModal));
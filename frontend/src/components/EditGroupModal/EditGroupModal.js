import { connect } from 'react-redux';
import { useForm } from "react-hook-form";
import { ACCESS_TYPES } from "../../helpers/constants";
import { setGroupAccess } from "../../store/thunks/groupThunks";
import Loader from '../Loader/Loader';
import { createRef, useEffect } from 'react';

function EditGroupModal({ isLoading, setGroupAccess, currentGroup }) {
    const { register, handleSubmit, setValue, reset, formState: { errors: formErrors } } = useForm();
    const closeBtn = createRef();

    useEffect(() => {
        if(!currentGroup) return;
        // closeBtn.current.click();
        setValue('accessType', currentGroup.access_type);
    }, [currentGroup]);

    const resetForm = () => {
        reset({
            accessType: [],
        });
    }

    const onFormSubmit = (data) => {
        setGroupAccess(currentGroup.groupName, data.accessType);
        // todo: add move ability
    };

    return (
        <div id="editGroupModal" className="modal" tabIndex="-1">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Edit group</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" ref={closeBtn}
                            onClick={resetForm}></button>
                    </div>
                    <div className="modal-body">
                        {
                            isLoading ? <Loader /> :
                            <form onSubmit={handleSubmit(onFormSubmit)}>
                                <div className="mb-3">
                                    <label htmlFor="accessType" className="">Access</label>
                                    <select className="form-control"
                                        {...register("accessType", {
                                            required: "This field is required.",
                                        })}>
                                        {
                                            ACCESS_TYPES.map(item => <option value={item.value} key={item.value}>
                                                {item.name}
                                            </option>)
                                        }
                                    </select>
                                    {formErrors.accessType ?
                                        <small className="text-danger d-block">{formErrors.accessType.message}</small>
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
        currentGroup: state.groupReducer.currentGroup,
    }
};

const mapDispatchToProps = {
    setGroupAccess
};

export default connect(mapStateToProps, mapDispatchToProps)((EditGroupModal));
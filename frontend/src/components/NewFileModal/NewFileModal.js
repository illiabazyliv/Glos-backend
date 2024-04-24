import { connect } from 'react-redux';
import { useForm } from "react-hook-form";
import { ACCESS_TYPES } from "../../helpers/constants";
import { addNewFile } from "../../store/thunks/fileThunks";
import Loader from '../../components/Loader/Loader';
import { createRef, useEffect } from 'react';

function NewFileModal({ isLoading, user, addNewFile, newFile }) {
    const { register, handleSubmit, reset, formState: { errors: formErrors } } = useForm();
    const closeBtn = createRef();

    useEffect(() => {
        if(!newFile) return;
        closeBtn.current.click();
        //todo: redirect to repos page
    }, [newFile]);

    const resetForm = () => {
        reset({
            file: null
        });
    }

    const onFormSubmit = (data) => {
        console.log(data);
        addNewFile(user.username, data.file[0].name, data.file[0]);
    };

    return (
        <div id="newFileModal" className="modal" tabIndex="-1">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Upload a file</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" ref={closeBtn}
                            onClick={resetForm}></button>
                    </div>
                    <div className="modal-body">
                        {
                            isLoading ? <Loader /> :
                            <form onSubmit={handleSubmit(onFormSubmit)}>
                                {/* <div className="mb-3">
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
                                </div> */}
                                <div className="mb-3">
                                    <label htmlFor="file" className="">File</label>
                                    <input type="file" className="form-control"
                                        {...register("file", {
                                            required: "This field is required.",
                                        })} />
                                    {formErrors.file ?
                                        <small className="text-danger d-block">{formErrors.file.message}</small>
                                        : null}
                                </div>
                            </form>
                        }

                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal" onClick={resetForm}>Cancel</button>
                        <button type="button" className="btn btn-primary" onClick={handleSubmit(onFormSubmit)}>Upload</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

const mapStateToProps = (state) => {
    return {
        isLoading: state.fileReducer.isLoading,
        newFile: state.fileReducer.newFile,
        user: state.authReducer.user,
    }
}

const mapDispatchToProps = {
    addNewFile
};

export default connect(mapStateToProps, mapDispatchToProps)((NewFileModal));
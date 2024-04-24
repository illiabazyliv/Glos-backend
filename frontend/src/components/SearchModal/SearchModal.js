
import { createRef } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";

function SearchModal({ repository }) {
    const { register, handleSubmit, reset, formState: { errors: formErrors } } = useForm();
    const navigate = useNavigate();
    const closeBtn = createRef();

    const onFormSubmit = (data) => {
        navigate('/dashboard/search?s=' + encodeURIComponent(data.search));
        closeBtn.current.click();
    };

    const resetForm = () => {
        reset({
            search: '',
        });
    }

    return (
        <div id="searchModal" className="modal" tabIndex="-1">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Search</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" ref={closeBtn} onClick={resetForm}></button>
                    </div>
                    <div className="modal-body">
                        <form onSubmit={handleSubmit(onFormSubmit)}>
                            <div class="input-group">
                                <span class="input-group-text">
                                    <i className='bi bi-search'></i>
                                </span>
                                <input type="search" class="form-control" placeholder="Start typing..."
                                    {...register("search", {
                                        required: "This field is required.",
                                        maxLength: 200,
                                    })} />
                            </div>
                        </form>
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal" onClick={resetForm}>Cancel</button>
                        <button type="button" className="btn btn-primary" onClick={handleSubmit(onFormSubmit)}>Search</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default SearchModal;

import { useForm } from "react-hook-form";

function SearchModal({ repository }) {
    const { register, handleSubmit, setValue, formState: { errors: formErrors } } = useForm();

    return (
        <div id="searchModal" className="modal" tabIndex="-1">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Search</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div className="modal-body">
                        <form>
                            <div class="input-group mb-3">
                                <span class="input-group-text">
                                    <i className='bi bi-search'></i>
                                </span>
                                <input type="text" class="form-control" placeholder="Start typing..."/>
                            </div>
                        </form>
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="button" className="btn btn-primary">Search</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default SearchModal;
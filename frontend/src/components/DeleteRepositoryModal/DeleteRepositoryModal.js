
import { useForm } from "react-hook-form";

function DeleteRepositoryModal({ repository }) {
    const { register, handleSubmit, setValue, formState: { errors: formErrors } } = useForm();

    return (
        <div id="deleteRepositoryModal" className="modal" tabIndex="-1">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Delete repository</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div className="modal-body">
                        <p>Are you sure you want to delete this repository?</p>
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary">Yes</button>
                        <button type="button" className="btn btn-primary" data-bs-dismiss="modal">No</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default DeleteRepositoryModal;
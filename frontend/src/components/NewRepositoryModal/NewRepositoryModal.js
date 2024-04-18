
import { useForm } from "react-hook-form";

function NewRepositoryModal({ repository }) {
    const { register, handleSubmit, setValue, formState: { errors: formErrors } } = useForm();

    return (
        <div id="newRepositoryModal" className="modal" tabIndex="-1">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Add new repository</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div className="modal-body">
                        <p>Form for creating a repository will go here</p>
                    </div>
                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                        <button type="button" className="btn btn-primary">Save</button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default NewRepositoryModal;
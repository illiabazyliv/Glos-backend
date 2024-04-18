
import { useForm } from "react-hook-form";

function ShareFileModal({ repository }) {
    const { register, handleSubmit, setValue, formState: { errors: formErrors } } = useForm();

    return (
        <div id="shareFileModal" className="modal" tabIndex="-1">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title">Share file</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div className="modal-body">
                        <p>Link for sharing file goes here</p>
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

export default ShareFileModal;
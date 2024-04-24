import { connect } from 'react-redux';
import { setCurrentFile } from '../../store/thunks/fileThunks';

function FileDropdown({ fileId, setCurrentFile }) {
    return (
        <div className='header-profile'>
            <a className="nav-link nav-profile d-flex align-items-center pe-0" href="#" data-bs-toggle="dropdown">
                <i className="bi bi-three-dots"></i>
            </a>

            <ul className="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile">
                <li>
                    <a className="dropdown-item d-flex align-items-center" href="#">
                        <i className="bi bi-cloud-download"></i>
                        <span>Download</span>
                    </a>
                </li>
                <li><hr className="dropdown-divider" /></li>
                <li>
                    <a className="dropdown-item d-flex align-items-center" href="#" data-bs-toggle="modal" data-bs-target="#shareFileModal">
                        <i className="bi bi-people"></i>
                        <span>Share</span>
                    </a>
                </li>
                <li><hr className="dropdown-divider" /></li>
                <li>
                    <a className="dropdown-item d-flex align-items-center" href="#" data-bs-toggle="modal" data-bs-target="#editFileModal"
                        onClick={() => {setCurrentFile(fileId);}}>
                        <i className="bi bi-pencil"></i>
                        <span>Edit access</span>
                    </a>
                </li>
                <li><hr className="dropdown-divider" /></li>
                <li>
                    <a className="dropdown-item d-flex align-items-center" href="#" data-bs-toggle="modal" data-bs-target="#deleteFileModal">
                        <i className="bi bi-trash"></i>
                        <span>Delete</span>
                    </a>
                </li>
            </ul>
        </div>
    );
}

const mapStateToProps = (state) => {
    return {
        
    }
};

const mapDispatchToProps = {
    setCurrentFile
};

export default connect(mapStateToProps, mapDispatchToProps)((FileDropdown));

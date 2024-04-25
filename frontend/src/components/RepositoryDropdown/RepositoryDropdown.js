import { connect } from 'react-redux';
import { setCurrentRepository } from '../../store/thunks/repositoryThunks';

function RepositoryDropdown({ repositoryId, setCurrentRepository, currentRepository }) {
    return (
        <div className='header-profile'>
            <a className="nav-link nav-profile d-flex align-items-center pe-0" href="#" data-bs-toggle="dropdown">
                <i className="bi bi-three-dots"></i>
            </a>

            <ul className="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile">
                <li>
                    <a className="dropdown-item d-flex align-items-center" href="#"  data-bs-toggle="modal" data-bs-target="#shareRepositoryModal"
                        onClick={() => { 
                            if(!(currentRepository?.displayname == repositoryId)) {
                                setCurrentRepository(repositoryId);
                                console.log('set rep')
                                console.log(currentRepository?.displayname)
                                console.log(repositoryId)
                            }
                        }}>
                        <i className="bi bi-people"></i>
                        <span>Share</span>
                    </a>
                </li>
                <li><hr className="dropdown-divider" /></li>
                <li>
                    <a className="dropdown-item d-flex align-items-center" href="#" data-bs-toggle="modal" data-bs-target="#editRepositoryModal"
                        onClick={() => {setCurrentRepository(repositoryId);}}>
                        <i className="bi bi-pencil"></i>
                        <span>Edit access</span>
                    </a>
                </li>
                <li><hr className="dropdown-divider" /></li>
                <li>
                    <a className="dropdown-item d-flex align-items-center" href="#" data-bs-toggle="modal" data-bs-target="#deleteRepositoryModal">
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
        currentRepository: state.repositoryReducer.currentRepository
    }
}

const mapDispatchToProps = {
    setCurrentRepository
};

export default connect(mapStateToProps, mapDispatchToProps)((RepositoryDropdown));
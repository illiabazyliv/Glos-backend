import { connect } from 'react-redux';
import { setCurrentGroup } from '../../store/thunks/groupThunks';

function GroupDropdown({ groupId, setCurrentGroup, currentGroup, btnRef }) {
    return (
        <div className='header-profile'>
            <a className="nav-link nav-profile d-flex align-items-center pe-0" href="#" data-bs-toggle="dropdown">
                <i className="bi bi-three-dots"></i>
            </a>

            <ul className="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile">
                <li>
                    <a className="dropdown-item d-flex align-items-center" href="#"  data-bs-toggle="modal" data-bs-target="#groupUsersModal"
                        onClick={() => { 
                            if(!(currentGroup?.displayname == groupId)) {
                                setCurrentGroup(groupId);
                            }
                        }} ref={btnRef}>
                        <i className="bi bi-people"></i>
                        <span>Manage users</span>
                    </a>
                </li>
                <li><hr className="dropdown-divider" /></li>
                <li>
                    <a className="dropdown-item d-flex align-items-center" href="#" data-bs-toggle="modal" data-bs-target="#editGroupModal"
                        onClick={() => {setCurrentGroup(groupId);}}>
                        <i className="bi bi-pencil"></i>
                        <span>Edit access</span>
                    </a>
                </li>
                <li><hr className="dropdown-divider" /></li>
                <li>
                    <a className="dropdown-item d-flex align-items-center" href="#" data-bs-toggle="modal" data-bs-target="#deleteGroupModal"
                        onClick={() => {setCurrentGroup(groupId);}}>
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
        currentGroup: state.groupReducer.currentGroup
    }
}

const mapDispatchToProps = {
    setCurrentGroup
};

export default connect(mapStateToProps, mapDispatchToProps)((GroupDropdown));
import { NavLink } from 'react-router-dom';

function DashboardSidebar() {
    return (
        <aside className='sidebar'>
            <ul className='sidebar-nav'>
                <li className='nav-item'>
                    {/* <NavLink className='nav-link collapsed' to='uploaded-files'>
                        <i className='bi bi-file-earmark-text'></i>
                        <span>Uploaded files</span>
                    </NavLink> */}

                    <a className='btn btn-primary ms-2 mb-3' href="#" data-bs-toggle="modal" data-bs-target="#newFileModal">
                        <i className='bi bi-plus sub-menu-special-icon me-2'></i>
                        <span>Upload a file</span>
                    </a>

                    <a className='btn btn-outline-primary ms-2 mb-3' href="#" data-bs-toggle="modal" data-bs-target="#newRepositoryModal">
                        <i className='bi bi-plus sub-menu-special-icon me-2'></i>
                        <span>Add new repository</span>
                    </a>
                </li>
                <li className='nav-item'>
                    <NavLink className='nav-link collapsed' to='uploaded-files'>
                        <i className='bi bi-file-earmark-text'></i>
                        <span>Uploaded files</span>
                    </NavLink>
                </li>
                <li className='nav-item'>
                    <NavLink className='nav-link collapsed' to='repositories'>
                        <i className='bi bi-folder'></i>
                        <span>Repositories</span>
                    </NavLink>
                </li>
                {/* <li className='nav-item'>
                    <NavLink to='repositories' className='nav-link' data-bs-target="#repositories-nav" data-bs-toggle="collapse" >
                        <i className='bi bi-folder'></i>
                        <span>Repositories</span>
                    </NavLink>
                    <ul id="repositories-nav" className='nav-content collapse'>
                        <li>
                            <NavLink className='nav-link' to='repositories' end>
                                <i className='bi bi-circle'></i>
                                <span>All repositories</span>
                            </NavLink>
                            <NavLink className='nav-link' to='repositories/general' end>
                                <i className='bi bi-circle'></i>
                                <span>General</span>
                            </NavLink>


                            <a className='nav-link' href="#" data-bs-toggle="modal" data-bs-target="#newRepositoryModal">
                                <i className='bi bi-plus sub-menu-special-icon'></i>
                                <span>Add new</span>
                            </a>


                        </li>
                    </ul>
                </li> */}
                <li className='nav-item'>
                    <NavLink className='nav-link collapsed' to='friends'>
                        <i className='bi bi-people'></i>
                        <span>Friends</span>
                    </NavLink>
                </li>
                <li className='nav-item'>
                    <NavLink className='nav-link collapsed' to='friends'>
                        <i className='bi bi-diagram-3'></i>
                        <span>Groups</span>
                    </NavLink>
                </li>
            </ul>
        </aside>

    );
}

export default DashboardSidebar;
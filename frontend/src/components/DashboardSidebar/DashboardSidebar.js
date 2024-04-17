import { NavLink } from 'react-router-dom';

function DashboardSidebar() {
    return (
        <aside className='sidebar'>
            <ul className='sidebar-nav'>
                <li className='nav-item'>
                    <NavLink className='nav-link collapsed' to='uploaded-files'>
                        <i className='bi bi-file-earmark-text'></i>
                        <span>Uploaded files</span>
                    </NavLink>
                </li>
                <li className='nav-item'>
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
                        <NavLink className='nav-link' to='new-repository' end>
                            <i className='bi bi-plus sub-menu-special-icon'></i>
                            <span>Add new</span>
                        </NavLink>
                        </li>
                    </ul>
                </li>
            </ul>
        </aside>
        
    );
  }
  
  export default DashboardSidebar;
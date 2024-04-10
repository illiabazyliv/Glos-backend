import { NavLink } from 'react-router-dom';

function DashboardSidebar() {
    return (
        <aside className='sidebar'>
            <ul className='sidebar-nav'>
                <li className='nav-item'>
                    <NavLink className='nav-link' to='all-files'>All files</NavLink>
                    <NavLink className='nav-link' to='repositories'>Repositories</NavLink>
                </li>
            </ul>
        </aside>
        
    );
  }
  
  export default DashboardSidebar;